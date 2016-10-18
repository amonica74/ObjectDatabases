package myoodbms.template;

import java.util.HashMap;

import myoodbms.dbclasses.*;
import myoodbms.dbutils.Relationship;
import myoodbms.dbutils.Version;

@Version(number=6)
@Relationship (name = "requestedReport",
relatedClass = ReportDefinition.class, //ReportDefinition
		   relType = RelationType.ONETOMANY,
		   inverseRelation = "requests" //requestsForThisReport relationship of class ReportDefinition
		)
public class GenerateRequest extends Request{
		
	HashMap<String, String> parameterValues;
	String filename; //nome da salvare
	String language;
	ReportDefinition repdef;
	
	public boolean generateDocument(){
		
		//nel Type the reference to the object, in this case repdef, is replaces
		//with the name of the relationship => in this case requestedReport.name
		System.out.println("I'm generating document for Report Definition " + repdef.name + " with the following parameters :");
		
		for (java.util.Map.Entry entry : parameterValues.entrySet()){
			System.out.println("Name: " +entry.getKey() +" Value: "+entry.getValue());			
			
		}
					
		System.out.println("The language used is " + language + " and I will save the document as "+filename);
		
		return true;
		
	}
	

}
