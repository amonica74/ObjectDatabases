package myoodbms.dbutils;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

import myoodbms.dbclasses.RelationType;

@Repeatable(Relationships.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Relationship {

	public String name();
	public Class relatedClass();
	public RelationType relType();
	public String inverseRelation();


}

