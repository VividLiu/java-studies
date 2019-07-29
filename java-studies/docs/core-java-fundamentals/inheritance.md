# Chapter 5. Inheritance

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
- if subclass does not call super constructor explicitly then the no argument constructor for the superclass constructor is called.

  - if superclass does not have a no argument constructor the java compiler will report an error

- super has two meanings:

  - invoke a superclass method
  - invoke a superclass constructor

subclass must initialize private fields of superclass through a constructor. subclass has no direct access to the private fields of its superclass.

#### Inheritance Hierarchies

**inheritance hierarchy** the collection of classes extending a common superclass is called an inheritance hierarchy

**inheritance chain** a path from a particular class to its ancestors in the inheritance hierarchy

Java does not support multiple inheritance. A class can only have a single parent.

#### Polymorphism

In java variables are polymorphic. A variable of type C can refer to an object of class C or an object of any subclass of C.

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

// compiler is OK; will result in ArrayStoreException at runtime
staff[0] = new Employee("Harry Hacker", ...);
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

When overriding method the subclass method must be at least as visible as the superclass method. If superclass is public the subclass must be public as well.

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

Surround target class name with parenthesis:

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

```java
// instanceof can be used to check before casting takes place
if (staff[1] instanceof Manager)
{
    boss = (Manager) staff[1];
}
```

```java
// the test does not generate an exception if x is null.
// It simply returns false. null refers to no object so it does not
// refer to object of type C

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
abstract class Person {
    private String name;
    public Person(String n) {
        name = n;
    }

    public abstract String getDescription();
    public String getName() {
        return name;
    }
}
```

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

| Modifier    | Class | Package | Subclass | World |
| ----------- | ----- | ------- | -------- | ----- |
| public      | Y     | Y       | Y        | Y     |
| protected   | Y     | Y       | Y        | N     |
| no modifier | Y     | Y       | N        | N     |
| private     | Y     | N       | N        | N     |

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

#### Using Reflection to Analyze Objects at Runtime

The way to get the contents of a field on an object:

f.get(obj) 

- f if is object of type Field
- obj is an object of type of the class the field f belongs to


```java
Employee harry = new Employee("Harry Hacker", 35000, 10, 1, 1989);
Class cl = harry.getClass();
// the class object representing Employee
Field f = cl.getDeclaredField("name");
// the name field of the Employee class
Object v = f.get(harry);
// the value of the name field of the harry object, i.e., the String
object "Harry Hacker"
```

To access private fields can use:

```java
// this only works if security manager allows it
f.setAccessible(true);
```
To set values of fields:

```java
f.set(obj, value)
```

#### Using Reflection to Write Generic Array Code

**Array** class in java.lang.reflect package allows to create arrays dynamically.

Procedure to copy array:

1. get the class of object
1. confirm that class is an array
1. use the getComponentType method of Class class to find the right type array. The getComponent type is only defined for class objects that represent arrays.

```java
public static Object goodCopyOf(Object a, int newLength)
{
    Class cl = a.getClass();
    if (!cl.isArray()) return null;
    Class componentType = cl.getComponentType();
    int length = Array.getLength(a);
    Object newArray = Array.newInstance(componentType, newLength);
    System.arraycopy(a, 0, newArray, 0, Math.min(length, newLength));
    return newArray;
}
```

!!! Note
    - The parameter of goodCopyOf is declared to be type Object and not an array of objects (Object[])
    - The array type int[] can be converted to an object, but not to an array of integers

