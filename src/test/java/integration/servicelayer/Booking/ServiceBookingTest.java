package integration.servicelayer.Booking;


import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorageImpl;
import dto.booking.Booking;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import servicelayer.booking.BookingServiceException;
import servicelayer.booking.BookingServiceImpl;
import servicelayer.customer.CustomerServiceException;
import servicelayer.customer.CustomerServiceImpl;
import servicelayer.employee.EmployeeServiceException;
import servicelayer.employee.EmployeeServiceImpl;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
public class ServiceBookingTest extends ContainerizedDbIntegrationTest {

    private BookingServiceImpl bookingService;
    private BookingStorageImpl bookingStorage;
    private EmployeeServiceImpl employeeService;
    private EmployeeStorageImpl employeeStorage;
    private CustomerServiceImpl customerService;
    private CustomerStorageImpl customerStorage;


    @BeforeEach
    public void Setup() throws SQLException, CustomerServiceException, EmployeeServiceException {
        runMigration(3);


        bookingStorage = new BookingStorageImpl(getConnectionString(), "root", getDbPassword());
        customerStorage = new CustomerStorageImpl(getConnectionString(), "root", getDbPassword());
        employeeStorage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());

        bookingService = new BookingServiceImpl(bookingStorage);
        employeeService = new EmployeeServiceImpl(employeeStorage);
        customerService = new CustomerServiceImpl(customerStorage);

        customerService.createCustomer("Filip", "Filipsen", new Date());
        customerService.createCustomer("Filip2", "Filipsen2", new Date());
        employeeService.createEmployee("emp1","emp1", new Date());
        employeeService.createEmployee("emp2","emp2", new Date());
    }

    @Test
    public void saveBookingInDbWhenCallingCreateBooking() throws BookingServiceException, SQLException {
        // Arrange
        // Act
        var booking =bookingService.createBooking(1,1,new Date(), new Time(21), new Time(22));
        var booking2 =bookingService.createBooking(2,2,new Date(), new Time(21), new Time(22));
        var bookingsById = bookingService.getBookingById(booking).getId();
        var bookingsById2 = bookingService.getBookingById(booking2).getId();
        // Assert
        assertEquals(booking,bookingsById);
        assertEquals(booking2,bookingsById2);
        assertNotEquals(booking,bookingsById2);

    }

    @Test
    public void mustReturnBookingWithCustomerId() throws CustomerServiceException, SQLException, BookingServiceException {
        // Arrange
        // Act
        var customer = customerService.createCustomer("test1", "test2", new Date());
        var customerId = customerService.getCustomerById(customer).getId();

        bookingService.createBooking(customerId,1,new Date(), new Time(12), new Time(13));

        var booking = bookingService.getBookingsForCustomer(customerId);

        // Assert
        assertTrue(booking.stream().anyMatch(x ->
                x.getCustomerId() == customerId));
    }

    @Test
    public void mustReturnBookingWithEmployeeId() throws SQLException, BookingServiceException, EmployeeServiceException {
        // Arrange
        // Act
        var employee = employeeService.createEmployee("test1", "test2", new Date());
        var employeeId = employeeService.getEmployeeById(employee).getId();

        bookingService.createBooking(1,employeeId,new Date(), new Time(12), new Time(13));

        var booking = bookingService.getBookingsForEmployee(employeeId);

        // Assert
        assertTrue(booking.stream().anyMatch(x ->
                x.getEmployeeId() == employeeId));
    }
}
