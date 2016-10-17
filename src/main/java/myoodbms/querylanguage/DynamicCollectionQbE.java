package myoodbms.querylanguage;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import myoodbms.dbclasses.BaseDBClass;
import myoodbms.dbutils.QueryHelper;
import myoodbms.querylanguage.Query.OpType;

import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

public class DynamicCollectionQbE<T extends BaseDBClass> implements Iterable<T> {
	
	
	T objtoret;
	List<T> elements;
	Query _query;
	OpType optype;
	public DynamicCollectionQbE(T toret) {
		
		this.objtoret=toret;
		_query=new Query();
		optype=OpType.AND;
	
	}
	
	public DynamicCollectionQbE(T toret,OpType optype) {
		
		this.objtoret=toret;
		_query=new Query();
		this.optype=optype;
	
	}
	
	public T getObjToRetrieve(){
		return objtoret;
	}
	
	@Override
	public Iterator<T> iterator() {
		Iterator<T> it = new Iterator<T>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
            	if(elements==null){
            		//elements = db.query(pred);
            		elements = _query.queryByExample(objtoret,optype);
            	}
                return currentIndex < elements.size();
            }

            @Override
            public T next() {
                return elements.get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
		return it;
	} 
	
	public Iterator<T> iterator(java.util.function.Predicate<T> pr) {
		/*
		Iterator<T> it = new Iterator<T>() {
			List<T> subelements;
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
            	if(elements==null){
            		elements = db.query(pred);
            	}
            	subelements= elements.stream().filter(pr).collect(Collectors.toList());
                return currentIndex < subelements.size();
            }

            @Override
            public T next() {
                return subelements.get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
		return it;
		*/
		return null;
	} 
	
	
	public DynamicCollectionQbE<T> Union(DynamicCollectionQbE<T> dyntoAdd){
		
		T newbaseclass = QueryHelper.union(objtoret, dyntoAdd.getObjToRetrieve());
		
		return new DynamicCollectionQbE<T>(newbaseclass,OpType.OR);
		
	}
	public DynamicCollectionQbE<T> Intersect(DynamicCollectionQbE<T> dyntoAdd){
		
		T newbaseclass = QueryHelper.intersect(objtoret, dyntoAdd.getObjToRetrieve());
		
		return new DynamicCollectionQbE<T>(newbaseclass);
		
	}
	
	public DynamicCollection<T> Minus(DynamicCollection<T> dyntoAdd){
		
		/*
		return new DynamicCollection<T>(db, new Predicate<T>() {
			private static final long serialVersionUID = 1L;
			public boolean match(T aut) {
		        return pred.match(aut) && !dyntoAdd.getPredicate().match(aut);
		    }
		});*/
		
		return null;
		
	}


}
