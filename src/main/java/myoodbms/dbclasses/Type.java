package myoodbms.dbclasses;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Collection;
import java.util.ArrayList;

//class used to save schema of classes
public class Type {

	public final String typename;
	public int Version;
	HashMap<String, Class<?>> attributes= new HashMap<String, Class<?>>() ;
	HashMap<String, ArrayList<Method>> methods= new HashMap<String, ArrayList<Method>>() ;
	HashMap<String, Relationship> relationships= new HashMap<String, Relationship>() ; //relationship
	public Type inherited;
	
	
    public Type(String _name, HashMap<String, Class<?>> _attributes){
    	typename=_name;
    	attributes = _attributes;
    	
    }
    
    public void setVersion(int version){
    	this.Version=version;
    }
	   
    public Instance instanciate(){
    	
    	return new Instance(this);
    }
    

    public void setMethods(HashMap<String, ArrayList<Method>> m){
    	
    	this.methods=m;
    }
    
    public void setRelationships(HashMap<String, Relationship> relationships){
    	this.relationships=relationships;
    }
    
    public HashMap<String, Relationship> getRelationships(){
    	return relationships;
    }
 
    public void appendMethod(String mname, Method m){
    	
    	if(this.methods.get(mname) == null){
    	
    		ArrayList<Method> methods = new ArrayList<Method>();
    		methods.add(m);
    		this.methods.put(mname,methods);

    	}else{ //method with same name already there
			
			ArrayList<Method> methods = this.methods.get(mname);
			methods.add(m);
			this.methods.put(mname, methods);
    			
    	}
    }
    
    /* APPEND METHOD WITH ALL THE CHECKS
    public void appendMethod(String mname, Method m){
    	
    	if(this.methods.get(mname) == null){
    	
    		ArrayList<Method> methods = new ArrayList<Method>();
    		methods.add(m);
    		this.methods.put(mname,methods);

    	}else{ //method with same name already there
    		Method sameSigMethod = signatureFound(m);
    		if(sameSigMethod == null){ //same name but different signature -> OVERLOAD allowed   			
    			ArrayList<Method> methods = this.methods.get(mname);
    			methods.add(m);
    			this.methods.put(mname, methods);
    			
    		}else{ //same signature
    			
    			if(inherited != null){ // same signature - could be overriding - check return type
    				if(sameOrSubReturnType(m,sameSigMethod)){ //OVERRIDING correct
    					ArrayList<Method> methods = this.methods.get(mname); //replace the method with the override
    	    			methods.remove(sameSigMethod);
    	    			methods.add(m);
    	    			this.methods.put(mname, methods);   					
    				}else{
    					System.out.println("Overriding not allowed!");       				
    				}
    			}
    		}
    	}
    }*/
    
    public HashMap<String, ArrayList<Method>> getMethods(){
    	
    	return this.methods;
    }
    
   public HashMap<String, Class<?>> getAttributes(){
    	
    	return this.attributes;
    }
   
   public void setAttributes(HashMap<String, Class<?>> atts){
   	 
   		this.attributes=atts;
   }
   
      
    public void inherits(Type c){
    	
    	if (inherited == null){
    		inherited = c;
    		//------ !!!! this.methods=getDeepCopy(c.getMethods());
    		//------ !!!!!this.attributes.putAll(c.getAttributes());
    	}else{
    		System.out.println("Multiple Inheritance not allowed!"); 
    	}
    	
    }
    
    public void replaceInheritance(Type c){
    	inherited = c;  	
    }
    
    private Method signatureFound(Method m){
    	
    	Method foundSignature = null;
    	
    	ArrayList<Method> methods = this.methods.get(m.name);
    	Iterator<Class<?>> paramtypes= m.parameters.values().iterator();
    	
    	int i;
    	outerloop:
    	for (Method met : methods){
    		
    		if(foundSignature != null)
    			break;
    	
	    	 if(met.parameters.values().size() != m.parameters.size()){
	    		 //foundSignature=null;
	    		 continue;
	    	 }else{
	    		i=0;
	    		for (Class<?> c : met.parameters.values()){
	    			if(!paramtypes.next().equals(c)){
	    	    		 //foundSignature=null;
	    				continue outerloop;
	    			}
	    		}
				foundSignature=met;
	    	 }
    		
    	}
    	
    	return foundSignature;
    }
    
    private boolean sameOrSubReturnType(Method childMethod, Method parentMethod){
    	
    	return (childMethod.returnparameter.equals(parentMethod.returnparameter) || 
    			parentMethod.returnparameter.isAssignableFrom(childMethod.returnparameter));
    }
    
    private HashMap<String, ArrayList<Method>> getDeepCopy(HashMap<String, ArrayList<Method>> source) {
    	HashMap<String, ArrayList<Method>> copy = new HashMap<String, ArrayList<Method>>();
    	ArrayList<Method> methods;
    	try{
	        for (HashMap.Entry<String, ArrayList<Method>> entry : source.entrySet()){
	        	methods= new ArrayList<Method>();
	        	for (Method m: entry.getValue())
	        		methods.add(m.clone());
	            copy.put(entry.getKey(), methods);
	        }
    	}catch(CloneNotSupportedException ex){}
        return copy;
    }   
}


