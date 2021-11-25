package integration.servicelayer.notifications;

import as.dto.customer.Customer;
import dto.customer.SmsMessage;
import org.junit.jupiter.api.*;
import as.servicelayer.notifications.SmsService;
import as.servicelayer.notifications.SmsServiceImpl;

import java.sql.Time;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("UnitTest")
public class ConstructMessageTest {

    SmsService smsService;

    @BeforeAll
    public void setup() {
        smsService = new SmsServiceImpl();
    }

    @Test
    public void mustReturnStringFromConstructMessageWhenSuppliedCustomerDateAndTime()  {
        // Arrange
        Customer customer = new Customer(1, "Allan", "Simonsen", new Date());
        Date bookingDay = new Date(150848458);
        Time start = new Time(1000);
        Time end = new Time(1000);
        String expectedMessage = "Dear Allan Simonsen, your booking at: Fri Jan 02 18:54:08 CET 1970 01:00:01-01:00:01. Have been confirmed and approved in our system, have a nice day";

        // Act
        SmsMessage result = smsService.constructMessage(customer,bookingDay,start,end);

        // Assert
        assertEquals(customer.getFirstname() + " " + customer.getLastname(), result.getRecipient());
        assertEquals(result.getMessage(), expectedMessage);
    }

    @Test
    public void mustReturnSmsMessageWithSuppliedEmptyStringCustomerDateAndTime()  {
        // Arrange
        Customer customer = new Customer(1, "", "", new Date());
        Date bookingDay = new Date(150848458);
        Time start = new Time(1000);
        Time end = new Time(1000);
        String expectedMessage = "Dear  , your booking at: Fri Jan 02 18:54:08 CET 1970 01:00:01-01:00:01. Have been confirmed and approved in our system, have a nice day";

        // Act
        SmsMessage result = smsService.constructMessage(customer,bookingDay,start,end);

        // Assert
        assertEquals(customer.getFirstname() + " " + customer.getLastname(), result.getRecipient());
        assertEquals(result.getMessage(), expectedMessage);
    }

    @Test
    public void mustReturnExceptionWhenSuppliedNullCustomerDateAndTime()  {
        // Arrange
        Customer customer = null;
        Date bookingDay = new Date(150848458);
        Time start = new Time(1000);
        Time end = new Time(1000);

        // Act//Assert
        Assertions.assertThrows(NullPointerException.class, () -> {
            smsService.constructMessage(customer,bookingDay,start,end);
        });
    }

    @Test
    public void mustReturnSmsMessageWithSuppliedCustomerNullDateAndTime()  {
        // Arrange
        Customer customer = new Customer(1, "", "", new Date());
        Date bookingDay = null;
        Time start = new Time(1000);
        Time end = new Time(1000);

        // Act//Assert
        Assertions.assertThrows(NullPointerException.class, () -> {
            smsService.constructMessage(customer,bookingDay,start,end);
        });
    }
    @Test
    public void mustReturnSmsMessageWithSuppliedCustomerDateAndNullTime()  {
        // Arrange
        Customer customer = new Customer(1, "", "", new Date());
        Date bookingDay = new Date(150848458);
        Time start = null;
        Time end = new Time(1000);

        // Act //Assert
        Assertions.assertThrows(NullPointerException.class, () -> {
            smsService.constructMessage(customer,bookingDay,start,end);
        });
    }
}
