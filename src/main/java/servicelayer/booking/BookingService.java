package servicelayer.booking;

import dto.booking.Booking;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;
import java.util.Date;

public interface BookingService {
    int createBooking(int customerId, int employeeId, Date date, Time start, Time end) throws BookingServiceException;
    Booking getBookingById(int id) throws SQLException;
    Collection<Booking> getBookingsById(int id);
}
