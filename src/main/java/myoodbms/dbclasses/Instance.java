package myoodbms.dbclasses;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import myoodbms.utils.Converter;
import bsh.EvalError;
import bsh.Interpreter;

import myoodbms.querylanguage.operators.ComparisonObject;

public class Instance {
	
	HashMap<String, Object> attributes;
	HashMap<String, Object> relationships;
	Type type;
	String typename;
	int Version;
	
	public Instance(Type type){ 
		this.attributes= new HashMap<>();
		this.relationships= new HashMap<>();
		this.type = type;
		this.typename= type.typename;
		this.Version = type.Version;
		
		}
	
	public Type getType(){
		return this.type;
	}
	
	public String getTypeName(){
		return this.typename;
	}
	
	public HashMap<String, Object> getAttributes(){
		return this.attributes;
	}
	
	public void setAttributes(HashMap<String, Object> att){
		this.attributes=att;
	}
	
	//attributes
	public void setAttributeValue(String key, Object value){
		
		boolean attrfound=false;
		if(type.attributes.keySet().contains(key) && (value==null || checker2(value, type.attributes.get(key)))){
			attrfound=true;			
		}else if(type.inherited != null && type.inherited.attributes.keySet().contains(key) && (value== null || checker2(value, type.inherited.attributes.get(key)))){			
			attrfound=true;		
		}else
			attrfound=false;

		if(!attrfound)
			System.out.println("Attribute not existent or not valide for type " + type.attributes.get(key));			
		else
			attributes.put(key, value);		
		
	}
	
	public Object getAttributeValue(String key){
		
		//controllare che quell'attributo c'è nel tipo, senno' dire non disponibile,
		//cosi aggiunta e cancellazione di attributi sono automatici
		Class<?> atttype;
		if(type.getAttributes().containsKey(key)){
			if(!checker2(attributes.get(key),type.getAttributes().get(key))){
				try{
					attributes.put(key, type.getAttributes().get(key).cast(attributes.get(key)));
				}catch(ClassCastException ex){
					attributes.put(key, null);
				}				
			}
			return attributes.get(key);
		}else if(type.inherited != null && type.inherited.getAttributes().containsKey(key)){
			if(!checker2(attributes.get(key),type.inherited.getAttributes().get(key))){
				try{
					attributes.put(key, type.inherited.getAttributes().get(key).cast(attributes.get(key)));
				}catch(ClassCastException ex){
					attributes.put(key, null);
				}				
			}
			return attributes.get(key);	
		}else{ //attribute not in Type anymore
			attributes.remove(key);
			System.out.println("Attribute not existing");	
			return null;
		}
		
	}
	
	public HashMap<String, Object> getRelationships(){
		return relationships;
	}
	
	//Relationships
	public void setRelatedObject(String relname, Instance relobject){
		setRelatedObject(relname, relobject,true);
	}
	
	public void setRelatedObject(String relname, Instance relobject, boolean performIntegrity){
		
		Relationship rel = type.getRelationships().get(relname);
		boolean ex = false;
		
		if(relationships.get(relname) == null){ //instanciate relationship
			if(rel.relType == RelationType.MANYTOONE || rel.relType == RelationType.MANYTOMANY){
				List<Instance> objects = new ArrayList<Instance>();
				objects.add(relobject);
				relationships.put(relname, objects);
			}else
				relationships.put(relname, relobject);
		}else{
			if(rel.relType == RelationType.MANYTOONE || rel.relType == RelationType.MANYTOMANY){
				List<Instance> objects= (ArrayList<Instance>)relationships.get(relname);
				objects.add(relobject);
				relationships.put(relname, objects);
			}else{
				System.out.println("Cannot add multiple objects in a ONETOMANY-ONETOONE relationship");	
				ex=true;
			}
			
		}
		
		//Integrity check (vedere nome giusto)
		if(performIntegrity && ex==false && relobject!=null)
			relobject.setRelatedObject(rel.inverseRelation, this, false);
	}
	
  public void removeRelatedObject(String relname, Instance relobject){
	  removeRelatedObject(relname, relobject,true);
   }
	
