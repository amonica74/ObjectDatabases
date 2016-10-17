package test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import serverapp.GenerateRequest;
import serverapp.PrintRequest;
import serverapp.ReportDatasource;
import serverapp.ReportDefinition;

import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

import myoodbms.DBManager;
import myoodbms.SchemaManager;
import myoodbms.dbclasses.Instance;
import myoodbms.dbclasses.Type;


//FARE AGGIORNAMENTO ATTRIBUTI
//DELETE ADD OBJECT IN MANYTOMANY
//ESECUZIONE METODO IN MANY TO MANY
//ESECUZIONE DI TUTTI I METODI
//DELETE DATASOURCE E VEDERE SE LO CANCELLA DA RELATIONSHIP

public class TestApp {
	
	public static void main(String[] args)
	{
		try{
		DBManager.openDB();
		
		SchemaManager sm = new SchemaManager();
		
		//----DELETE sm.deleteSchema();
		//----DELETE INSTANCES sm.deleteInstances();
			
		sm.loadSchema();
		
		/* REPORT DEFINITION CREATION
		
		ReportDefinition repdef= new ReportDefinition();
		repdef.setName("exportList");
		repdef.setTemplate("template1"); 
		repdef.setVersion("1.05"); 
		repdef.setShowInLauncher(true); 
		
		ArrayList<String> supportedoutputtypes = new ArrayList<String>();
		supportedoutputtypes.add("PDF");
		supportedoutputtypes.add("EXCEL");
		
		repdef.setSupportedOutputTypes(supportedoutputtypes);
		
		repdef.store();
		*/
		
		//REPORT DEFINITION CREATION2
		
		/*
		ReportDefinition repdef2= new ReportDefinition();
		repdef2.setName("NCTSGroupageList");
		repdef2.setTemplate("template865"); 
		repdef2.setVersion("1.02"); 
		repdef2.setShowInLauncher(false); 
		
		ArrayList<String> supportedoutputtypes2 = new ArrayList<String>();
		supportedoutputtypes2.add("PDF");
		supportedoutputtypes2.add("WORD");
		
		repdef2.setSupportedOutputTypes(supportedoutputtypes2);
		
		repdef2.store();
		*/
		
		
		/* GENERATE REQUEST CREATION
		LocalTime now = LocalTime.now();
		HashMap<String, String> pars = new HashMap<String, String>();
		pars.put("deckey", "340");
		pars.put("declarantName", "Topolino");
		
		GenerateRequest genreq = new GenerateRequest();
		genreq.setStartTime(now.minusSeconds(8));
		genreq.setEndTime(now);
		genreq.setFilename("exportList.pdf");
		genreq.setLanguage("de");
		genreq.setParameterValues(pars);
		
		genreq.setReportDefinition(repdef);
		
		genreq.store();
		*/
		
		
		/*GENERATE REQUEST 2 CREATION
		LocalTime now2 = LocalTime.now();
		HashMap<String, String> pars2 = new HashMap<String, String>();
		pars2.put("deckey", "880");
		pars2.put("declarantName", "Paperino");
		pars2.put("year","2016");
		
		GenerateRequest genreq2 = new GenerateRequest();
		genreq2.setStartTime(now2.minusSeconds(5));
		genreq2.setEndTime(now2);
		genreq2.setFilename("NCTSImportList.pdf");
		genreq2.setLanguage("de");
		genreq2.setParameterValues(pars2);
		
		genreq2.setReportDefinition(repdef);
		
		genreq2.store();
		*/
		
		/* GENERATE REQUEST CREATION - 3
		LocalTime now3 = LocalTime.now();
		HashMap<String, String> pars3 = new HashMap<String, String>();
		pars3.put("startdatum", "12.04.2016");
		pars3.put("key", "A678");
		
		GenerateRequest genreq3 = new GenerateRequest();
		genreq3.setStartTime(now3.minusSeconds(3));
		genreq3.setEndTime(now3);
		genreq3.setFilename("exportList.pdf");
		genreq3.setLanguage("en");
		genreq3.setParameterValues(pars3);
		
		genreq3.setReportDefinition(repdef2);
		
		genreq3.store();
		*/
		
		
		/*GENERATE REQUEST 2 CREATION -4
		LocalTime now4 = LocalTime.now();
		HashMap<String, String> pars4 = new HashMap<String, String>();
		pars4.put("deckey", "111");
		pars4.put("declarantName", "Clarabella");
		pars4.put("year","2013");
		
		GenerateRequest genreq4 = new GenerateRequest();
		genreq4.setStartTime(now4.minusSeconds(2));
		genreq4.setEndTime(now4);
		genreq4.setFilename("NCTSImportList.pdf");
		genreq4.setLanguage("en");
		genreq4.setParameterValues(pars4);
		
		genreq4.setReportDefinition(repdef2);
		
		genreq4.store();
		*/
		
		
		/*GENERATE REQUEST 2 CREATION -5
		LocalTime now5 = LocalTime.now();
		HashMap<String, String> pars5 = new HashMap<String, String>();
		pars5.put("deckey", "456");
		pars5.put("declarantName", "Topolino");
		pars5.put("year","2016");
		
		GenerateRequest genreq5 = new GenerateRequest();
		genreq5.setStartTime(now5.minusSeconds(2));
		genreq5.setEndTime(now5);
		genreq5.setFilename("NCTSImportList.pdf");
		genreq5.setLanguage("en");
		genreq5.setParameterValues(pars5);
		
		//genreq4.setReportDefinition(repdef2);
		
		genreq5.store();
		*/
		
		
		
		/*PRINT REQUEST CREATION
		LocalTime now3 = LocalTime.now();
		
		PrintRequest preq = new PrintRequest();
		preq.setStartTime(now3.minusSeconds(1));
		preq.setEndTime(now3);
		preq.setPrinterName("PRTREI06");
		preq.setTrayIndex(0);
		preq.setNumCopies(2);
		preq.setFormat("A4");
		
		preq.store();
		*/
		
		/*REPORT DATSOURCE CREATION		
		ReportDatasource ds = new ReportDatasource();
		ds.setConnectionString("jdbc:mysql:$0");
		ds.setUsername("pippo");
		ds.setPassword("aabb");
		
		ds.store();*/
		
		
		/*REPORT DATSOURCE CREATION		
		ReportDatasource ds2 = new ReportDatasource();
		ds2.setConnectionString("jdbc:mysql:$0");
		ds2.setUsername("pippo");
		ds2.setPassword("aabb");
		
		ds2.store();*/
		
		/*
		repdef.setReportDatasource(ds);
		repdef.setReportDatasource(ds2);
		
		repdef.store();
		*/
		
		
		
		ObjectSet<Instance> result = DBManager.db.queryByExample(Instance.class);
		
		
		List<Instance> genreqlist = result.stream().filter(p -> p.getTypeName().equals("GenerateRequest")).collect(Collectors.toList());
		
		GenerateRequest genreq1 = new GenerateRequest(genreqlist.get(0));
		GenerateRequest genreq2 = new GenerateRequest(genreqlist.get(1));
		
		GenerateRequest genreq3 = new GenerateRequest(genreqlist.get(2));
		GenerateRequest genreq4 = new GenerateRequest(genreqlist.get(3));
		GenerateRequest genreqa = new GenerateRequest(genreqlist.get(4));

		
		genreq1.setErrorMessage("ERRORE NELLA REQUEST!!!!!!");
		
		//genreq3.setLanguage("en");
		//genreq3.store();
		
		//genreq2.delete();
		
				
		String filename= genreq1.getFilename();
		LocalTime starttime = genreq1.getStartTime();
		
		myoodbms.template.Request.statusenum reqstatus= genreq1.printActivityReport();
		
		genreq1.generateDocument();
		genreq2.generateDocument();
		
		
		Instance printreqinstance = result.stream().filter(p -> p.getTypeName().equals("PrintRequest")).findAny().orElse(null);
		PrintRequest preq = new PrintRequest(printreqinstance);
		
		myoodbms.template.Request.statusenum printreqstatus= preq.printActivityReport();
		
		boolean res= preq.printDocument("documentToPrint.pdf");
		
		System.out.println(res);
		
		
		List<Instance> repdeflist = result.stream().filter(p -> p.getTypeName().equals("ReportDefinition")).collect(Collectors.toList());

		ReportDefinition rdef = new ReportDefinition(repdeflist.get(0));
		ReportDefinition rdef2 = new ReportDefinition(repdeflist.get(1));
		
		ArrayList<String> scopes = new ArrayList<String>();
		scopes.add("Scope1");
		scopes.add("Scope2");
			
		rdef.printReportInfo(scopes);
		
		List<Instance> dslist = result.stream().filter(p -> p.getTypeName().equals("ReportDatasource")).collect(Collectors.toList());
		
		ReportDatasource ds1 = new ReportDatasource(dslist.get(0));
		ReportDatasource ds2 = new ReportDatasource(dslist.get(1));
		
		//ds2.setConnectionString("odbc:as400:$0");
		//ds2.setUsername("papapapa");
		//ds2.setPassword("zzxxx");
		
		ds1.checkConnection("AS400", true);
		ds2.checkConnection("MSSQL", false);
		
		int numb= rdef.getDatasourcesNumber();
		
		System.out.println(numb);
		
		//rdef2.setRequest(genreq3);
		//rdef2.setReportDatasource(ds2);
		
		//genreq4.delete();
		
		//rdef2.delete();
		
		//ds2.setReportDefinition(rdef);
		//rdef.setReportDatasource(ds2);		
		//rdef.removeReportDataSource(ds2);
		
		//numb= rdef.getDatasourcesNumber();
		
		//System.out.println(numb);
		
		/*
		rdef2.store();
		genreq3.store();
		ds2.store();
		*/
		
		}catch(Exception ex){
			System.out.println(ex);
			
		}finally{
			DBManager.close();
		}
		
		/*
		try{
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		//config.common().objectClass(Author.class).cascadeOnDelete(true);
		db = Db4oEmbedded.openFile("test.db");
		
		
		
		
		ObjectSet<TestClass> result = db.queryByExample(TestClass.class);
		System.out.println(result.size());
		
		TestClass tc = result.get(0);
		//System.out.println(result.get(0).name);
		System.out.println(tc.getSum(20));
		
		TestClass tc2= new TestClass(8,2);
		System.out.println(tc2.getSum(20));
		}finally{
			db.close();
		}*/
		
		/*
		myoodbms.SchemaManager scMgr= new myoodbms.SchemaManager();
		scMgr.createSchema();
		*/
		
		/*
		try{
		Parser.ParseFile(ReportDefinition.class);
		}catch(Exception ex){
			
			String ale="ciao";
		}*/
		
		/*
		Method[] classMethods = Request.class.getDeclaredMethods();
		
		for(Method m: classMethods){
			String ale="ciao";
			ale="q";
		}*/
		
	}

}
