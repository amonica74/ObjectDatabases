package myoodbms.dbclasses;

import java.util.*;
import java.util.stream.Collectors;

import com.db4o.ObjectSet;

import myoodbms.DBManager;
import myoodbms.utils.Converter;


public abstract class BaseDBClass {
	
	//private final Instance instance;
	public Instance instance;
	 
		
	public Instance getInstance(){
		
		return instance;
	}
	
	public void checkVersion(){
		
		if(instance.type.Version != instance.Version){
			updateAttributes();
			//da fare updateRelationships
		}
		
	}
	
	public void store(){
		
		
		DBManager.db.store(instance);
	}
	
	public void delete(){
		
		//Mantain relationship integrity 
		removeMeFromRelationship();		
		DBManager.db.delete(instance);
	}
	
	
	private void removeMeFromRelationship(){		
		for (Relationship rel : instance.type.getRelationships().values()){			
			ObjectSet<Instance> result = DBManager.db.queryByExample(Instance.class);
			List<Instance> result2 =  result.stream().filter(p -> p.typename.equals(rel.relatedClass.getSimpleName())).collect(Collectors.toList());
			
			//estraggo dal db le instance con il tipo relatetype
			for (Instance inst : result2){ //chiaro che non tutte avranno questo oggetto nelle loro relazioni
										//pero' devo trovare quelle che le ha
				
				if(rel.relType==RelationType.ONETOONE || rel.relType==RelationType.MANYTOONE){
					Instance todel= (Instance)inst.getRelationships().get(rel.inverseRelation);
					if(todel.equals(this.instance))
						inst.getRelationships().remove(rel.inverseRelation);
				}else{
					ArrayList<Instance> todel= (ArrayList<Instance>)inst.getRelationships().get(rel.inverseRelation);
					todel.remove(this.instance);	// se c'è lo toglie senno' non fa niente			
				}
				
				DBManager.db.store(inst);
			}
			
		}
	}
	
	private void updateAttributes(){
			
		String key;
		for (HashMap.Entry<String, Object> entry : instance.attributes.entrySet()) {

			key=entry.getKey();
			if(instance.type.getAttributes().containsKey(entry.getKey())){  // attribute exist, check type -> CHANGE OF ATTRIBUTE TYPE
				if(!checker2(instance.attributes.get(entry.getKey()),instance.type.getAttributes().get(entry.getKey()))){
					try{
						instance.attributes.put(key, instance.type.getAttributes().get(key).cast(instance.attributes.get(key)));
					}catch(ClassCastException ex){
						instance.attributes.remove(entry.getKey());
					}				
				}
			}else if(instance.type.inherited != null && !instance.type.inherited.getAttributes().containsKey(entry.getKey())){ //DELETE OF AN ATTRIBUTE
				instance.attributes.remove(key);
				System.out.println("Attribute not existing");	
			}
		}
		
		instance.Version= instance.type.Version;
		store();
		
	}
	
	
private boolean checker2(Object obj, Class<?> someClass) {
		
		if(someClass.isPrimitive())
			return Converter.convertToWrapper(someClass).equals(obj.getClass());
		else
			return someClass.equals(obj.getClass());
}

}
