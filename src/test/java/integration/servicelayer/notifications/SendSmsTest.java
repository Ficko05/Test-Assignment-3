package integration.servicelayer.notifications;

import as.dto.customer.Customer;
import dto.customer.SmsMessage;
import org.junit.jupiter.api.*;
import as.servicelayer.notifications.SmsService;
import as.servicelayer.notifications.SmsServiceImpl;

import java.sql.Time;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("UnitTest")
public class SendSmsTest {

    SmsService smsService;

    @BeforeAll
    public void setup() {
        smsService = new SmsServiceImpl();
    }

    @Test
    public void mustReturnTrueWhenSuppliedWithCustomerDateAndTime()  {
        // Arrange
        Customer customer = new Customer(1, "Allan", "Simonsen", new Date());
        Date bookingDay = new Date(150848458);
        Time start = new Time(1000);
        Time end = new Time(1000);
        SmsMessage smsMessage = smsService.constructMessage(customer, bookingDay, start, end);

        // Act
        boolean result = smsService.sendSms(smsMessage);

        // Assert
        assertTrue(result);
    }

    @Test
    public void mustReturnFalseWhenSuppliedWithEmptyStringCustomerDateAndTime()  {
        // Arrange
        Customer customer = new Customer(1, "", "", new Date());
        Date bookingDay = new Date(150848458);
        Time start = new Time(1000);
        Time end = new Time(1000);
        SmsMessage smsMessage = smsService.constructMessage(customer, bookingDay, start, end);

        // Act
        boolean result = smsService.sendSms(smsMessage);

        // Assert
        assertFalse(result);
    }

    @Test
    public void mustReturnExceptionWhenSuppliedNullCustomerDateAndTime()  {
        // Arrange
        SmsMessage smsMessage = null;

        // Act
        Assertions.assertThrows(NullPointerException.class, () -> {
            smsService.sendSms(smsMessage);
        });
    }

}
