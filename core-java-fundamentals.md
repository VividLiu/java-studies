- [Core Java Fundamentals](#core-java-fundamentals)
  - [Chapter 4. Objects and Classes](#chapter-4-objects-and-classes)
    - [Classes](#classes)
    - [Relationships between Classes](#relationships-between-classes)
    - [Using Pre-defined classes](#using-pre-defined-classes)
    - [Defining your own classes](#defining-your-own-classes)
      - [Explicit vs Implicit parameters](#explicit-vs-implicit-parameters)
      - [Benefits of Encapsulation](#benefits-of-encapsulation)
      - [Class-based access privileges](#class-based-access-privileges)
      - [Final Instance Fields](#final-instance-fields)
    - [Static Fields and Methods](#static-fields-and-methods)
      - [main method](#main-method)
    - [Method Parameters](#method-parameters)
    - [Object Construction](#object-construction)
      - [Overloading](#overloading)
      - [Constructor with No Arguments](#constructor-with-no-arguments)
      - [Explicit Field initialization](#explicit-field-initialization)
      - [Parameter Name](#parameter-name)
      - [Calling Another Constructor](#calling-another-constructor)
      - [Initialization Blocks](#initialization-blocks)
      - [initialization mechanics](#initialization-mechanics)
      - [static initialization block](#static-initialization-block)
        - [hello world without main](#hello-world-without-main)
      - [useful features for constructors](#useful-features-for-constructors)
      - [Object Destruction and finalize method](#object-destruction-and-finalize-method)
    - [Packages](#packages)
      - [import](#import)
      - [locating packages](#locating-packages)
      - [static imports](#static-imports)
      - [Adding class into a package](#adding-class-into-a-package)
      - [Package Scope](#package-scope)
      - [Class Path](#class-path)
        - [Setting the Class Path](#setting-the-class-path)
      - [Documentation Comments](#documentation-comments)
        - [Class Comments](#class-comments)
        - [Method Comments](#method-comments)
        - [Field Comments](#field-comments)
        - [General Comments](#general-comments)
        - [Package and Overview Comments](#package-and-overview-comments)
        - [Comment Extraction](#comment-extraction)
    - [Class Design Hints](#class-design-hints)
  - [Inheritance](#inheritance)
    - [Classes, Superclasses and Subclasses](#classes-superclasses-and-subclasses)
      - [super in constructor](#super-in-constructor)
      - [Inheritance Hierarchies](#inheritance-hierarchies)
      - [Polymorphism](#polymorphism)
      - [dynamic binding](#dynamic-binding)
      - [Preventing Inheritance: Final Classes and Methods](#preventing-inheritance-final-classes-and-methods)
      - [Casting](#casting)
      - [Abstract Classes](#abstract-classes)
      - [Protected Access](#protected-access)
    - [Object: The Cosmic Superclass](#object-the-cosmic-superclass)
      - [equals Method](#equals-method)
        - [Equality Testing and Inheritance](#equality-testing-and-inheritance)
        - [Perfect equals method](#perfect-equals-method)
        - [Tips and Caution](#tips-and-caution)
      - [The hashCode Method](#the-hashcode-method)
      - [The toString Method](#the-tostring-method)
    - [Generic Array List](#generic-array-list)
      - [Accessing Array List Elements](#accessing-array-list-elements)
      - [Compatibility between Typed and Raw Array Lists](#compatibility-between-typed-and-raw-array-lists)
    - [Object Wrappers and Autoboxing](#object-wrappers-and-autoboxing)
    - [Methods with Variable Number of Parameters](#methods-with-variable-number-of-parameters)
    - [Enumeration Classes](#enumeration-classes)
    - [Reflection](#reflection)
      - [The Class Class](#the-class-class)
      - [Primer on Catching Exceptions](#primer-on-catching-exceptions)
      - [Using Reflection to Analyze the Capabilities of Classes](#using-reflection-to-analyze-the-capabilities-of-classes)

# Core Java Fundamentals

## Chapter 4. Objects and Classes

### Classes

**class** template from which objects are made

**construct** create an **instance** of the class

**encapsulation** or **information hiding** hides implementation detail from user of the object

**instance fields** bits of data in an object

**methods** procedures that operate on data

**state** set of values that are part of the current object

**Object** cosmic super-class that all other classes inherit from

**identity** how is the object is distinguished from others that may have the same behavior

### Relationships between Classes

- **Dependence** "uses-a" one class nees to access another object
- **Aggregation** "has-a" one object contains many of another type of object
- **Inheritance** "is-a" expresses relationship between a more special and general class

### Using Pre-defined classes

**constructor** methods always has the same name as the class name

``java

// call constructor with new keyword
Date date = new Date();
``

**objects** variables store a reference to an object that is stored somewhere else.

```c++
Date birthday; // Java

Date* birthday; // C++
```

**mutator** methods change instance fields on an object. They are typically named with a "set" prefix.

**accessor** methods only look up the state of the object. Typically are prefixed with get methods.

### Defining your own classes

```java
class ClassName
{
    field1
    field2
    // ...
    constructor1
    constructor2
    //...
    method1
    method2
    //...
}
```

Name of source file must match name of the public class in the file

- can only have one public class in one source file
- source file can contain many non-public classes
- class with main function is the entry point of the program

Two ways to compile multiple source files

1. can invoke compiler using wildcards or supply multiple files
1. supply the entry point class file to the compiler; compiler will search and resolve dependency files; If the dependency files are newer than the compiled versions the compiler will automatically compile them (similar to make)

**constructor** characteristics

- same name as the class
- can have more than one constructor
- can take zero or more parameters
- has no return value
- is always called with the new operator (can not be invoked directly)

Don't introduce local variables with the same as the instance fields. Nasty bugs!

```java
class Employee() {
    private String name;
    private double salary;
    public Employee(String n, double s, ...) {
        String name = n; // Error
        double salary = s; // Error these are shadowing the instance fields
    }
}
```

#### Explicit vs Implicit parameters

Calling object is the implicit parameter to each methods

- **this** is the parameter that can be used to refer to the implicit parameter

**explicit** parameters are explicitly listed in the method declarations

#### Benefits of Encapsulation

Create field accessor methods rather than declaring fields public

- private data field
- public field accessor
- public field mutator


**Caution** DO NOT Return reference to mutable objects in accessor. Return a clone of the mutable object to prevent mutation outside of the class.

```java
class Employee
{
    private Date hireDay;

    public Date getHireDay() {
        return hireDay;
    }
}
```

#### Class-based access privileges

**methods** can access the private data of all objects of its class

```java

class Employee
{
    ...
    public boolean equals(Employee other) {
        return name.equals(other.name);
    }
}
```

#### Final Instance Fields

final instance fields it must be initialized when the object is constructed. The value can not be changed after it is set

```java
class Employee
{
    // you must guarantee that the field value
    //     has been set after the end of every
constructor.
    private final String name;
}
```

**final** operator is mostly useful when a field is a primitive or immutable type class. It can be confusing if the final field refers to a mutable object such as Date.

```java
private final Date hiredate; // Date reference can not change but Date value can change
```

### Static Fields and Methods

**static** fields and methods belong to the class and not individual objects

- synonymous wtih class fields and class methods
- static constants is most common use

```java
public class Math
{
    // without static PI would be instance field
    public static final double PI = 3.14159
}
```

- public fields not good idea but public constants are fine since they can't be changed
- **native** methods can change value of final variable because they bypass Java programming language (because they are not implemented in Java language)
- example System.out is set by Java

- static methods
  - don't have **this** parameter since they don't operate on objects
  - can't access instance fields but can access static fields
  - can be called from object instance but the syntax is confusing because it doesn't need object to compute value

use static methods in:

1. method does not need to access object state (all parameters it requires are supplied explicitly)
1. method only needs to access static fields of a class

Factory methods yield objects with different functionality

- constructors can't be named, must be same name as class
- in constructor you can't vary the type of constructed object

#### main method

**main** method does not operate on any objects. When program starts the main method has to construct the required objects.

- useful to have main method for classes for testing purposes

### Method Parameters

**call by value** method gets just the value that the caller provides. A method can not modify the value of the value stored in the variable.

**call by reference** method gets the location of the variable the caller provides. A method can modify the value stored in the variable.

**call by name**  Call by name is an evaluation strategy where the arguments to a function are not evaluated before the function is called—rather, they are substituted directly into the function body (using capture-avoiding substitution) and then left to be evaluated whenever they appear in the function. If an argument is not used in the function body, the argument is never evaluated; if it is used several times, it is re-evaluated each time it appears. Not really used by modern programs. Algol did use it.

Java always uses call by value

- a method cannot modify a parameter of a primitive type
- a method can change the state of an object parameter
- a method cannot make an object paramter refer to a new object

When an object is passed to a method it gets a copy of the reference. It can not change the original variable that references the object.

### Object Construction

#### Overloading

**overloading** occurs if several methods have same name but different parameters.

**overloading resolution** compiler picks the correct method by matching the parameter types in the headers of various methods with the types of values used in a specific method call. An error occurs if more than one method or zero methods are matched.

**java allows** any method to be overloaded, not just constructors

**default field initialization** object fields are automatically assigned a default value when the value is not set in the constructor

- boolean: false
- numeric: 0
- object reference: null
- string: ""

#### Constructor with No Arguments

- no argument constructor is provided when you write a class with **no constructor** (all fields set to default value)

- if class has at least one constructor but does not have a no argument constructor it is illegal to call the constructor without any arguments

#### Explicit Field initialization

Assign value inside class definition. Used when all constructors set instance to the same value. The initialization can also be a method call.

```java
class Employee 
{
    private String name = "";
    private static int id = assignId();
}
```

#### Parameter Name

Different conventions:

```java
// single letter
public Employee(String n)
{
    name = n;
}
```

```java
// Prefix Name
public Employee(String aName)
{
    name = aName;
}
```

```java
// Same name
public Employee(String name)
{
    this.name = name;
}
```

#### Calling Another Constructor

**this**: if it is the first statement of a constructor it has the form this(...), then the constructor calls another constructor of the same class.

```java
public Employee(String)
{
    this("Employee #" + nextId, s);
    nextId++;
}
```

#### Initialization Blocks

Class declarations can contain an arbitrary block of code that gets executed whenever an object of that class is constructed

```java
class Employee
{
    private static int nextId;

    private int id;
    private String name;
    private double salary;

    {
        id = nextId;
        nextId++;
    }

    public Employee(String n, double s)
    {
        name = n;
        salary = s;
    }

    public Employee() 
    {
        name = "";
        salary = 0;
    }
}
```

#### initialization mechanics

1. all data fields are initialized to their default values (0, false, null)
2. all field initializers and initialization blocks are executed, in the order in which they occur in the class declaration
3. if the first line of the constructor calls a second constructor, then the body of the second constructor is executed
4. the body of the constructor is executed

#### static initialization block

useful for classes that require complex initialization code for static fields

- executed in order that they are written in class

```java
// static initialization occurs when class is FIRST loaded
static
{
    Random generator = new Random();
    nextId = generator.nextInt(10000);
}
```

##### hello world without main

```java
public class Hello
{
    static
    {   
        // call System.exit(0) to avoid error
        System.out.println("Hello, World");
    }
}
```

#### useful features for constructors

- overloaded constructors
- call to another constructor with this(...)
- no argument constructor
- object initialization block
- static initialization block
- instance field initialization

#### Object Destruction and finalize method

Freeing up memory happens by the Java virtual machine no need for Destructor to handle.

- **finalize** method gets called before the garbage collector gets called
  - do not rely on it; no guarantee when it would run
  - Runtime.addShutdownHook useful for call code before JVM shuts down
  - manage resources such as file handles explicitly

### Packages

**package** collection for a group of classes.

- main packages in standard java library
  - java.util
  - java.net
  - java.lang

- main reason to use use guarantee uniqueness of class names
- different packages have nothing to do with each other 
  - java.util vs java.util.jar
- convention to use domain name in reverse owned by you to write java packages
- public classes can be accessed from other packages. 
- can access class by adding full package name in front of every calss
 
#### import

- import specific class or whole package
- less tedious to type than typing whole package name every time

```java
import java.util.*;

import java.util.Date;

// import java.*.* not allowed (nested *)
```

With collision in classes could get compiler error

```java
import java.util.*;
import java.sql.*;

Date today; // Error-- jva.util.Date or java.sql.Date?

// import java.util.Date  would have resolved
```

#### locating packages

Compiler responsible for locating classes in a package.

- Bytecodes use full package names to refer to other classes

#### static imports

Can import static methods  and fields not just classes:

```java
import static java.lang.System.*
out.println("Goodbye, world"); // i.e. System.out
```

#### Adding class into a package

Need to place name of package at TOP of source BEFORE code that defines classes

```java
package com.horstmann.corejava;

public class Employee 
{
    // ...
}
```

If no package declaration in the source file the classes in source file belong to the default package

Place source files into subdirectory that matches full package name

Assuming the package name is called com.horstmann.corejava

- com
  - horstmann
    - corejava
      - Employee.java
      - Employee.class
  

Can have multiple classes in different packages. Compilation must be done at the Base directory (directory containing com):

-com
    - horstmann
      - corejava
        - Employee.java
        - Employee.class
      - mycompany
        - PayrollApp.java
        - PayrollApp.class


NOTE: Compiler does not check directory structure when it compiles source files. The source file placed in directory name not matching package name will compile without errors if it doesn't depend on other packages. The virtual machine will result in an error because it won't find the resulting classes when you run the program

#### Package Scope

- class fields with public modifier can be accessed by any class
- private modifiers can only be used by the class that defines them
- default (no setting) will allow access by all methods in the same package

Even if not in the package you can add a user defined class into the package. For the java.* class the java compiler forbids this since Java 1.2

Package sealing prevents the addition of classes to a package.

#### Class Path

JAR files use ZIP format to organize files and subdirectories.

Share classes among programs:

1. place class files inside directory. The base directory for the package tree.
2. place JAR files inside a directory
3. Set class path (collection of all locations that can contain a class)
    - /home/user/classdir:.:/home/user/archives/archive.jar

Starting in JAVA SE 6 you can specify a wildcard for JAR file directory:

- /home/user/classdir:.:/home/user/archives/'*'
    - Note: the wildcard must be excaped in UNIX to prevent shell expansion

NOTE: javac compiler always looks for files in the curent directory but java virtual machine launcher only looks into the current directory if "." directory is on the class path. This is not a problem if you have no class path set; it is included in the default. If you forget to include "." when setting the class path the program can compile but will fail to run.

Virtual machine first searches for the following directories for classes:

    - jre/lib
    - jre/lib/ext

Class references without packages the compiler needs to find the package that contains the class. All import directives are possible sources

- Compiler searches for class in all of the locations in the class path
- error is issued if more than one class is found. 
- Compiler looks at source files to see if source is newer than the class file. If it is, it performs a recompile.
- Only public classes can be imported from other pacakges
    - A source file can only contain one public class, and the names of the file and public class must match
    - this makes it easy for compiler to search for source file from other packages
- importing class from current package the compiler searches all source files of current package to determine what file defines the class. (NOTE: classes in same package can just be used and no need to import them)

##### Setting the Class Path

Several ways to specify the class path:

- Use classpath or -cp to specify the class path:

```bash
java -classpath /home/user/classdir:.:/home/user/archives/archive.jar MyProg
```

- use environment variable

```bash
export CLASSPATH=/home/user/classdir:.:/home/user/archives/archive.jar
```

NOTE: not a good idea to set CLASSPATH environment variable permanently. Global settings can cause surprises.

#### Documentation Comments

javadoc utility extracts information for the following items:

- packages
- public classes and interfaces
- public and protected fields
- public and protected constructors and methods

Each comment is placed immediately above the feature that describes it. 

- Each comment starts with /** and ends with */

The first statement of the comment should be a summary statement.

Free form text can accept html modifiers. Good to avoid h1 and hr tags because they can interfere with formatting of the document.

If comment contains link to other files such as images these files should be placed into a subdirectory of the directory containing the source file, anamed doc-files.

- need to use doc-files directory in your link
- javadoc copies the doc-files directories and their contents from the source directory to the documentation directory

##### Class Comments

Must be placed after any import statements durectly before the class definition:

```java
/**
* A <code>Card</code> object represents a playing card, such
* as "Queen of Hearts". A card has a suit (Diamond, Heart,
* Spade or Club) and a value (1 = Ace, 2 . . . 10, 11 = Jack,
* 12 = Queen, 13 = King)
*/
public class Card
{
    // ...
}
```

##### Method Comments

Each method comment must precede the method that describes it. The following are special method tags:

- @param variable description
- @return description
- @throws class description

Example method comment:

```java
/**
* Raises the salary of an employee.
* @param byPercent the percentage by which to raise the salary (e.g. 10
means 10%)
* @return the amount of the raise
*/
public double raiseSalary(double byPercent)
{
double raise = salary * byPercent / 100;
salary += raise;
return raise;
}
```

##### Field Comments

Generally only need to document public fields, generally static constants:

```java
/**
* The "Hearts" card suit
*/
public static final int HEARTS = 1;
```

##### General Comments

The following tags can be used in class documentation:

- @author name
- @version text
- @since text
- @deprecated text
- @see reference
    - adds hyperlink in the "see also" section
        - package.class#featureLabel
        - \<a href="..."\>\<label\</a\>
        - "text"
    - can be used for both class and methods
    - name of package or class can be omitted. Must use # to refer to methods.
    - multiple @see tags can be added
- @link tags
    - can be used to add links anywhere in the documentation
        - @link package.class#featureName

##### Package and Overview Comments

Package comments need to be placed in a separate file. Two choices:

1. supply package.html file. All text between body tag is extracted
2. supply a package-info.java file. The file must contain an intial Javadoc comment delimited by /** and */. This must be followed by a package statement. No further code or comments must be in the file.


##### Comment Extraction

docDirectory is where you want the document to go. To extract the comments follow these steps:

1. Change directory to source files you want to document
2. run one of the following commands:

- for single package 
```bash
javadoc -d docDirectory nameOfPackage
```
- for multiple package
```bash
javadoc -d docDirectory nameOfPackage 1 nameOfPackage 2 ...
```
- Document multiple packages
```bash
javadoc -d docDirectory *.java
```

If you omit the -d directory all files are extracted to the current directory. This is not a good idea.

Fine tuning javadoc:

- -author and -version options are used to include @author and @version tags
- -link to include hyperlinks to standard classes
- -linksource each source file is converted to HTML without color coding but with line numbers.
- doclets can be used to output specific formats

### Class Design Hints

Hints for well-mannered OOP

1. Always keep data private. When data are kept private, changes in their representation will not affect the user of the class, and bugs are easier to detect.
1. Always initialize dat. Don't rely on defaults but initialize all variables explicitly, either by supplying a default or by setting defaults in all constructors.
1. Don't use too many types in a class. Replace related simple fields with their own class.
1. Not all fields need individual field accessors and mutators. Only supply them if they require to be changed.
1. Break up classes with too many responsibilities
1. make names of your classes and methods reflect their responsibilities

- class name should be nown or a nount precdeded by an adjective or a gerund (an "-ing")
  - Order
  - RushOrder
  - BillingAddress
- getter method should start with "get"
- setter method should start with "set"

## Inheritance

### Classes, Superclasses and Subclasses

extends keyword making derived class from existing class

- existing class is called superclass, base class or parent class
- new class is called subclass, derived class or child class

Term for superclass and subclass is confusing. Superclass has less functionality than subclass.

- super and sup come from language of sets. The superclass is a superset of the child class. The child class is a subset of the parent.

example:

```java
class Manager extends Employee
{
    private double bonus;
    public void setBonus(double b)
    {
        bonus = b;
    }
}
```

Only need to indicate differences between subclass and superclass. 

- subclass has not direct access to private fields of the superclass
- to access the private fields need to use accessor

If a method is overriden in the subclass you can access the parent class implementation by using the super keyword:


```java
class Manager extends Employee
{
    private double bonus;
    public void setBonus(double b)
    {
        bonus = b;
    }
    public double getSalary()
    {
        double baseSalary = super.getSalary();
        return baseSalary + bonus;
    }
}
```

NOTE: super and this are not exactly analogous. Super is not a reference to an object. you can not assign the value super to another object value. Super is a special keyword.

Inheritance can add fields, add methods, or override methods. They can never take away methods or fields.


#### super in constructor

When used inside the constructor the super keyword directs the constructor to call the parent's constructor.

- it must be the first line in the constructor
- if subclass does not class super constructor explicitly then the no argument constructor for the superclass constructor is called.
  - if superclass does not have a no argument constructor the java compiler will report an error

- super has two meanings:
  1. invoke a superclass method
  1. invoke a superclass constructor

subclass must initialize private fields of superclass through a constructor. subclass has no direct access to the private fields of its superclass.

#### Inheritance Hierarchies

**inheritance hierarchy** the collection of classes extending a common superclass is called an inheritance hierarchy

**inheritance chain** a path from a particular class to its ancestors in the inheritance hierarchy

Java does not support multiple inheritance. A class can only have a single parent.

#### Polymorphism

In java variables are polymorhic. A variable of type C can refer to an object of class C or an object of any subclass of C.

**polymorphism** when an object variable refers to multiple actual types.

You can assign a subclass object to a superclass reference

- when you call a subclass specific method from the superclass reference the compiler will error

```java
Manager boss = new Manager(...);
Employee[] staff = new Employee[3];
staff[0] = boss;

boss.setBonus(5000); // ok
staff[0].setBonus(5000); // Error

// can not assign a superclass reference to a subclass variable
Manager m = staff[i]; // Error
```
In Java all arrays remember the element type which they are created.

- the array types monitor that only compatible references are stored in the array
- attempting to store an incompatible type in the array reference results in an **ArrayStoreException** error

```java
Manager[] managers = new Manager[10];

Employee[] staff = managers; // OK

staff[0] = new Employee("Harry Hacker", ...); // compiler is OK; will result in ArrayStoreException at runtime
```

#### dynamic binding

**dynamic binding** process by which appropriate method is selected at runtime.

What happens when x.f(param) method is called on an object:

1. compiler looks at the declared type of x (implicit parameter) and the method name. Assuming x resolves to object of type class C
  - all methods in class C that are named f are enumerated
    - this includes all non-private superclass methods of class C

1. complier determines the type of parameters supplied to the method call. It finds a unique matched signature **overloading resolution**. If multiple matches are found the compiler throws an error. 

- The return type is not part of the signature. The return type can be be overriden by the subclass. However the return type must be a subtype of the original return type.

```java
public Employee getBuddy() (...)
public Manager getBuddy() (...) // ok to change return type
```

1. If a method is private, static, final, or a constructor the compiler knows what method to call through **static binding**. Otherwise the method needs to be resolved via dynamic binding (because method to be called depends on actual type of implicit parameter).

1. Virtual machine needs to call the version of method that is appropriate for the actual type of object that x belongs. The virtual machine uses a **method table** to resolve the method call for efficiency.

**dynamic binding** makes programs extensible without need to modify existing code.

When overriding method the subclass method must be at least as visible as the superclass method. If superclass is public the subclss must be public as well.

#### Preventing Inheritance: Final Classes and Methods

if a class has **final** modifier it can not be extended. By default all of its methods are also final and can not be overridden. its fields are not **final** automatically

```java
final class Executive extends Manager {
    ...
}
```

if a method has a **final** it can not be overridden.

```java
final class Employee {
    public final String getName() 
    {
        return name;
    }
}
```

if a field is declared as **final** its value can not be declared after the object is constructed.

The only reason to use **final** is to ensure that the sematics of calling the class or method does not change:

- getTime and setTime for Calendar class are final. Calendar class has the responsibility of converting Date class and calendar state

- String class is final. No other class can subclass the String.

#### Casting

**casting** is the process of forcing a conversion from one type to another.

Surround target class name with parentehsis:

```java
Manager boss = (Manager) staff[0];
```

Only reason to use a cast is if you want to use an object to its full capacity after the type has been temporarily forgotton.

Casting down an inheritance chain will cause error during Runtime:

```java
// staff[1] contains Employee superclass of Manager
Manager boss = (Manager) staff[1]; // ClassCastException
```

Compiler will not let you make a cast if there is no chance for cast to succeed:

```java
Date c = (Date) staff[1]; // Date is not subclass of Employee
```

- you can only cast within inheritance hierarchy
- instance of can be used to check before casting takes place

```java
if (staff[1] instanceof Manager)
{
    boss = (Manager) staff[1];
}
```

```java
// the test does not generate an exception if x is null. It simply returns false. null refers to no object so it does not refer to object of type C
x instanceof C
```

#### Abstract Classes

- A class with one or more abstract methods must be declared with the abstract keyword.

- An abstract class can have fields and concrete methods.

- An abstract class can not be instantiated.

- An abstract class can also have no abstract methods.

- Abstract methods are placeholders for methods that are implemented in subclasses.

When inheriting from an abstract class you have two choices:

1. leave some or all abstract methods in the parent class undefined in the subclass. This subclass must be declared as an abstract as well.

2. subclass declares all the abstract methods in the parent class. This class can be a non-abstract class and instantiated.

Abstract classes can be used as types for variable assignment. These assignments allow a variety of types to be referenced by a single variable:

```java
Person[] people = new Person[2];
people[0] = new Employee(. . .);
people[1] = new Student(. . .);

for (Person p : people)
    // Error if getDescription was not defined in the Person class but existed in the Employee and Student class as concrete methods
    // compiler ensures that only methods on the declared type are invocable
    System.out.println(p.getName() + ", " + p.getDescription());

```

#### Protected Access

Variables, methods, and constructors, which are declared protected in a superclass can be accessed only by the subclasses in other package or any class within the package of the protected members' class.

- You can't access a protected superclass field in a different instance of the class.

Summary of four access modifiers:

1. private: visible to the class only
2. public: visible to the world
3. protected: visible to the package and all subclasses
4. default: visible to the package. The unfortunate default.

Modifier | Class | Package | Subclass | World
---------|----------|---------|--------|---------
 public  | Y | Y | Y | Y
 protected | Y | Y | Y | N
 no modifier | Y | Y | N | N
 private | Y | N | N | N

```java
public class A {
    protected int data = 10;
}

public class B {
    A obj = new A();

    public int getData{
        // can be accessed because in same package
        return obj.data;
    }
}
```


```java
// The 'C' class cannot access data variable of A's object since it is beyond the scope; not in same package.

public class C {
    A obj = new A();

    public int getData{
        return obj.data; //compile time error
    }
}
```

### Object: The Cosmic Superclass

The Object class implicitly extends every class in Java.

- Array types are always class types that extend the Object class

#### equals Method

Equals method checks whether two objects are equal. 

- The object method default equal method checks whether two object references are identical.

```java
 // returns true if both arguments are null, or false
 // returns false if only one is null or false
Objects.equals(a,b)
```

##### Equality Testing and Inheritance

The recommendations for equality check are the following:

1. equality is reflexive. For any non-null reference x, x.equals(x) should return true
1. equality is symmetric: For any reference x and y, x.equals(y) should return true if and only if y.equals(x) is true
1. it is transitive: for any reference x, y, and z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) should return true
1. it is consistent: if the objects to which x and y refer haven't changed, then repeated calls to x.equas(y) return the same value
1. for any non-null reference x, x.equals(null) should return false

##### Perfect equals method

recipe for perfect equals method:

- Name the explicit parameter otherObject --- layter, you will need to cast it to another variable that you should call other.
- test whether this happens to be identical to otherObject:

```java
if (this == otherObject) return true;
```

- test whether otherObject is null and return false if it is. This test is required.

```java
if (getClass() != otherObject.getClass()) return false;
```

If the same semantics holds for all subclasses, you can use an instanceof test:

```java
if (!(otherObject instanceof ClassName)) return false;
```

- Cast otherObject to a variable of your class type:

```java
ClassName other = (ClassName) otherObject
```

- Now compare the fields, as required by your notion of equality. use == for primitive type fields, Object.equals for object fields. Return true if all fields match, false otherwise

```java
return field1 == other.field1
    && Objects.equals(field2, other.field2)
    && ...;
```

If you redefine equals in a subclass, include a call to super.equals(other).

##### Tips and Caution

- For array type fields use Array.equals method to check corresponding array elements are equal

```java
// true if arrays have equal lengths and equal elements in corresponding positions
// arrrays can have component types Object, int, long, short, char, byte, boolean, float or double
static boolean equals(type[] a, type[] b)
```

- Make sure you use @Override declaration to ensure that you are overriding the intended equals method in the parent

```java
// returns true if a and b are both null, false if exactly one of them is null, and a.equals(b) otherwise
@Override public boolean equals(Object other)
```

#### The hashCode Method

Hash code is an integer that is derived from an object.

String classes use the following algorithm to compute the hash code:

```java
int hash = 0;
for (int i = 0; i < length(); i++) {
    hash = 31 * hash + charAt(i);
}

// string hash codes are derived from contents

// hash code 2556 for both
String s = "Ok";
String t = new String("Ok");
```

The hashCode method is defined in the Object class. Every object has a default hash code derived from memory address.

Every time you redefine the equals method you should redefined the hashCode method. 

Useful functions for redefining the hashCode:

```java
// returns hash code for this object
int hashCode()

// retuns hashCode that is combined from hash codes of all supplied objects
int Objects.hash(Object ... objects)

// retuns 0 if a is null or a.hashCode() otherwise
static int Objects.hashCode(Object a)

// computes the hash code of the array a, which can have component type Object, int, long, short, char, byte, boolean, float, or double
static int hashCode(type[] a)
```

#### The toString Method

toString returns string representation of the object

Most toString methods return the name of the class, followed by the field values enclosed in square brackets:

```java
public String toString()
{
    return getClass().getName()
        + "[name=" + name
        + ",salary=" + salary
        + ",hireDay=" + hireDay
        + "]";
}

// calling superclass toString method
class Manager extends Employee
    {

        // example: Manager[name=...,salary=...,hireDay=...][bonus=...]
        public String toString()
        {
            return super.toString()
              + "[bonus=" + bonus
              + "]";
        }
}
```

When calling System.out.println(x) it automatically calls x.toString() and prints the resulting string.


```java
// TIP: alternative to writing x.toString()

"" + x
```
Object class defines to toString method prints the class name and the hashCode of the object. System.out toString method prints something like:

- java.io.PrintStream@2f6684

Arrays toString method is inherited from Object. It uses archaic form for the type

```java
// prints "[I@1a46e30"
// prefix denotes array of integers
int[] luckyNumbers = { 2, 3, 5, 7, 11, 13 };
String s = "" + luckyNumbers;

// prints "[2, 3, 5, 7, 11, 13]"
// Arrays.deepToString fro multidimensional arrays
String s = Arrays.toString(luckyNumbers);
```

Write toString method for every class that you write to help in debugging.

```java
// called within a class
getClass() // returns object that contains information about object

// comparing objects
x.equals(Object otherObject)

// return string representation of this object
x.toString()

// return the name of this class
String getName()

// returns the name of this class
Class getSuperclass()
```

### Generic Array List

ArrayList is a generic class with type parameter.

- automatically adjusts capacity as you add and remove elements

```java
// allocating an array list
// only has potential for holding 100 elements
new ArrayList<>(100) // capacity is 100

// not same as allocating a new array
new Employee[100]


// constructs an empty array list of type T
ArrayList<T>()

// constructs an empty array list with specified capacity
ArrayList<T>(int initialCapacity)

// add object to existing arrayList
boolean add(T obj)

// return number of elements currently stored in ArrayList
int size()

// ensures array list has capacity to store given number of elements
void ensureCapacity(int capacity)

// reduces storage capacity of array list to current size
void trimToSize()

```

#### Accessing Array List Elements

Dealing with ArrayList elements is a bit more cumbersome. Can't access elements with []

```java
// do not call list.set(i, x) until size of array list is larger than i
staff.set(i, harry)

// retrieving elements from an array list
Employee e = staff.get(i);

```

raw arrayList is dangerous as its add and set methods accept objects of any type. 

```java
// compiles without so much as a warning
// you run into grief only when you retrief object
staff.set(i, new Date());
```

Tip for getting benefit of ArrayList with convenient of array:

```java
ArrayList<X> lsit = new ArrayList<>();
while (...) 
{
    x = ...;
    list.add(x);
}

X[] a = new X[list.size()];
list.toArray(a)
```

Can add and remove elements to middle of an array:

```java
int n = staff.size() /2;

// The elements located at n and above are shifted 
staff.add(n, e);

// elements located above n are copied down and size of array is reduced by one
staff.remove(n);
```

You can use "for each loop to traverse the contents of an array:

```java
for (Employee e: staff)
{
    // do something with e
}

for (int i = 0; i < staff.size(); i++)
{
    Employee e = staff.get(i);
    // do something with e
}
```

Summary of ArrayList versus Array:

- don't have to specify array size
- use add to add as many elements as you like
- use size() instead of length to count number of elements
- use a.get(i) instead of a[i] to access element

#### Compatibility between Typed and Raw Array Lists

Always used type parameters for added safety

```java
public class EmployeeDB 
{
    public void update(ArrayList list) { // ... }
    public ArrayList find(String query) { // ... }

    // pass a typed array list to the update method without casts
    ArrayList<Employee> staff = ...;

    // The staff object is passed to the update method
    // not completely safe as update method can add elements to array list that are not of type Employee
    employeeDB.update(staff);
}
```

Assigning a raw ArrayList to a typed one, you get a warining:

```java

// can see warning text if you compile with the option
//  -Xlint:unchecked
ArrayList<Employee> result = employeeDB.find(query); // yields warning

// Casting does not make warning go away
//  limitation of generic types in Java. The compiler translates all typed array lists into raw ArrayList objects after checking taht the type rules are not violated. The casts (ArrayList) and (ArrayList<Employee>) carray out identical runtime checks
ArrayList<Employee> result = (ArrayList<Employee>) employeeDB.find(query); // yields another warning

// make sure that warning is ok and use following construct to suppress warning

@SuppressWarnings("unchecked") ArrayList<Employee> result = (ArrayList<Employee>) employeeDB.find(query); // yields another warning
```

### Object Wrappers and Autoboxing

All primitive types have a class counterpart. These classes are called wrappers:

- Integer
- Long
- Float
- Double
- Short
- Byte 
- Character
- Void
- Boolean

The wrapper classes are immutable and cannot be changed. They are also final and can not be subclassed.

The type parameter inside the angle brackets (generic) cannot be a primitive. Use Object wrapper equivalent for primitives:

```java
// ArrayList is far less efficient than int[]
ArrayList<Integer> list = new ArrayList<>();
```

Autoboxing example:

```java
list.add(3);

// automatically translated to. Conversion is autoboxing
list.add(Integer.valueOf(3));
```

Unboxing happens automatically by compiler:

```java
int n = list.get(i);

// compiler translates into
int n = list.get(i).intValue();
```

The == operator might fail during comparisons for wrapper objects:

```java
Integer a = 1000;
Integer b = 1000;
// may or may not be true
//  Java implementation "MAY" choose to wrap commonly occurring values into identical objects
// use equals method to avoid ambiguity
if (a == b) 
```

The autoboxing specification requires that boolean, byte, char $\le$ 127, short, and int between -128 and 128 are wrapped into fixed objects.

The compiler and not the virtual machine performs the boxing and unboxing. 

The wrappers are also a convenient palce for certain basic methods:

It is impossible to write a java method that increments an integer parameter because parameters to Java methods are always passed by value.

TIP: can use holder types defined in org.omg.CORBA package IntHolder, BooleanHolder etc to change value passed in:

```java
public static void triple(IntHolder x)
{
    x.value = 3 * x.value;
}

```

Useful methods for Integer class:

```java

// java.lang.Integer: returns the value of this Integer object as an int
int intValue

// returns a new String object representing the number i in base 10
static String toString(int i)

static int parseInt(String s)
static int parseInt(String s, int radix)

static Integer valueOf(String s)
static Integer valueOf(String s, int radix)

// java.text.NumberFormat
// returns the numeric value, assuming the specified String represents a number

Number parse(String s)
```

### Methods with Variable Number of Parameters

Methods can have variable number of parameters:

```java
// ... denotes method can received variable number of parametersj
public PrintStream printf(String fmt, Object... args) { return format(fmt, args); }
```

The vararg parameter can be any type

### Enumeration Classes

Example to define an Enum:

```java
public enum Size 
{
    // enum will have only four instances
    // new objects can not be constructed
    SMALL("S"), MEDIUM("M"), LARGE("L"), EXTRA_LARGE("XL");

    private String abbreviation;

    private Size(String abbreviation) {this.abbreviation = abbreviation ;}
    public String getAbbreviations() { return abbreviation; }
}
```

Constructer is only invoked when enumerated constants are constructed.

Useful methods:

- Convert an enum to a string:

```java
Size.SMALL.toString() // "SMALL"
```

- Convert string to enum

```java
Size s = Enum.valueOf(Size.class, "SMALL");
```

- ordinal method yields the position of the enumerated constants

```java
Size.MEDIUM.ordinal() // returns 1
```

- compare relative position of the enumerated constants

```java
Size.MEDIUM.compareTo(Size.SMALL)
```

### Reflection

#### The Class Class

runtime type identification: is maintained by by Java runtime to keep track of the clas to which an object belongs.

- used by virtual machine to select methods to execute

The getClass method returns instance instance of Class type:

```java
Employee e;
Class cl = e.getClass();

System.out.println(e.getClass().getName() + " " + e.getName());

// prints Employee Harry Hacker
```

The forName returns a class object corresponding to a class name:

```java

// works if forName is name of class or interface
// otherwise it wil lreturn checked exception
String className = "java.util.Date";
Class cl = Class.forName(className);
```

Can use Java type to return mathching class object

```java
// Class object describes a type which may or may not be a class
Class cl1 = Date.class; // if you import java.util.*
Class cl2 = int.class;
Class cl3 = Double[].class;
```

Caution: getName retuns strange names for array types

```java
Double[].class.getName() // return [Ljava.lang.Double;
```

The == operator can be used to compare class objects. Only one Class object for each type:

```java
if (e.getClass() == Employee.class) // ...
```

Create an instanc of a class dynamically (so long as class requires no options; or the no argument constructor is calledj):

```java
String s = "java.util.Date";
Object m = Class.ForName(s).newInstance();
```

#### Primer on Catching Exceptions

Two types of exceptions:

1. Checked: the compiler will check if a handler exists for the exception
1. Unchecked: the compiler does not check if a handler is supplied.

handler: can catch exceptions and deal with them 

The Class.forName method is a method that throws a checked exception.

```java

try
{
    // statements that might throw exceptionsj
    String name = ...; // get class name
    Class cl = Class.forName(name); // is not a class or interface the rest of statements are skipped
    // do something with cl
} 
catch (Exception e)
{
    // the method is defined in the Throwable class (parent of Exception)
    e.printStackTrace();
}
```

#### Using Reflection to Analyze the Capabilities of Classes

Three classes in java.lang.reflect package:

1. Field: describe fields
1. Method: describe methods
1. Constructor: the constructors of a class

- All classes have a getModifiers method that returns an integer with various bits turned on and off, that describes the midifiers used, such as public and static.

Methods of a class to get access to class items:

- include public fields of superclasses:

1. getFields 
1. getMethods
1. getConstructors

- Only includes methods that are declared in the class

1. getDeclaredFields
1. getDeclaredMethods
1. getDeclaredConstructors
