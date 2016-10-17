package myoodbms.template;

import java.time.LocalTime;

import myoodbms.dbclasses.*;
import myoodbms.dbutils.Version;

@Version(number=1)
public class Request {
		
	public static enum statusenum{
	    FAILED,
	    SUCCESS
	}
	LocalTime startTime;
	LocalTime endTime;
	String user;
	String errorMessage;
	statusenum status;
		
	
	public statusenum printActivityReport(){
				
		System.out.println("This is a GENERIC REQUEST");
		System.out.println("Request started at " +startTime);
		System.out.println("Request ended at " + endTime);	
		
		if(errorMessage != "") {
			System.out.println("ERROR:  " + errorMessage);	
			return statusenum.FAILED;
		}else
			return statusenum.SUCCESS;
		
	}

}
