package myoodbms.querylanguage;

import com.db4o.query.Predicate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import serverapp.GenerateRequest;

import com.db4o.ObjectSet;

import myoodbms.DBManager;
import myoodbms.dbclasses.BaseDBClass;
import myoodbms.dbclasses.Instance;
import myoodbms.dbclasses.Type;
import myoodbms.querylanguage.operators.ComparisonObject;

public class Query {
	
	public static enum OpType{
	    AND,
	    OR
	}
	

		
    public <T> List<T> queryByExample(Class<?> classtoquery){
    	
    	List<Object> ret = new ArrayList<Object>();
    	  
    	try{
		java.lang.reflect.Constructor<?> constructor = classtoquery.getConstructor(Instance.class);		
		
		ObjectSet<Instance> result = DBManager.db.queryByExample(Instance.class);
				
		//fare stessa cosa nel querybyexample sotto
		List<Instance> ilist = result.stream().filter(p -> (
				p.getTypeName().equals(classtoquery.getSimpleName())
				|| (p.getType().inherited!=null && p.getType().inherited.typename.equals(classtoquery.getSimpleName()))
				)).collect(Collectors.toList());

		Object newinstance;
		for(Instance inst: ilist){
				newinstance = constructor.newInstance(inst);
				ret.add(newinstance);
		}
		
    	}catch(Exception ex){}
    	
    	return (List<T>) ret;
    	
	}
    
	public <T> List<T> queryByExample(BaseDBClass toretrieve){
		
		return queryByExample(toretrieve, OpType.AND);
		
	}
    
	
	
public <T> List<T> queryByExample(BaseDBClass toretrieve, OpType optype){
	
	Instance cont = toretrieve.getInstance();
	java.lang.reflect.Constructor<?> constructor;
	List<Object> ret = new ArrayList<Object>();
	
	try{
		constructor = toretrieve.getClass().getConstructor(Instance.class);	

	List <Instance> ilist = DBManager.db.query(new Predicate<Instance>() {
	    public boolean match(Instance toret) {
	    	boolean result = true;
	    	boolean partialresult=true;
	    	boolean relationshipresult=true;
	    	if(optype == OpType.OR){
	    		result=false;
		    	partialresult=false;
		    	relationshipresult=false;
	    	}
	    	if(toret.getTypeName().equals(toretrieve.getClass().getSimpleName())){
	    		
	    		if(cont.getAttributes().size() == 0 && cont.getRelationships().size() == 0)
	    		  return false;
	    		else{
		    		result = compareAttributes(cont,toret,optype);
			    	
			    	for (HashMap.Entry<String, Object> entryrel : cont.getRelationships().entrySet()) {
			    		
			    		Instance toretrieve;
			    		if(entryrel.getValue() instanceof List)
			    			toretrieve= (Instance)((ArrayList)entryrel.getValue()).get(0);
			    		else
			    			toretrieve= (Instance)entryrel.getValue();	    		
	
			    		if(toret.getRelationships().get(entryrel.getKey()) != null){
			    			ArrayList<Instance> tocompare;
			    			Instance tocom;
			    			
			    			if(toret.getRelationships().get(entryrel.getKey()) instanceof List){
			    				if(((ArrayList)toret.getRelationships().get(entryrel.getKey())).size() ==0 && toretrieve==null)
			    					partialresult=true;
			    				else{
				    				tocompare = (ArrayList)toret.getRelationships().get(entryrel.getKey()); 
				    				for(Instance insttocomp: tocompare){
				    					partialresult = compareAttributes(toretrieve,insttocomp,OpType.AND);
				    					
				    					if(partialresult==true)
				    						break;  					
				    				}	
			    				}
			    			}else{
			    				//partialresult=true;
			    				tocom = (Instance)toret.getRelationships().get(entryrel.getKey());
			    				if(toretrieve == null)
			    					partialresult=false;
			    				else
			    					partialresult = compareAttributes(toretrieve,tocom,optype);
			    			}
						}else{
							if(toretrieve ==null)
								relationshipresult=true;
							else
								relationshipresult=false;
						}
			    		
			    		if(optype == OpType.AND)
			    			relationshipresult =relationshipresult && partialresult;
			    		else
			    			relationshipresult =relationshipresult || partialresult;
			    		
			    	}
	    	  }	//attributes or relationships not size 0 - this happens when intersecting 2 collections with different values for same attribute (see QueryHelper, Intersect method)	    	
	    	}else
	    		return false;
	    	
	    	if(optype == OpType.AND)
	    		return (result && relationshipresult) ;
	    	else
	    		return (result || relationshipresult) ;
    		
	    }
	});

	Object newinstance;
	for(Instance inst: ilist){
			newinstance = constructor.newInstance(inst);
			ret.add(newinstance);
	}
	
	}catch(Exception ex){}
	
	return (List<T>) ret;
	
}

private boolean compareAttributes(Instance toretrieve, Instance tocompare, OpType optype){
	
	boolean result=true;
	if(optype == OpType.OR)
		result=false;
	for (HashMap.Entry<String, Object> entry : toretrieve.getAttributes().entrySet()) {
		if(entry.getValue() !=null){
			if (entry.getValue() instanceof ComparisonObject){
				if(optype == OpType.AND)
					result= result && ((ComparisonObject)entry.getValue()).evaluate(tocompare.getAttributes().get(entry.getKey()));		
				else
					result= result || ((ComparisonObject)entry.getValue()).evaluate(tocompare.getAttributes().get(entry.getKey()));	
			}else{ //EQUALS case	    			
    			if(optype == OpType.AND)
    				result= result && tocompare.getAttributes().get(entry.getKey()).equals(entry.getValue());
    			else
    				result= result || tocompare.getAttributes().get(entry.getKey()).equals(entry.getValue());
			}
		}
		else{ //NULL case
			if(optype == OpType.AND)
				result=result && tocompare.getAttributes().get(entry.getKey()) == null;
			else
				result=result || tocompare.getAttributes().get(entry.getKey()) == null;
		}
	}
	
	return result;
	
}


}
