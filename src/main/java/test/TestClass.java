package test;

public class TestClass {
	
	int attr1;
	int attr2;
	
	public TestClass(int attribute1, int attribute2){
		
		this.attr1=attribute1;
		this.attr2=attribute2;
		
	}
	
	public int getSum(int tosum){
		
		return attr1+attr2+90 +tosum;
		
	}

}