   public void removeRelatedObject(String relname, Instance relobject, boolean performIntegrity){
		
		Relationship rel = type.getRelationships().get(relname);
		boolean ex = false;
		
		if(relationships.get(relname) == null){ //instanciate relationship
			System.out.println("Relationship not existing");	
			ex=true;
		}else{
			if(rel.relType == RelationType.MANYTOONE || rel.relType == RelationType.MANYTOMANY){
				List<Instance> objects= (ArrayList<Instance>)relationships.get(relname);
				objects.remove(relobject);
				relationships.put(relname, objects);
			}else{
				relationships.remove(relname);
			}
			
		}
		
		//Integrity check (vedere nome giusto)
		if(performIntegrity && ex==false)
			relobject.removeRelatedObject(rel.inverseRelation, this, false);
	}
		
		
	public Object executeMethod(String methodname){
		
		return executeMethod(methodname, new ArrayList<Object>());
	}
	
	public Object executeMethod(String methodname, ArrayList<Object> pars){
		
		Object result=null;
		int i;
		//devo fare match del method
		Method met= findMatchMethods(methodname, pars);
		if(met==null)
			System.out.println("No match method found!");
		else{		
			try{
				Interpreter interp = new Interpreter();
				i=0;
				for (String parname : met.parameters.keySet()) {
					interp.set(parname, pars.get(i));
					i++;
				}
				
				for (HashMap.Entry<String, Object> entry : attributes.entrySet()) {
					interp.set(entry.getKey(), entry.getValue());
				}
				
				//replacing objects coming from relationships (only ONETOMANY-ONETOONE)
				for (HashMap.Entry<String, Relationship> entry : this.type.getRelationships().entrySet()) {
					if(entry.getValue().relType== RelationType.ONETOMANY || entry.getValue().relType== RelationType.ONETOONE) {//OR ONETOONE FARE!!!! x tutte le relationships
						//nel metodo c'è nomerelationship.attributo
						Instance related = (Instance)this.relationships.get(entry.getKey());
						if (related != null){
							for (HashMap.Entry<String, Object> att : related.attributes.entrySet()) {
								interp.set(entry.getKey()+"."+att.getKey(), att.getValue()); //relatedreport.name lo metto al valore dell'attributo name dell'oggetto collegato
							}
						}
					}else{ //MANYTOONE, MANYTOMANY
						ArrayList<Instance> related = (ArrayList<Instance>)this.relationships.get(entry.getKey());
						if (related != null){
								interp.set(entry.getKey(), related);
						}
						
					}
				}
				
				result = interp.eval( met.code);
			} catch ( EvalError e ) {
				EvalError a = e;
				a.getErrorLineNumber();
			}								
		}
			
		return result;
	}


	private boolean checker(Object obj, Class<?> someClass) {
		if(someClass.isPrimitive())
			someClass= Converter.convertToWrapper(someClass);
			
	    return someClass.isInstance(obj);
	}

	private boolean checker2(Object obj, Class<?> someClass) {
		
		if(obj instanceof ComparisonObject)
			return true;		
		else if(someClass.isPrimitive())
			return Converter.convertToWrapper(someClass).equals(obj.getClass());
		else
			return someClass.equals(obj.getClass());
	}
	
	private Method findMatchMethods(String methodname, ArrayList<Object> pars){
		
		Method matchMethod = null;
		
		matchMethod = findMethod(methodname, pars, this.type.getMethods());
				
		//search methods in superclass
		if(matchMethod == null && this.type.inherited != null)
			matchMethod = findMethod(methodname, pars, this.type.inherited.getMethods());

		return matchMethod;
		
	}
	
	private Method findMethod(String methodname, ArrayList<Object> pars, HashMap<String, ArrayList<Method>> methods){
		
		Method matchMethod = null;
		int i;
		
		ArrayList<Method> methodssamename = methods.get(methodname);
		
		if(methodssamename != null){		
			outerloop:
			for (Method m: methodssamename){
				
				if(matchMethod != null)
	    			break;
				
				if (m.parameters.size() != pars.size())
					continue;
				else{
					i=0;
					for (Class<?> value : m.parameters.values()) {
						//if(!(pars[i] instanceof Class.forName("String").class)){
						if(!checker(pars.get(i), value)){
							continue outerloop;
						}
						i++;
					}
					matchMethod=m;				
				}			
			}	
		}
		return matchMethod;
		
	}


}
