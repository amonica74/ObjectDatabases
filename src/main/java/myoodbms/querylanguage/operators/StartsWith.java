package myoodbms.querylanguage.operators;

public class StartsWith extends ComparisonObject{
		
		String start;
		
		public StartsWith(String start){
			
			this.start=start;
		}
		
		public boolean evaluate(Object toeval){
			
			return ((String)toeval).startsWith(start);
			
		}
		
	}

