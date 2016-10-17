package serverapp;

import java.time.LocalTime;
import java.util.ArrayList;

import myoodbms.dbclasses.BaseDBClass;
import myoodbms.dbclasses.Instance;
import myoodbms.dbutils.Schema;
import myoodbms.querylanguage.operators.ComparisonObject;

public class PrintRequest extends BaseDBClass{
	
	 
	 public PrintRequest()  {
	    	
	    	instance = new Instance(Schema.types.get(this.getClass().getSimpleName()));
	}

	//uso quando tiro fuori con una query
	public PrintRequest(Instance instance){
		
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
	public void setPrinterName(String printerName) {		
		this.instance.setAttributeValue("printerName",printerName);
	}

	public String getPrinterName(){
		return (String)this.instance.getAttributeValue("printerName");
	}
	
	public void setFormat(String format) {		
		this.instance.setAttributeValue("format",format);
	}

	public String getFormat(){
		return (String)this.instance.getAttributeValue("format");
	}
	
	public void setTrayIndex(int trayIndex) {		
		this.instance.setAttributeValue("trayIndex",trayIndex);
	}

	public int getTrayIndex(){
		return (int)this.instance.getAttributeValue("trayIndex");
	}
	
	public void setNumCopies(int numCopies) {		
		this.instance.setAttributeValue("numCopies",numCopies);
	}

	public int getNumCopies(){
		return (int)this.instance.getAttributeValue("numCopies");
	}
	
	//SETTERS FOR QUERY
	public void setNumCopies(ComparisonObject numCopies) {		
		this.instance.setAttributeValue("numCopies",numCopies);
	}
	
	//MY METHODS
	public myoodbms.template.Request.statusenum printActivityReport(){		
		return (myoodbms.template.Request.statusenum)this.instance.executeMethod("printActivityReport");
	}

	//nota!!!, i parametri devono essere messi nell'ArrayList nello stesso ordine della signature
	public boolean printDocument(String document){		
		ArrayList<Object> pars = new ArrayList<Object>();
		pars.add(document);
		
		return (boolean)this.instance.executeMethod("printDocument",pars);
	}

}
