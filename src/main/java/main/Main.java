package main;

import datalayer.employee.EmployeeStorageImpl;
import dto.customer.Customer;
import datalayer.customer.CustomerStorageImpl;
import dto.employee.Employee;
import servicelayer.customer.CustomerServiceException;
import servicelayer.customer.CustomerServiceImpl;

import java.sql.SQLException;

public class Main {
    //run docker and run this command
    //docker run -d --rm --name mysql-test-db -e MYSQL_ROOT_PASSWORD=testuser123 -p 3307:3306 mysql
    //create a db connection port 3307 and schema called TestASS

    private static final String conStr = "jdbc:mysql://localhost:3307/TestASS";
    private static final String user = "root";
    private static final String pass = "testuser123";


    public static void main(String[] args) throws SQLException, CustomerServiceException {
//customerStorage.createCustomer(new CustomerCreation("Filip","Filipovic", null ));
//customerServiceImpl.createCustomer("hello", "hellovic", null);
        CustomerStorageImpl customerStorage = new CustomerStorageImpl(conStr, user, pass);
        EmployeeStorageImpl employeesStorage = new EmployeeStorageImpl(conStr, user, pass);

        CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerStorage);


        populateDummyCustomer(customerServiceImpl);
        //populateDummyEmployees();
       // populateDummyBookings();

        soutCusomers(customerStorage);
       // soutEmployees(employeesStorage);


        System.out.println("The end.");
    }


    public static void soutCusomers(CustomerStorageImpl customerStorage) throws SQLException {
        System.out.println("Got customers: ");
        for (Customer c : customerStorage.getCustomers()) {
            System.out.println(toStringCosumer(c));
        }
        System.out.println("Got Employee: ");

    }

    public static void soutEmployees(EmployeeStorageImpl employeesStorage) throws SQLException {
        for (Employee e : employeesStorage.getEmployee()) {
            System.out.println(toStringEmployee(e));
        }
    }

    public static void populateDummyCustomer(CustomerServiceImpl customerServiceImpl) throws CustomerServiceException {
        customerServiceImpl.createCustomer("Filip", "Filipsen", null);
        customerServiceImpl.createCustomer("Allan", "Allansen", null);
        customerServiceImpl.createCustomer("Nina", "Ninasen", null);
    }

    public static void populateDummyEmployees() {

    }

    public static void populateDummyBookings() {

    }


    public static String toStringCosumer(Customer c) {
        return "{" + c.getId() + ", " + c.getFirstname() + ", " + c.getLastname() + "}";
    }

    public static String toStringEmployee(Employee e) {
        return "{" + e.getId() + ", " + e.getFirstname() + ", " + e.getLastname() + "}";
    }
}
