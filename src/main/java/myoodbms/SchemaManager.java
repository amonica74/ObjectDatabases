package myoodbms;

import myoodbms.template.*;
import myoodbms.utils.Parser;
import myoodbms.dbclasses.*;
import myoodbms.dbclasses.Type;
import myoodbms.dbutils.Schema;
import myoodbms.dbutils.Version;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.lang.reflect.Method;

import com.db4o.*;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.*;

import org.eclipse.jdt.core.*;


//PROVARE SCHEMA EVOLUTION !!!!!!! cancellare attribute in iNSTANCE
public class SchemaManager {
	

	//ClassPool cp;
	ObjectSet<Type> result;
	
	 //delete all the Types
	public void deleteSchema (){
		
		ObjectSet<Type> result = DBManager.db.queryByExample(Type.class);
		for(Type t:result)
			DBManager.deleteObject(t);
		
		
	}
	
	//delete all the Instances
	public void deleteInstances (){
		
		ObjectSet<Instance> result = DBManager.db.queryByExample(Instance.class);
		for(Instance t:result)
			DBManager.deleteObject(t);
		
		
	} //delete all the Types

	
	public void loadSchema(){
		

		Schema.types = new HashMap<String, Type>();//FARE NEW GLOBALS DA UN'ALTRA PARTE
		
		result = DBManager.db.queryByExample(Type.class);
		
		System.out.println("----------------------3");
		
		//va ripetuto per tutte le classi
		//andrebbe fatto dinamico per tutte le classi che sono nel package
		//myoodbms.template --> sostituire Request.class con class dinamica di volta in volta estratta
		//***************************
		
		loadType(Request.class);
		loadType(GenerateRequest.class);
		loadType(PrintRequest.class);
		loadType(ReportDefinition.class);
		loadType(ReportDatasource.class);

		
		//*************************
		
	}
	
	private void loadType(Class<?> schemaClass){
		
		try{
		Type requestType = result.stream().filter(p -> p.typename.equals(schemaClass.getSimpleName())).findAny().orElse(null);

		if(requestType == null){			
			requestType= createType(schemaClass); // creo leggendo il template e poi restituisco Type e lo storo
			DBManager.db.store(requestType);			
		}else{
			if(requestType.Version != schemaClass.getAnnotation(Version.class).number()){
				requestType= updateType(schemaClass, requestType);
				DBManager.db.store(requestType);
			}			
		}
		Schema.types.put(schemaClass.getSimpleName(), requestType);
		}catch(Exception ex){
			
			System.out.println(ex.getMessage() +" "+ ex.getStackTrace());
			java.io.StringWriter sw = new java.io.StringWriter();
			ex.printStackTrace(new java.io.PrintWriter(sw));
			String exceptionAsString = sw.toString();
			System.out.println(exceptionAsString);
		}
		
	}
	
	private Type createType(Class<?> schemaClass){
		
		HashMap<String,Class<?>> attributes= new HashMap<String,Class<?>>();
		HashMap<String, Relationship> relationships = new HashMap<String, Relationship>();
		
		//annotations- relationships
		myoodbms.dbutils.Relationship[] classAnnotations = schemaClass.getAnnotationsByType(myoodbms.dbutils.Relationship.class);
		Relationship rel;
		for( myoodbms.dbutils.Relationship ann : classAnnotations){
			rel= new Relationship();
			rel.name=ann.name();
		    rel.inverseRelation=ann.inverseRelation();
			rel.relatedClass=ann.relatedClass();
			rel.relType=ann.relType();
			
			relationships.put(rel.name, rel);
			
		}
	
		//attributes
		HashMap<String,String> toreplace = new HashMap<String,String>();
		Field[] classFields = schemaClass.getDeclaredFields();
		String relname="";
		for( Field f: classFields){	
			relname="";
			for (HashMap.Entry<String, Relationship> entry : relationships.entrySet()){				
				if(f.getType().equals(((Relationship)entry.getValue()).relatedClass)){
					relname=entry.getKey();
					break;
				}else if(f.getType().equals(java.util.List.class) || f.getType().equals(java.util.Set.class)){
					if(((ParameterizedType)f.getGenericType()).getActualTypeArguments()[0].equals(((Relationship)entry.getValue()).relatedClass)){
						relname=entry.getKey();
						break;	
					}
				}
			}
			if(!relname.equals(""))
				toreplace.put(f.getName(), relname);
			else
				attributes.put(f.getName(),f.getType());
		}
		
		Type newtype = new Type(schemaClass.getSimpleName(), attributes);
		
		newtype.setRelationships(relationships);
		
		//Version
		myoodbms.dbutils.Version verAnnotation = schemaClass.getAnnotationsByType(myoodbms.dbutils.Version.class)[0];
		newtype.Version=verAnnotation.number();

		
		//methods
		Method[] classMethods = schemaClass.getDeclaredMethods();
		try{
			Parser.ParseFile(schemaClass);	
		}catch(java.io.IOException ex){}
		HashMap<String,String> bodys =Parser.getBodys();

		HashMap<String,Class<?>> parameters;
		String code,signature,nameforsignature;
		myoodbms.dbclasses.Method newmethod;
		for(Method m : classMethods){
			signature="";
			parameters= new HashMap<String,Class<?>>();
			for(Parameter p : m.getParameters()){
				parameters.put(p.getName(),p.getType());
				try{
					nameforsignature = ((ParameterizedType)p.getParameterizedType()).getActualTypeArguments()[0].getTypeName();
					signature+= ", "+p.getType().getSimpleName().toString() +"<"+nameforsignature.substring(nameforsignature.lastIndexOf(".")+1)+"> "+p.getName();
		    	}catch(Exception ex){		    		
					signature+= ", " + p.getType().getSimpleName().toString()+ " "+p.getName();
		    	}
			}	
			if(signature !="") signature= signature.substring(2);
			signature = m.getName() +"("+signature +")";
			code= bodys.get(signature);
			for (HashMap.Entry<String, String> entry : toreplace.entrySet()){	
				code=code.replace(entry.getKey(),entry.getValue());
			}
			newmethod = new myoodbms.dbclasses.Method(m.getName(), parameters, m.getReturnType(),code);		
			newtype.appendMethod(m.getName(), newmethod);
			
		}

		
		
		//superclass
		Class<?> superClass = schemaClass.getSuperclass();
		if(superClass != Object.class)
			newtype.inherits(Schema.types.get(superClass.getSimpleName()));
		
		
		return newtype;
	}
	
