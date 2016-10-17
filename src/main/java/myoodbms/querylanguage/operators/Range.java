package myoodbms.querylanguage.operators;

// manages also < and >
public class Range extends ComparisonObject{
		
		Number from;
		Number to;
		
		public Range(Number from, Number to){
			
			this.from=from;
			this.to=to;
		}
		
		public boolean evaluate(Object toeval){
			
			if(from != null && to!= null)
				return ((Number)toeval).doubleValue() > from.doubleValue() && ((Number)toeval).doubleValue() <to.doubleValue();
			else if(from!=null)
				return ((Number)toeval).doubleValue() > from.doubleValue();
			else //to !=null
				return ((Number)toeval).doubleValue() <to.doubleValue();
					
			
		}
		
		
	}


