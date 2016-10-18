package serverapp;

import java.time.LocalTime;

import myoodbms.dbclasses.BaseDBClass;
import myoodbms.dbclasses.Instance;
import myoodbms.dbutils.Schema;
import myoodbms.template.Request.statusenum;

public class Request extends BaseDBClass{
	 
	 public Request()  {
	    	
	    	instance = new Instance(Schema.types.get(this.getClass().getSimpleName()));
	}

	//used when retrieving the object with a query
	public Request(Instance instance){
		
		this.instance=instance;
		checkVersion();
	}
	
	//setters and getters
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

	public myoodbms.template.Request.statusenum printActivityReport(){
		
		return (myoodbms.template.Request.statusenum)this.instance.executeMethod("printActivityReport");
	}

}
