package integration.datalayer.Booking;


import com.github.javafaker.Faker;
import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.employee.EmployeeStorage;
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
@Tag("integration")
public class CreateBookingTest extends ContainerizedDbIntegrationTest {

    private BookingStorage bookingStorage;

    private CustomerStorage customerStorage;
    private EmployeeStorage employeeStorage;

    @BeforeAll
    public void Setup() throws SQLException {
        runMigration(3);
        bookingStorage = new BookingStorageImpl(getConnectionString(), "root", getDbPassword());

        var numBooking = bookingStorage.getBookings().size();
        if (numBooking < 100) {
            addFakerBooking(100 - numBooking);
        }
    }

    private void addFakerBooking(int numBooking) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numBooking; i++) {
            BookingCreation b = new BookingCreation(faker.idNumber().hashCode(), faker.idNumber().hashCode(), faker.date().birthday(), new Time(12), new Time(14));
            bookingStorage.createBooking(b);
        }
    }

    @Test
    public void mustSaveBookingInDatabaseWhenCallingCreateBooking() throws SQLException, IOException {
        // Arrange
        customerStorage.createCustomer(new CustomerCreation("John","Carlssonn", new Date() ));
        employeeStorage.createEmployee(new EmployeeCreation("emp","emp",new Date()));


        // Act
        bookingStorage.createBooking(new BookingCreation(1,
                1, new Date(), new Time(12), new Time(13)));
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
        assertEquals(1,id2-id1);
    }

}
