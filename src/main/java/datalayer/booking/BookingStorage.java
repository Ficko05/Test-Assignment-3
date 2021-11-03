package datalayer.booking;

import dto.booking.Booking;
import dto.booking.BookingCreation;

import java.sql.SQLException;
import java.util.Collection;

public interface BookingStorage {
    public Booking getBookingWithId(int bookingId) throws SQLException;
    public Collection<Booking> getBookings() throws SQLException;
    public int createBooking(BookingCreation bookingToCreate) throws SQLException;

    public Collection<Booking> getBookingsForCustomer(int customerId) throws SQLException;

    Collection<Booking> getBookingsForEmployee(int employeeId) throws SQLException;
}
