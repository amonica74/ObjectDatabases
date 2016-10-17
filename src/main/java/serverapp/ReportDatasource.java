package serverapp;

import java.util.ArrayList;

import myoodbms.dbclasses.BaseDBClass;
import myoodbms.dbclasses.Instance;
import myoodbms.dbutils.Schema;
import myoodbms.querylanguage.operators.ComparisonObject;

public class ReportDatasource extends BaseDBClass{
	
	
	 public ReportDatasource()  {
	    	
	    	instance = new Instance(Schema.types.get(this.getClass().getSimpleName()));
	}

	//uso quando tiro fuori con una query
	public ReportDatasource(Instance instance){
		
		this.instance=instance;
		checkVersion();
	}
	
	public void setConnectionString(String connectionString) {		
		this.instance.setAttributeValue("connectionString",connectionString);
	}

	public String getConnectionString(){
		return (String)this.instance.getAttributeValue("connectionString");
	}
	
	
	public void setUsername(String username) {		
		this.instance.setAttributeValue("username",username);
	}

	public String getUsername(){
		return (String)this.instance.getAttributeValue("username");
	}
	
	public void setPassword(String password) {		
		this.instance.setAttributeValue("password",password);
	}

	public String getPassword(){
		return (String)this.instance.getAttributeValue("password");
	}
	
	//Set Object from relationship
	public void setReportDefinition(ReportDefinition rep){
		this.instance.setRelatedObject("reports",rep.getInstance());
	}
	
	//setters for Query
	public void setUsername(ComparisonObject username) {		
		this.instance.setAttributeValue("username",username);
	}
	
	public boolean checkConnection(String dbtype, boolean printPassword){
		
		ArrayList<Object> pars = new ArrayList<Object>();
		pars.add(dbtype);
		pars.add(printPassword);
		
		return (boolean)this.instance.executeMethod("checkConnection",pars);
	}

}
