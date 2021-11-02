package integration.servicelayer.notifications;

import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import dto.customer.Customer;
import dto.customer.CustomerCreation;
import dto.customer.SmsMessage;
import integration.ContainerizedDbIntegrationTest;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceException;
import servicelayer.booking.BookingServiceImpl;
import servicelayer.customer.CustomerService;
import servicelayer.customer.CustomerServiceImpl;
import servicelayer.notifications.SmsService;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
@Ignore
public class CreateBookingTest {

    private BookingService bookingService;
    @Mock
    private SmsService smsService;
    @Mock
    private CustomerService cs;
    @Mock
    private BookingStorage bs;

    @BeforeAll
    public void Setup() throws SQLException {
        /*cs = new CustomerServiceImpl(CustomerStorage);
        CustomerStorage customerStorage = new CustomerStorageImpl();
        when(cs.getCustomerById(any(Integer.class))).then(new Answer<Customer>() {
            int sequence = 1;

            @Override
            public Customer answer(InvocationOnMock invocation) throws Throwable {
                Customer cs = new Customer(1, "Allan", "Simonsen", new Date());
                return cs;
            }
        });

        bookingService = new BookingServiceImpl(bs, cs);*/
    }

    @Test
    public void test() throws BookingServiceException {
        // Arrange
        /*Customer customer = new Customer(1, "Allan", "Simonsen", new Date());
        Date bookingDay = new Date(150848458);
        Time start = new Time(1000);
        Time end = new Time(1000);
        String expectedMessage = "Dear Allan Simonsen, your booking at: Fri Jan 02 18:54:08 CET 1970 01:00:01-01:00:01. Have been confirmed and approved in our system, have a nice day";
        */
        // Act
        int result = bookingService.createBooking(1, 1, new Date(), new Time(1000), new Time(1000));

        // Assert
        assertEquals(1, result);
    }


}