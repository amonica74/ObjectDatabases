package myoodbms.template;

import myoodbms.dbclasses.RelationType;
import myoodbms.dbutils.Relationship;
import myoodbms.dbutils.Version;

@Version(number=1)
@Relationship (
		   name = "reports",  //Set<GenerateRequest>
		   relatedClass = ReportDefinition.class,
		   relType = RelationType.MANYTOMANY,
		   inverseRelation = "datasources" //datasources relationship della classe ReportDefinition
		)

public class ReportDatasource {
		
	String connectionString;
	String username;
	String password;
	
	
	public boolean checkConnection(String dbtype, boolean printPassword){
		

		System.out.println ("Checking connection to "+connectionString.replace("$0", "DBTYPE=" +dbtype));
		System.out.println ("With username "+username);
		
		if(printPassword)
			System.out.println ("And password "+password);
		
		return true;
			
	}


}
