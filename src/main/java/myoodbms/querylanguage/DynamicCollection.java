package myoodbms.querylanguage;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import myoodbms.dbclasses.Instance;

import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;


public class DynamicCollection<T> implements Iterable<T> {

	Predicate<T> pred;
	List<T> elements;
	ObjectContainer db;
	public DynamicCollection(ObjectContainer db,Predicate<T> pred) {
		
		this.pred=pred;
		this.db=db;
		
		/*
		this.pred2= new Predicate<Instance>() {
			private static final long serialVersionUID = 1L;
			public boolean match(Instance inst) {
		        return inst.getTypeName().equals(pred.extentType().getSimpleName()) && pred.match;
		    }
		}*/
	
	}
	
	public Predicate<T> getPredicate(){
		return pred;
	}
	
	@Override
	public Iterator<T> iterator() {
		Iterator<T> it = new Iterator<T>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
            	if(elements==null){
            		elements = db.query(pred);
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
	} 
	
	
	public DynamicCollection<T> Union(DynamicCollection<T> dyntoAdd){
		
		return new DynamicCollection<T>(db, new Predicate<T>() {
			private static final long serialVersionUID = 1L;
			public boolean match(T aut) {
		        return pred.match(aut) || dyntoAdd.getPredicate().match(aut);
		    }
		});
		
	}
	public DynamicCollection<T> Intersect(DynamicCollection<T> dyntoAdd){
		
		return new DynamicCollection<T>(db, new Predicate<T>() {
			private static final long serialVersionUID = 1L;
			public boolean match(T aut) {
		        return pred.match(aut) && dyntoAdd.getPredicate().match(aut);
		    }
		});
		
	}
	
	public DynamicCollection<T> Minus(DynamicCollection<T> dyntoAdd){
		
		return new DynamicCollection<T>(db, new Predicate<T>() {
			private static final long serialVersionUID = 1L;
			public boolean match(T aut) {
		        return pred.match(aut) && !dyntoAdd.getPredicate().match(aut);
		    }
		});
		
	}

}
