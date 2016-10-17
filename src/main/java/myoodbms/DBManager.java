package myoodbms;

import myoodbms.dbclasses.Instance;
import myoodbms.dbclasses.Type;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

public class DBManager {
	
	public static ObjectContainer db;
	
	public static void openDB(){
		
		EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration(); 
		configuration.common().objectClass(Instance.class).minimumActivationDepth(10);
		configuration.common().objectClass(Type.class).minimumActivationDepth(10);
		configuration.common().objectClass(Type.class).updateDepth(10);
		configuration.common().objectClass(Instance.class).updateDepth(10);
		configuration.common().objectClass(Type.class).cascadeOnDelete(true);
		configuration.common().objectClass(Instance.class).objectField("attributes").cascadeOnDelete(true);
		//configuration.common().objectClass(Instance.class).objectField("relationships").cascadeOnDelete(true);
		
		db= Db4oEmbedded.openFile(configuration, "test.db");
		
		
	}
	
	public static void close(){
		db.close();
	}
	
	
	public static void deleteObject(Object todel){
		db.delete(todel);
	}

}
