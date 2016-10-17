package myoodbms.querylanguage.operators;

import java.util.List;

public class Contains extends ComparisonObject{
		
		Object obj;
		
		public Contains(Object o){
			
			this.obj=o;
		}
		
		public boolean evaluate(Object toeval){
			
			boolean toret=false;
			
			if (toeval instanceof String)
				toret= ((String)toeval).contains((String)obj);
			else if(toeval instanceof List)
				toret= ((List)toeval).contains(obj);
			
			return toret;
		}
		
		
	}
