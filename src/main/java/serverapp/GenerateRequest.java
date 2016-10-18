package serverapp;

import java.time.LocalTime;
import java.util.HashMap;

import myoodbms.dbclasses.BaseDBClass;
import myoodbms.dbclasses.Instance;
import myoodbms.dbutils.Schema;
import myoodbms.querylanguage.operators.ComparisonObject;

public class GenerateRequest extends BaseDBClass{
	
	 
	 public GenerateRequest()  {
	    	
	    	instance = new Instance(Schema.types.get(this.getClass().getSimpleName()));
	}

	//used when I retireve the object with a query
	public GenerateRequest(Instance instance){
		
		this.instance=instance;
		checkVersion();
	}
	
	//INHERITED setters and getters
	public void setStartTime(LocalTime stime) {		
		this.instance.setAttributeValue("startTime",stime);
	}

	public LocalTime getStartTime(){
		return (LocalTime)this.instance.getAttributeValue("startTime");
	}
	
	public void setEndTime(LocalTime etime) {		
		this.instance.setAttributeValue("endTime",etime);
	}

	public LocalTime getEndTime(){
		return (LocalTime)this.instance.getAttributeValue("endTime");
	}

	public void setUser(String user) {		
		this.instance.setAttributeValue("user",user);
	}

	public String getUser(){
		return (String)this.instance.getAttributeValue("user");
	}
	
	public void setErrorMessage(String mess) {		
		this.instance.setAttributeValue("errorMessage",mess);
	}

	public String getErrorMessage(){
		return (String)this.instance.getAttributeValue("errorMessage");
	}
	
	public void setStatus(myoodbms.template.Request.statusenum reqstatus) {		
		this.instance.setAttributeValue("status",reqstatus);
	}

	public myoodbms.template.Request.statusenum getStatus(){
		return (myoodbms.template.Request.statusenum)this.instance.getAttributeValue("status");
	}
	
	
	// MY setters and getters
	public void setFilename(String filename) {		
		this.instance.setAttributeValue("filename",filename);
	}

	public String getFilename(){
		return (String)this.instance.getAttributeValue("filename");
	}
	
	public void setLanguage(String lang) {		
		this.instance.setAttributeValue("language",lang);
	}

	public String getLanguage(){
		return (String)this.instance.getAttributeValue("language");
	}
	
	public void setParameterValues(HashMap<String, String> pars){
		this.instance.setAttributeValue("parameterValues",pars);
	}
	
	public HashMap<String, String> getParameterValues(){
		return (HashMap<String, String>)this.instance.getAttributeValue("parameterValues");
	}
	
	//SETTERS FOR QUERY
	public void setFilename(ComparisonObject filename) {	
		this.instance.setAttributeValue("filename",filename);
	}
	public void setLanguage(ComparisonObject language) {	
		this.instance.setAttributeValue("language",language);
	}
	
	
	//Set Object from relationship
	public void setReportDefinition(ReportDefinition rep){
		if(rep==null) //for query purposes
			this.instance.setRelatedObject("requestedReport",null);
		else
			this.instance.setRelatedObject("requestedReport",rep.getInstance());
	}
	
	//INHERITED METHODS
	public myoodbms.template.Request.statusenum printActivityReport(){		
		return (myoodbms.template.Request.statusenum)this.instance.executeMethod("printActivityReport");
	}

	//MY METHODS
	public boolean generateDocument(){		
		return (boolean)this.instance.executeMethod("generateDocument");
	}
	

}
