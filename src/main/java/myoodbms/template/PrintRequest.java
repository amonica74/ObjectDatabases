package myoodbms.template;
import java.time.Duration;

import myoodbms.dbutils.Version;

@Version(number=3)
public class PrintRequest extends Request{
	
		
	String printerName;
	int trayIndex;
	int numCopies;
	String format;
	
	
	public boolean printDocument(String document){
		
		System.out.println("I'm printing " + document + "on printer "+printerName);
		
		System.out.println("Printed " + numCopies + " copies with format "+format +" from tray "+trayIndex);
		
		return true;
		
	}
	
	public statusenum printActivityReport(){
		
		System.out.println("This is a PRINT REQUEST ");
		System.out.println("The execution lasted "+java.time.Duration.between(startTime, endTime).toMillis());
		
		return status.SUCCESS;
		
	}

}
