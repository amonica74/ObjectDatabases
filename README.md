# ObjectDatabases
This is a project for ObjectDatabases course (ETH, CAS in Computer Science, SS2016)

***
**IMPORTANT NOTE**<br/>
The code in this project is not optimized, so it can be sometimes redundant or not particularly performant. The aim
of the project was to propose an implementation of some OODBMS features and it is in any case not suitable, as it is, 
for a production environment
***

<h4>Content and goal of the project</h4>

The goal of the project is to create a Object Oriented Database Management System on the top of java by implementing the main 
functionalities that such a system must have.

As a base for the persistence an already existing OODMBS (db4o) is used, but the main functionalities are implemented 
without making use of the ones already built in this product. 
Starting from a set of classes (see representation in _classes.pdf_ files), some connected by a hierarchical structure,
some simply related by a one-to-one or many-to-one  relationship, the software automatically creates the schema 
in the database (DDL operation) and provides some of the features required by a OODBMS.


<H4> Features </H4>


+ _Data Definition Language_: automatic creation and update of schema when loading the database (during the startup of the 
application); the java classes in the template package are scanned by means of the java reflection and the method bodies parsed
by the AST Parser of JDT Plugin for Eclipse; the whole class definitions are stored in the db using the _Type_ class.
The classes in template package are the only ones that the developer should create. During the creation hierarchy, relationships,
multi-value attributes are automatically included in the schema.

+ _Data Manipulation Language_: the classes in serverapp package are automatically generated from template classes (this mapping is not implemented yet). They inherits
from _BaseDbClass_ and are wrapper of the _Instance_ class, which persists the instances of the objects into the db (by allowing creation of new objects, setting of attribute values, setting of related objects)
These classes must be used by the developer in the application. Their methods perform the persistence logic in a totally transparent way to the developer.

+ Automatic update of the schema every time the application starts (it is enought to change a class in the template package and
the modification are immediately reflected into the db). The instances already in the db are automatically updated too when retrieved (create/delete attributes, change of attribute type, when compatible)

+ When retrieving an attribute of an instance or executing a method, the inheritance rules (methods override and members inheritance) are automatically managed. Methods are dynamically executed by using _BeanShell_. The executemethod of the _Instance_ 
class looks for methods in the whole hierarchy, by retrieving the best match in case of overloading.

+ Relationships are specified in the template classes according to ODMG specifications by means of java annotation 
  Example:

          @Relationship (name = "requests”,relatedClass = GenerateRequest.class, 
      relType = RelationType.MANYTOONE, inverseRelation = "requestedReport")

          @Relationship (name = "datasources",relatedClass = ReportDatasource.class,
      relType = RelationType.MANYTOMANY, inverseRelation = "reports")

+ The referential integrity is automatically mantained 
    - When deleting an object, it is deleted from all objects related to it.
    - When deleting or adding an object in a relationship, the inverse relationship is automatically updated (according to the relation type)

+ Query Language

 The query language follows the model proposed by the _QueryByExample_ mechanism of db4o, but at the same time it is implemented to overcome the limitations imposed by the standard. The idea is that the developer creates an object containing all the attribute values and related objects that he/she wants to search for and passes the object to the query mechanism.

  Example: Retrieving all the _GenerateRequest_ object whose filename attribute starts with NCTS substring.

      Query ql = new Query();
      GenerateRequest gentoret= new GenerateRequest();
      gentoret.setFilename(new StartsWith("NCTS"));
      List<GenerateRequest> genlist = ql.queryByExample(gentoret);

 
  The Query Language allows to:
    - Retrieve all objects of a class (types or subtypes)
    -	Retrieve all objects with attribute having default values (null for objects, 0 for numbers), not allowed by standard _QueryByExample_ of db4o
    -	Retrieve attributes in OR with _ContainedIn_
    -	Retrieve attributes with _NoEquals_
    -	Retrieve strings attributes by using _Startswith_ or _Contains_
    -	Retrieve multivalue attributes by using Contains to identify if the attribute contains a certain value
    -	Retrieve numbers with >, <, or both with Range
    -	Retrieve objects on the basis of certain values of their related objects
    -	Retrieve objects that don’t have any related object
    
+ Dynamic Collection 
The system supports Dynamic Collections based on QuerybyExample;  these collections are instantiated from a query and contain only the elements resulting by the execution of the query. The supported features are:
    -	Objects retrieved when accessed with _Iterator_
    -	Intersect of Dynamic Collection
    -	Union of Dynamic Collection
    -	(Related objects not supported)


<h2>Packages List</h2>
+ _moodbms_ – contains the two classes _DBManager_ and _SchemaManager_ for the opening, close and configuration of the database and the management of the schema
+ _myoodbms.dbclasses_ – contains base classes of the oodbms: _Type_, _Instance_, _Method_, _Relationship_, _BaseDBClass_, _RelationType_
+ _myoodbms.dbutils_ – contains helpers for the db operations
+ _myoodbms.querylanguage_ - contains classes used to perform queries and dynamic collections. 
+ _myoodbms.querylanguage.operators_ – contains classes used to extend QueryByExample mechanism of db4o. In order to provide more functionalities, you have simply to create new classes derived from _ComparisonObject_.
+ _myoodbms.template_- contains classes created by the developer, which are  used to generate the database schema.
+ _myoodbms.utils_ –  general utils for the project
+ _serverapp_ - wrapper classes automatically generated from templates. 
These classes inherit from _myoodbms.dbclasses.BaseDBClass_ and wrap the functions needed to provide persistence. They have to used by the developer in their applications.
+ _myoodbms.Test_ – contains classes to test the project. The main 2 classes are _TestApp_, that creates some objects saving them in the db, retrieves them, sets some attributes values and executes some methods, and _TestQuery_, that tests the query mechanism and contains several examples about the different possibilities offered by the Query Language of the system.


<h2>External Libraries</h2> 

+ BeanShell (http://www.beanshell.org)
+ AST Parser of JDT Plugin for Eclipse
+ db40 OODBMS

