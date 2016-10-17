package myoodbms.querylanguage.operators;

import java.util.ArrayList;

public class ContainedIn extends ComparisonObject{
	
	ArrayList values;
	
	public ContainedIn (ArrayList values){
		
		this.values=values;
		
	}
	
	
	public boolean evaluate(Object toeval){
		
		return values.contains(toeval);			
		
	}
	

}
