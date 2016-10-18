package serverapp;

import java.util.ArrayList;

import myoodbms.dbclasses.BaseDBClass;
import myoodbms.dbclasses.Instance;
import myoodbms.dbutils.Schema;
import myoodbms.querylanguage.operators.ComparisonObject;

public class ReportDefinition extends BaseDBClass{

	 
	 public ReportDefinition()  {
	    	
	    	instance = new Instance(Schema.types.get(this.getClass().getSimpleName()));
	}

	//used when retrieving the object with a query
	public ReportDefinition(Instance instance){
		
		this.instance=instance;
		checkVersion();
	}
	
	public void setName(String name) {		
		this.instance.setAttributeValue("name",name);
	}

	public String getName(){
		return (String)this.instance.getAttributeValue("name");
	}
	
	public void setTemplate(String template) {		
		this.instance.setAttributeValue("template",template);
	}

	public String getTemplate(){
		return (String)this.instance.getAttributeValue("template");
	}
	
	public void setVersion(String version) {		
		this.instance.setAttributeValue("version",version);
	}

	public String getVersion(){
		return (String)this.instance.getAttributeValue("version");
	}
	
	public void setShowInLauncher(boolean sInLaunch) {		
		this.instance.setAttributeValue("showInLauncher",sInLaunch);
	}

	public boolean getShowInLauncher(){
		return (boolean)this.instance.getAttributeValue("showInLauncher");
	}
	
	public void setSupportedOutputTypes(ArrayList<String> supportedoutputtypes) {		
		this.instance.setAttributeValue("supportedoutputtypes",supportedoutputtypes);
	}

	public ArrayList<String> getSupportedOutputTypes(){
		return (ArrayList<String>)this.instance.getAttributeValue("supportedoutputtypes");
	}
	
	
	//SETTERS FOR QUERY
	public void setSupportedOutputTypes(ComparisonObject supportedoutputtype) {	
		this.instance.setAttributeValue("supportedoutputtypes",supportedoutputtype);
	}
		
	
	//RELATIONSHIPS
	public void setRequest(GenerateRequest req){
		this.instance.setRelatedObject("requests",req.getInstance());
	}
	
	
	public void removeRequest(GenerateRequest req){
		this.instance.removeRelatedObject("requests",req.getInstance());
	}
	
	
	public void setReportDatasource(ReportDatasource rds){
		this.instance.setRelatedObject("datasources",rds.getInstance());
	}
	
	
	public void removeReportDataSource(ReportDatasource rds){
		this.instance.removeRelatedObject("datasources",rds.getInstance());
	}
	
	
	
	//METHODS
	public int getDatasourcesNumber(){
		
		return (int)this.instance.executeMethod("getDatasourcesNumber");
		
	}
	
	public void printReportInfo(ArrayList<String> scopes){
		
		ArrayList<Object> pars = new ArrayList<Object>();
		pars.add(scopes);
		
		this.instance.executeMethod("printReportInfo",pars);
		
	}
	

}
