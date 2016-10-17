package myoodbms.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeParameter;


public class Parser {
	
	private static HashMap<String,String> bodys = new HashMap<String,String>();


	//parser.setResolveBindings(true);
	
	//use ASTParse to parse string
	public static void parse(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
 
		cu.accept(new ASTVisitor() {

			
			 public boolean visit(MethodDeclaration node){
		            //System.out.println("Declaration of '"+node.getName()+"' at line"
		                    //+ cu.getLineNumber(node.getStartPosition()));
		            
		            //System.out.println("The body is: " + node.getBody().toString());
		            
		            String pars = node.parameters().toString().replace("[", "(").replace("]", ")");
		            
		            bodys.put(node.getName().toString()+pars, node.getBody().toString());
		            
		            
		            //System.out.println("Pars: " + node.parameters().toString().replace("[", "(").replace("]", ")"));
		            
		           
		                return true;
			 }
		});
 
	}
 
	//read file content into a string
	public static String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
 
		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			//System.out.println(numRead);
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
 
		reader.close();
 
		return  fileData.toString();	
	}
	
	public static void ParseFile(Class<?> classtoparse) throws IOException{

		bodys.clear();
		
		File dirs = new File(".");
		String dirPath = dirs.getCanonicalPath() + File.separator+"src"+File.separator+"myoodbms"+ File.separator+"template"+File.separator;
		String filePath= dirPath + classtoparse.getSimpleName() +".java";
		
		System.out.println(filePath);

		//String filePath="/Users/ale/workspace/objectdatabases/objectdatabases/src/myoodbms/template/Request.java";
		parse(readFileToString(filePath));

	}
	
	public static HashMap<String,String> getBodys(){
		return bodys;
	}

}
