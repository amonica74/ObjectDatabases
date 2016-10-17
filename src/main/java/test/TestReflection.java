package test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestReflection {
	
	String pippo;
	List<String> stringList;
	
	//((ParameterizedType)f.getGenericType()).getActualTypeArguments()[0]

    public static void main(String... args) throws Exception {
    	
    	/*
    	Field[] classFields = TestReflection.class.getDeclaredFields();
		for( Field f: classFields){	
			
			if(f.getType().equals(java.util.List.class) || f.getType().equals(java.util.Set.class))
				System.out.println(((ParameterizedType)f.getGenericType()).getActualTypeArguments()[0]); 
			
		}
		int a;
		
	    Method[] classMethods = TestReflection.class.getDeclaredMethods();
	    for(Parameter p : classMethods[1].getParameters()){
	    	
	    	try{
	    		a = 2;
	    	//Type a = ((ParameterizedType)p.getParameterizedType()).getActualTypeArguments()[0];
	    		String nome = ((ParameterizedType)p.getParameterizedType()).getActualTypeArguments()[0].getTypeName();
	    		String nome2 =p.getType().getSimpleName().toString() +"<"+nome.substring(nome.lastIndexOf(".")+1)+">"; 
	    	}catch(Exception ex){
	    		
	    		a=3;
	    	}
	    }*/
    	/*
        Field stringListField = Test.class.getDeclaredField("stringList");
        ParameterizedType stringListType = (ParameterizedType) stringListField.getGenericType();
        Class<?> stringListClass = (Class<?>) stringListType.getActualTypeArguments()[0];
        System.out.println(stringListClass); // class java.lang.String.

        Field integerListField = Test.class.getDeclaredField("integerList");
        ParameterizedType integerListType = (ParameterizedType) integerListField.getGenericType();
        Class<?> integerListClass = (Class<?>) integerListType.getActualTypeArguments()[0];
        System.out.println(integerListClass); // class java.lang.Integer.
        */
    	
    	HashMap<String, Object> map1=	new HashMap<String, Object>();
    	HashMap<String, Object> map2= new HashMap<String, Object>();
    	map1.put("ale","aaa");
    	map1.put("vcz","bbb");
    	map2.put("vcz","ddd");
    	map2.put("qwer","ccc");
    	
    	HashMap<String, Object> map3= new HashMap<String, Object>();
    	map3.putAll(map1);
    	map3.putAll(map2);
    	
    	int a=5;
    	a++;
    	
    }
    
    public void testmet(int ale, ArrayList<String> bbb){
    	int a= 5;
    }
    
    


}
