package test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import serverapp.GenerateRequest;
import serverapp.PrintRequest;
import serverapp.ReportDatasource;
import serverapp.ReportDefinition;
import serverapp.Request;
import myoodbms.DBManager;
import myoodbms.SchemaManager;
import myoodbms.dbclasses.Instance;
import myoodbms.querylanguage.DynamicCollectionQbE;
import myoodbms.querylanguage.Query;
import myoodbms.querylanguage.Query.OpType;
import myoodbms.querylanguage.operators.ContainedIn;
import myoodbms.querylanguage.operators.Contains;
import myoodbms.querylanguage.operators.NotEquals;
import myoodbms.querylanguage.operators.Range;
import myoodbms.querylanguage.operators.StartsWith;

import com.db4o.ObjectSet;
import com.db4o.query.Predicate;


public class TestQuery {
	
	
	public static void main(String[] args)
	{
	try{
		DBManager.openDB();
		
		System.out.println("----------------------1");
		
        SchemaManager sm = new SchemaManager();
			
		sm.loadSchema();
		System.out.println("----------------------2");
		
		Query ql = new Query();
		
		/*standard QueryByEmample, all instances*/
		
		//List<ReportDefinition> replist= ql.queryByExample(ReportDefinition.class);		
		//List<GenerateRequest> genlist= ql.queryByExample(GenerateRequest.class);
		List<Request> reqlist= ql.queryByExample(Request.class);
		
		//for(Request req : reqlist)
			//req.printActivityReport();
		
		
		
		/* Query by example with attributes in AND and in OR*/
		GenerateRequest gentoret= new GenerateRequest();
		gentoret.setFilename("exportList.pdf");
		gentoret.setErrorMessage(null);
		
		List<GenerateRequest> genlist1 = ql.queryByExample(gentoret);
		List<GenerateRequest> genlist2 = ql.queryByExample(gentoret,Query.OpType.OR);
		
		for(GenerateRequest req : genlist1)
			req.printActivityReport();
		
		System.out.println("----------------------");
		
		for(GenerateRequest req : genlist2)
			req.printActivityReport();
		
		
		/* attribute string StartsWith */
		GenerateRequest gentoret2= new GenerateRequest();
		gentoret2.setFilename(new StartsWith("NCTS"));
		
		List<GenerateRequest> genlist3 = ql.queryByExample(gentoret2);
		
		
		/* attribute string contains substring (suitable also for MULTIVALUE ATTRIBUTES)*/
		GenerateRequest gentoret3= new GenerateRequest();
		gentoret3.setFilename(new Contains("export"));
		
		List<GenerateRequest> genlist4 = ql.queryByExample(gentoret3);
		
		
		/* attribute with related object in AND and in OR*/
		GenerateRequest gentoret4= new GenerateRequest();
		gentoret4.setFilename(new StartsWith("NCTS"));
		
		ReportDefinition repdef4= new ReportDefinition();
		repdef4.setName("exportList");
		gentoret4.setReportDefinition(repdef4);
		
		List<GenerateRequest> genlist5 = ql.queryByExample(gentoret4,OpType.OR);
		List<GenerateRequest> genlist7 = ql.queryByExample(gentoret4);
		
		
		/* values of attributes in OR */
        // language ='de' OR language='en'
		GenerateRequest gentoret5= new GenerateRequest();
		
		gentoret5.setLanguage(new ContainedIn(new ArrayList<String>(Arrays.asList(new String[]{"de","fr"}))));
	
		
		List<GenerateRequest> genlist6 = ql.queryByExample(gentoret5);
		
		
		
		/*Requests that don't have any ReportDefinition*/
		GenerateRequest gentoret8= new GenerateRequest();
		
		gentoret8.setReportDefinition(null);
	
		
		List<GenerateRequest> genlist8 = ql.queryByExample(gentoret8);
		
		
		/* MULTIVALUE ATTRIBUTES with contains */
		ReportDefinition repdef= new ReportDefinition();
		repdef.setSupportedOutputTypes(new Contains("WORD"));
		
		GenerateRequest gen = new GenerateRequest();
		gen.setFilename("exportList.pdf");
		repdef.setRequest(gen);
		
		List<ReportDefinition> repdeflist = ql.queryByExample(repdef,OpType.OR);
		
		System.out.println("----------------------");
		
		
		/* Test with many-to-many relationship */
		ReportDatasource ds = new ReportDatasource();
			
		ds.setUsername(new StartsWith("p"));
		ReportDefinition repdef2= new ReportDefinition();
		repdef2.setVersion("1.02");
		ds.setReportDefinition(repdef2);
		
		List<ReportDatasource> repdslist = ql.queryByExample(ds);
		
		
		/* Test with integer <4 with Range */
		PrintRequest printreq= new PrintRequest();
		printreq.setNumCopies(new Range(null,4));
		
		List<PrintRequest> preqlist = ql.queryByExample(printreq);
		
	
		/* Dynamic collection filtered on filename and language of GenerateRequest */
		GenerateRequest gentoret9= new GenerateRequest();
		gentoret9.setFilename("exportList.pdf");
		gentoret9.setLanguage("en");
		
		DynamicCollectionQbE<GenerateRequest> authcoll = new DynamicCollectionQbE<GenerateRequest>(gentoret9);
		
	
		GenerateRequest reqfound;
	Iterator<GenerateRequest> myit= authcoll.iterator();
	while(myit.hasNext()){
		reqfound= (GenerateRequest)myit.next();
		System.out.println(reqfound.getFilename());
	}
	
	System.out.println("----------------------");
	
	/* second DynamicCollection */
	GenerateRequest gentoret10= new GenerateRequest();
	gentoret10.setLanguage("de");
	
	DynamicCollectionQbE<GenerateRequest> authcoll2 = new DynamicCollectionQbE<GenerateRequest>(gentoret10);
	
	//INTERSECT coll
	//DynamicCollectionQbE<GenerateRequest> authcoll3 = authcoll.Intersect(authcoll2);
	//UNION coll
	DynamicCollectionQbE<GenerateRequest> authcoll3 = authcoll.Union(authcoll2);

	GenerateRequest reqfound2;
Iterator<GenerateRequest> myit2= authcoll3.iterator();
while(myit2.hasNext()){
	reqfound2= (GenerateRequest)myit2.next();
	System.out.println(reqfound2.getFilename() + "  "+reqfound2.getLanguage());
}

		
		int a= 5;
		a+=1;
		
	}catch(Exception ex){
		System.out.println(ex);
		
	}finally{
		DBManager.close();
	}
	

	}
}
