# Chapter 4. Objects and Classes

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

**call by name** Call by name is an evaluation strategy where the arguments to a function are not evaluated before the function is called—rather, they are substituted directly into the function body (using capture-avoiding substitution) and then left to be evaluated whenever they appear in the function. If an argument is not used in the function body, the argument is never evaluated; if it is used several times, it is re-evaluated each time it appears. Not really used by modern programs. Algol did use it.

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

Can import static methods and fields not just classes:

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

-com - horstmann - corejava - Employee.java - Employee.class - mycompany - PayrollApp.java - PayrollApp.class

NOTE: Compiler does not check directory structure when it compiles source files. The source file placed in directory name not matching package name will compile without errors if it doesn't depend on other packages. The virtual machine will result in an error because it won't find the resulting classes when you run the program

#### Package Scope

- class fields with public modifier can be accessed by any class
- private modifiers can only be used by the class that defines them
- default (no setting) will allow access by all methods in the same package

Even if not in the package you can add a user defined class into the package. For the java.\* class the java compiler forbids this since Java 1.2

Package sealing prevents the addition of classes to a package.

#### Class Path

JAR files use ZIP format to organize files and subdirectories.

Share classes among programs:

1. place class files inside directory. The base directory for the package tree.
2. place JAR files inside a directory
3. Set class path (collection of all locations that can contain a class)
   - /home/user/classdir:.:/home/user/archives/archive.jar

Starting in JAVA SE 6 you can specify a wildcard for JAR file directory:

- /home/user/classdir:.:/home/user/archives/'\*'
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

- Each comment starts with /\*_ and ends with _/

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
2. supply a package-info.java file. The file must contain an intial Javadoc comment delimited by /\*_ and _/. This must be followed by a package statement. No further code or comments must be in the file.

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

