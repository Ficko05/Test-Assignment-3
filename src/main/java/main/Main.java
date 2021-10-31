package main;

import datalayer.booking.BookingStorageImpl;
import datalayer.employee.EmployeeStorageImpl;
import dto.booking.Booking;
import dto.customer.Customer;
import datalayer.customer.CustomerStorageImpl;
import dto.employee.Employee;
import servicelayer.booking.BookingServiceException;
import servicelayer.booking.BookingServiceImpl;
import servicelayer.customer.CustomerServiceException;
import servicelayer.customer.CustomerServiceImpl;
import servicelayer.employee.EmployeeServiceException;
import servicelayer.employee.EmployeeServiceImpl;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

public class Main {
    //run docker and run this command
    //docker run -d --rm --name mysql-test-db -e MYSQL_ROOT_PASSWORD=testuser123 -p 3307:3306 mysql
    //create a db connection port 3307 and schema called TestASS
    //
    //if i have maven istalled do this:
    //mvn flyway:migrate



    private static final String conStr = "jdbc:mysql://localhost:3307/TestASS";
    private static final String user = "root";
    private static final String pass = "testuser123";


    public static void main(String[] args) throws SQLException, CustomerServiceException, EmployeeServiceException, BookingServiceException {
//customerStorage.createCustomer(new CustomerCreation("Filip","Filipovic", null ));
//customerServiceImpl.createCustomer("hello", "hellovic", null);
        CustomerStorageImpl customerStorage = new CustomerStorageImpl(conStr, user, pass);
        EmployeeStorageImpl employeesStorage = new EmployeeStorageImpl(conStr, user, pass);
        BookingStorageImpl bookingStorage = new BookingStorageImpl(conStr, user, pass);

        CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerStorage);
        EmployeeServiceImpl employeeServiceImpl = new EmployeeServiceImpl(employeesStorage);
        BookingServiceImpl bookingServiceImpl = new BookingServiceImpl(bookingStorage);


        populateDummyCustomer(customerServiceImpl); //populates the sql db
        populateDummyEmployees(employeeServiceImpl); //populates the sql db
        populateDummyBookings(bookingServiceImpl); //populates the sql db

        soutCusomers(customerStorage); //runs through the db and sout's them out in terminal
        soutEmployees(employeesStorage); //runs through the db and sout's them out in terminal
        soutBookings(bookingStorage); //runs through the db and sout's them out in terminal


        System.out.println("The end.");
    }


    public static void soutCusomers(CustomerStorageImpl customerStorage) throws SQLException {
        System.out.println("Got customers: ");
        for (Customer c : customerStorage.getCustomers()) {
            System.out.println(toStringCosumer(c));
        }
        System.out.println("Got Customers: ");

    }

    public static void soutEmployees(EmployeeStorageImpl employeesStorage) throws SQLException {
        for (Employee e : employeesStorage.getEmployee()) {
            System.out.println(toStringEmployee(e));
        }
        System.out.println("Got Employees");
    }

    public static void soutBookings(BookingStorageImpl bookingStorage) throws SQLException {
        for (Booking b : bookingStorage.getBookings()) {
            System.out.println(toStringBooking(b));
        }
        System.out.println("Got Bookings");
    }

    public static void populateDummyCustomer(CustomerServiceImpl customerServiceImpl) throws CustomerServiceException {
        customerServiceImpl.createCustomer("Filip", "Filipsen", new Date());
        customerServiceImpl.createCustomer("Allan", "Allansen", new Date());
        customerServiceImpl.createCustomer("Nina", "Ninasen", new Date());
    }

    public static void populateDummyEmployees(EmployeeServiceImpl employeeServiceImpl) throws EmployeeServiceException {
        employeeServiceImpl.createEmployee("emp", "Empsen", new Date());
    }

    public static void populateDummyBookings(BookingServiceImpl bookingServiceImpl) throws BookingServiceException {
        bookingServiceImpl.createBooking(1,1,new Date(),new Time(12), new Time(13));

    }


    public static String toStringCosumer(Customer c) {
        return "{" + c.getId() + ", " + c.getFirstname() + ", " + c.getLastname() + "}";
    }

    public static String toStringEmployee(Employee e) {
        return "{" + e.getId() + ", " + e.getFirstname() + ", " + e.getLastname() + "}";
    }

    public static String toStringBooking(Booking b) {
        return "{" + b.getId() + ", " + b.getCustomerId() + ", " + b.getEmployeeId() + ", " + b.getDate() + ", " + b.getStart()+ ", " + b.getEnd() +  "}";
    }
}
