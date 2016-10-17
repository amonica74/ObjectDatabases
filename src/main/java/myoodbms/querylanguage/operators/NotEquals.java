package myoodbms.querylanguage.operators;

public class NotEquals extends ComparisonObject{
	
	Object obj;
	
	public NotEquals(Object obj){
		
		this.obj=obj;
	}
	
	public boolean evaluate(Object toeval){
		
		return (!toeval.equals(obj));
		
	}

}
