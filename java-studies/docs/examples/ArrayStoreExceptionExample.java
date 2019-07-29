
class Employee 
{
    private double salary;

    public double getSalary()
    {
        return salary;
    }
}

class Manager extends Employee 
{
    private double bonus;

    public double getBonus()
    {
        return bonus;
    }
}

public class ArrayStoreExceptionExample {

    public static void main(String[] args) {
             
        Manager[] managers = new Manager[10];
        Employee[] staff = managers; // OK

        staff[0] = new Employee(); // compiler is OK
    }

}