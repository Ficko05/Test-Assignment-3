package servicelayer.booking;

import datalayer.booking.BookingStorage;
import dto.booking.Booking;
import dto.booking.BookingCreation;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;
import java.util.Date;

public class BookingServiceImpl implements BookingService {

    private BookingStorage bookingStorage;

    public BookingServiceImpl(BookingStorage bookingStorage) {
        this.bookingStorage = bookingStorage;
    }

    @Override
    public int createBooking(int customerId, int employeeId, Date date, Time start, Time end) throws BookingServiceException {
        try {
            return bookingStorage.createBooking(new BookingCreation(customerId, employeeId, date, start, end));
        } catch (SQLException throwables) {
            throw new BookingServiceException(throwables.getMessage());
        }
    }

    @Override
    public Booking getBookingById(int id) throws SQLException {
        return bookingStorage.getBookingWithId(id);
    }

    @Override
    public Collection<Booking> getBookings() {
        return null;
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) {
        return bookingStorage.getBookingsForCustomer(customerId);
    }

    @Override
    public Collection<Booking> getBookingsForEmployee(int employeeId) {
        return null;
    }
}
