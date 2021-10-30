package datalayer.booking;

import dto.booking.Booking;
import dto.booking.BookingCreation;

import java.sql.SQLException;
import java.util.List;

public interface BookingStorage {
    public Booking getBookingWithId(int bookingId) throws SQLException;
    public List<Booking> getBookings() throws SQLException;
    public int createBooking(BookingCreation bookingToCreate) throws SQLException;
}
