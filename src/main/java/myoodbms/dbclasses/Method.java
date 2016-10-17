package myoodbms.dbclasses;

import java.util.HashMap;

	
public class Method implements Cloneable{
		
		String name;
		HashMap<String, Class<?>> parameters;
		Class<?> returnparameter;
		String code;
		
		 public Method(String name, HashMap<String, Class<?>> parameters,Class<?> returnparameter,String code){
				this.name=name;
				this.parameters=parameters;
				this.returnparameter=returnparameter;
				this.code=code;
		    	
		    }
		 
		 public Method clone() throws CloneNotSupportedException {
		        return (Method) super.clone();
		    }
		
	}