	private Type updateType(Class<?> schemaClass, Type prevtype){
		
		prevtype.getAttributes().clear();
		prevtype.getRelationships().clear();
		prevtype.getMethods().clear();
		
		
		HashMap<String,Class<?>> attributes= new HashMap<String,Class<?>>();
		HashMap<String, Relationship> relationships = new HashMap<String, Relationship>();
		
		//annotations- relationships
		myoodbms.dbutils.Relationship[] classAnnotations = schemaClass.getAnnotationsByType(myoodbms.dbutils.Relationship.class);
		Relationship rel;
		for( myoodbms.dbutils.Relationship ann : classAnnotations){
			rel= new Relationship();
			rel.name=ann.name();
		    rel.inverseRelation=ann.inverseRelation();
			rel.relatedClass=ann.relatedClass();
			rel.relType=ann.relType();
			
			relationships.put(rel.name, rel);
			
		}
	
		//attributes
		HashMap<String,String> toreplace = new HashMap<String,String>();
		Field[] classFields = schemaClass.getDeclaredFields();
		String relname="";
		for( Field f: classFields){	
			relname="";
			for (HashMap.Entry<String, Relationship> entry : relationships.entrySet()){				
				if(f.getType().equals(((Relationship)entry.getValue()).relatedClass)){
					relname=entry.getKey();
					break;
				}else if(f.getType().equals(java.util.List.class) || f.getType().equals(java.util.Set.class)){
					if(((ParameterizedType)f.getGenericType()).getActualTypeArguments()[0].equals(((Relationship)entry.getValue()).relatedClass)){
						relname=entry.getKey();
						break;	
					}
				}
			}
			if(!relname.equals(""))
				toreplace.put(f.getName(), relname);
			else
				attributes.put(f.getName(),f.getType());
		}
		
		prevtype.setAttributes(attributes);
		
		prevtype.setRelationships(relationships);
		
		//Version
		myoodbms.dbutils.Version verAnnotation = schemaClass.getAnnotationsByType(myoodbms.dbutils.Version.class)[0];
		prevtype.Version=verAnnotation.number();

		
		//methods
		Method[] classMethods = schemaClass.getDeclaredMethods();
		try{
			Parser.ParseFile(schemaClass);	
		}catch(java.io.IOException ex){}
		HashMap<String,String> bodys =Parser.getBodys();

		HashMap<String,Class<?>> parameters;
		String code,signature,nameforsignature;
		myoodbms.dbclasses.Method newmethod;
		for(Method m : classMethods){
			signature="";
			parameters= new HashMap<String,Class<?>>();
			for(Parameter p : m.getParameters()){
				parameters.put(p.getName(),p.getType());				
				try{
					nameforsignature = ((ParameterizedType)p.getParameterizedType()).getActualTypeArguments()[0].getTypeName();
					signature+= ", "+p.getType().getSimpleName().toString() +"<"+nameforsignature.substring(nameforsignature.lastIndexOf(".")+1)+"> "+p.getName();
		    	}catch(Exception ex){		    		
					signature+= ", " + p.getType().getSimpleName().toString()+ " "+p.getName();
		    	}
			}	
			if(signature !="") signature= signature.substring(2);
			signature= m.getName() +"("+signature +")";
			code= bodys.get(signature);
			for (HashMap.Entry<String, String> entry : toreplace.entrySet()){	
				code=code.replace(entry.getKey(),entry.getValue());
			}
			newmethod = new myoodbms.dbclasses.Method(m.getName(), parameters, m.getReturnType(),code);		
			prevtype.appendMethod(m.getName(), newmethod);
			
		}

			
		//superclass -SISTEMARE PERCHE' BISOGNA TOGLIERE PRIMA LA VECCHIA EREDITARIETA'
		Class<?> superClass = schemaClass.getSuperclass();
		if(superClass != Object.class)
			prevtype.replaceInheritance(Schema.types.get(superClass.getSimpleName()));		
		
		return prevtype;
		
	
	}
	
	private boolean checker2(Object obj, Class<?> someClass) {
	    return someClass.equals(obj.getClass());
	}

}
