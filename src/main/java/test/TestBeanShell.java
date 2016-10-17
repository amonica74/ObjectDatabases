package test;

import java.util.HashMap;

import myoodbms.dbclasses.Instance;
import myoodbms.dbclasses.RelationType;
import myoodbms.dbclasses.Relationship;
import bsh.EvalError;
import bsh.Interpreter;

public class TestBeanShell {	
	
	public static void main(String[] args)
	{
		
		Interpreter interp = new Interpreter();
		HashMap<String, String> parameterValues=new HashMap<String, String>();
		parameterValues.put("primo", "ciao");
		parameterValues.put("secondo", "ciao2");
		

		System.out.println("Size "+ parameterValues.size());
		
		String code = "System.out.println(\"Size \"+ parameterValues.size()); for (java.util.Map.Entry entry : parameterValues.entrySet()){System.out.println(\"Name: \" +entry.getKey() +\" Value: \"+entry.getValue());}";
			
		String datasourceConnection =  "jdbc:mydb:$0";
		String dbtype = "AS400";
		String code1 = "System.out.println(datasourceConnection.replace(\"$0\", \"DBTYPE=\" +dbtype));";
		
		
		try{
	    interp.set("parameterValues", parameterValues);
	    interp.set("datasourceConnection", datasourceConnection);
	    interp.set("dbtype", dbtype);
	    
		
		interp.eval(code1);
		}catch ( EvalError e ) {
		    // Error evaluating script
			EvalError a = e;
			a.getErrorLineNumber();
			}	
		
		
		
	}

}
