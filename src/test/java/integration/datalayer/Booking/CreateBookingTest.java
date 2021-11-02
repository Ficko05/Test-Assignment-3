package integration.datalayer.Booking;


import datalayer.booking.BookingStorage;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.*;

import java.sql.SQLDataException;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
public class CreateBookingTest extends ContainerizedDbIntegrationTest {

    private BookingStorage bookingStorage;

    @BeforeAll
    public void Setup() throws SQLDataException{

    }

    @Test
    public void test() {
        // Arrange

        // Act

        // Assert

    }


}
