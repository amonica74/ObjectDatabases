package myoodbms.dbutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import myoodbms.dbclasses.BaseDBClass;
import myoodbms.querylanguage.operators.ContainedIn;

public class QueryHelper {
	
	//---> in both cases create a new object of type T and return it
	
	//public <T> T intersect(T obj1, T obj2){
	public static <T extends BaseDBClass> T intersect(T obj1, T obj2){
		
		T newobjforquery =null;
		Class<T> cls= (Class<T>) obj1.getClass();
		
		try{
			HashMap<String, Object> att1 = obj1.instance.getAttributes();
			HashMap<String, Object> att2 = obj2.instance.getAttributes();
			
			HashMap<String, Object> att3= new HashMap<String, Object>();
			att3.putAll(att1);
			att3.putAll(att2);
			
			newobjforquery= cls.newInstance();
			//if same attribute is in both objects but with different values, 
			//intersect is empty, so intersect attributes must remain empty
			if(att1.size() + att2.size() == att3.size()) 
				newobjforquery.instance.setAttributes(att3);
			
		}catch(Exception ex){}
	
		return newobjforquery;
		
	}
	
	public static <T extends BaseDBClass> T union(T obj1, T obj2){
		
		T newobjforquery =null;
		Class<T> cls= (Class<T>) obj1.getClass();
		
		try{
			HashMap<String, Object> att1 = obj1.instance.getAttributes();
			HashMap<String, Object> att2 = obj2.instance.getAttributes();
			
			HashMap<String, Object> att3= new HashMap<String, Object>();
			
			for (HashMap.Entry<String, Object> entry : att1.entrySet()) {
				if(att2.containsKey(entry.getKey())){ // if both have the same attribute, put them in OR with ContainedIn 
					att3.put(entry.getKey(), new ContainedIn(new ArrayList<Object>(Arrays.asList(new Object[]{entry.getValue(),att2.get(entry.getKey())}))));
					att2.remove(entry.getKey());
				}else{
					att3.put(entry.getKey(),entry.getValue());
				}
			}
			
			for (HashMap.Entry<String, Object> entry : att2.entrySet()) {
					att3.put(entry.getKey(),entry.getValue());
			}
			
			newobjforquery= cls.newInstance();
		    newobjforquery.instance.setAttributes(att3);
			
		}catch(Exception ex){}
	
		return newobjforquery;
		
	}

}
