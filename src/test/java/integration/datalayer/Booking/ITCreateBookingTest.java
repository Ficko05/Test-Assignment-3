package integration.datalayer.Booking;


import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.booking.BookingCreation;
import dto.customer.CustomerCreation;
import dto.employee.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("IntegrationTest")
public class ITCreateBookingTest extends ContainerizedDbIntegrationTest {

    private BookingStorage bookingStorage;
    private CustomerStorage customerStorage;
    private EmployeeStorage employeeStorage;


    @BeforeEach
    public void Setup() throws SQLException {
        runMigration(3);
        bookingStorage = new BookingStorageImpl(getConnectionString(), "root", getDbPassword());
        customerStorage = new CustomerStorageImpl(getConnectionString(), "root", getDbPassword());
        employeeStorage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());
        //var numBooking = bookingStorage.getBookings().size();

        customerStorage.createCustomer(new CustomerCreation("John", "Carlssonn", new Date()));
        customerStorage.createCustomer(new CustomerCreation("Karl", "johnssonn", new Date()));
        employeeStorage.createEmployee(new EmployeeCreation("emp", "emp", new Date()));
        employeeStorage.createEmployee(new EmployeeCreation("emp2", "emp2", new Date()));

    }


    @Test
    public void mustSaveBookingInDatabaseWhenCallingCreateBooking() throws SQLException, IOException {
        // Arrange
        // Act
        bookingStorage.createBooking(new BookingCreation(1, 1, new Date(), new Time(12), new Time(13)));
        // Assert
        var bookings = bookingStorage.getBookingsForCustomer(1);

        assertTrue(bookings.stream().anyMatch(x ->
                x.getCustomerId() == 1 &&
                        x.getEmployeeId() == 1));
    }

    @Test
    public void mustReturnLatestId() throws SQLException {
        // Arrange
        // Act
        var id1 = bookingStorage.createBooking(new BookingCreation(1, 1, new Date(), new Time(12), new Time(13)));
        var id2 = bookingStorage.createBooking(new BookingCreation(2, 2, new Date(), new Time(12), new Time(13)));
        // Assert
        assertEquals(1, id2 - id1);
    }

    @Test
    public void mustReturnBookingWithCustomerId() throws SQLException {
        // Arrange
        // Act
        var customer = customerStorage.createCustomer(new CustomerCreation("testname", "testname", new Date()));
        var customerId = customerStorage.getCustomerWithId(customer).getId();
        System.out.println("customer id " + customerId);

        var bookingCreated = bookingStorage.createBooking(new BookingCreation(customerId, 1, new Date(), new Time(12), new Time(13)));
        System.out.println("booking created " + bookingCreated);

//        var bookingCustomerId = bookingStorage.getBookingWithId(bookingCreated).getCustomerId();
//        System.out.println("bookingCust id hh " + bookingCustomerId);

        var booking = bookingStorage.getBookingsForCustomer(customerId);
        System.out.println("boobking from cust id " + booking);
        System.out.println("trying to get bookibgd " + bookingStorage.getBookings());

        // Assert
        //assertTrue(customerId == bookingStorage.getBookingWithId(bookingCreated).getCustomerId() );
        assertTrue(booking.stream().anyMatch(x ->
                x.getCustomerId() == customerId));

    }


}
