package integration.servicelayer.notifications;

import org.junit.Ignore;
import org.junit.jupiter.api.*;
import as.servicelayer.booking.BookingServiceException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
@Ignore
public class CreateBookingTest {



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
        // Assert
    }


}