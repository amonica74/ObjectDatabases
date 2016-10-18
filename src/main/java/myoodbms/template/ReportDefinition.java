package myoodbms.template;

import java.util.ArrayList;
import java.util.Set;

import myoodbms.dbclasses.RelationType;
import myoodbms.dbutils.Relationship;
import myoodbms.dbutils.Version;

@Version(number=2)
@Relationship (
		   name = "requests",  //Set<GenerateRequest>
		   relatedClass = GenerateRequest.class,
		   relType = RelationType.MANYTOONE,
		   inverseRelation = "requestedReport" //requestedReport relationship della classe GenerateRequest
		)
@Relationship (
		   name = "datasources",  //Set<GenerateRequest>
		   relatedClass = ReportDatasource.class,
		   relType = RelationType.MANYTOMANY,
		   inverseRelation = "reports" //reports relationship della classe ReportDatasource
		)

public class ReportDefinition {
	
	String name;
	String template;
	String version;	
	ArrayList<String> supportedoutputtypes;
	boolean showInLauncher;
	
	
	Set<ReportDatasource> sources;
	
	public int getDatasourcesNumber(){
		
		System.out.println("This is the amount of datasources "+ sources.size());
		return sources.size();
		
	}
	
	public void printReportInfo(ArrayList<String> scopes){
		
		System.out.println("Name "+ name);
		System.out.println("Version "+ version);
		System.out.println("Supported Output Types");
		for(String outtype : supportedoutputtypes)
			System.out.println(outtype);
		
		System.out.println("Scopes received");
		for(String scope : scopes)
			System.out.println(scope);
		
	}


}
