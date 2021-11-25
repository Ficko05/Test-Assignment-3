package as;

import as.servicelayer.booking.BookingServiceException;
import as.servicelayer.booking.BookingServiceImpl;
import as.servicelayer.customer.CustomerServiceException;
import as.servicelayer.customer.CustomerServiceImpl;
import as.servicelayer.employee.EmployeeServiceException;
import as.servicelayer.employee.EmployeeServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

@SpringBootApplication
public class TestAssignment3Application {

    //run docker and run this command
    //docker run -d --rm --name mysql-test-db -e MYSQL_ROOT_PASSWORD=testuser123 -p 3307:3306 mysql
    //create a db connection port 3307 and schema called TestASS

    private static final String conStr = "jdbc:mysql://localhost:3307/TestASS";
    private static final String user = "root";
    private static final String pass = "testuser123";

    public static void main(String[] args) throws CustomerServiceException, EmployeeServiceException, BookingServiceException, SQLException {
        System.out.println("opdateret nu");
        SpringApplication.run(TestAssignment3Application.class, args);
        datalayer.customer.CustomerStorageImpl customerStorage = new datalayer.customer.CustomerStorageImpl(conStr, user, pass);
        datalayer.employee.EmployeeStorageImpl employeesStorage = new datalayer.employee.EmployeeStorageImpl(conStr, user, pass);
        datalayer.booking.BookingStorageImpl bookingStorage = new datalayer.booking.BookingStorageImpl(conStr, user, pass);

        CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerStorage);
        EmployeeServiceImpl employeeServiceImpl = new EmployeeServiceImpl(employeesStorage);
        BookingServiceImpl bookingServiceImpl = new BookingServiceImpl(bookingStorage, customerServiceImpl);


        populateDummyCustomer(customerServiceImpl); //populates the sql db
        populateDummyEmployees(employeeServiceImpl); //populates the sql db
        populateDummyBookings(bookingServiceImpl); //populates the sql db

        soutCusomers(customerStorage); //runs through the db and sout's them out in terminal
        soutEmployees(employeesStorage); //runs through the db and sout's them out in terminal
        soutBookings(bookingStorage); //runs through the db and sout's them out in terminal

        System.out.println("The end.");


    }
    public static void soutCusomers(datalayer.customer.CustomerStorageImpl customerStorage) throws SQLException {
        System.out.println("Got customers: ");
        for (as.dto.customer.Customer c : customerStorage.getCustomers()) {
            System.out.println(toStringCostumer(c));
        }
        System.out.println("Got Customers: ");

    }

    public static void soutEmployees(datalayer.employee.EmployeeStorageImpl employeesStorage) throws SQLException {
        for (dto.employee.Employee e : employeesStorage.getEmployees()) {
            System.out.println(toStringEmployee(e));
        }
        System.out.println("Got Employees");
    }

    public static void soutBookings(datalayer.booking.BookingStorageImpl bookingStorage) throws SQLException {
        for (dto.booking.Booking b : bookingStorage.getBookings()) {
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


    public static String toStringCostumer(as.dto.customer.Customer c) {
        return "{" + c.getId() + ", " + c.getFirstname() + ", " + c.getLastname() + "}";
    }

    public static String toStringEmployee(dto.employee.Employee e) {
        return "{" + e.getId() + ", " + e.getFirstname() + ", " + e.getLastname() + "}";
    }

    public static String toStringBooking(dto.booking.Booking b) {
        return "{" + b.getId() + ", " + b.getCustomerId() + ", " + b.getEmployeeId() + ", " + b.getDate() + ", " + b.getStart()+ ", " + b.getEnd() +  "}";
    }
}

