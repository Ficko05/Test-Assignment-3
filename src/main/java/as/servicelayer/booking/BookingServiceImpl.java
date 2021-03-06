package as.servicelayer.booking;

import datalayer.booking.BookingStorage;
import dto.booking.Booking;
import dto.booking.BookingCreation;
import as.dto.customer.Customer;
import dto.customer.SmsMessage;
import as.servicelayer.customer.CustomerService;
import as.servicelayer.notifications.SmsService;
import as.servicelayer.notifications.SmsServiceImpl;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;
import java.util.Date;

public class BookingServiceImpl implements BookingService {

    private BookingStorage bookingStorage;
    private CustomerService customerService;
    private SmsService smsService;

    public BookingServiceImpl(BookingStorage bookingStorage, CustomerService customerService) {

        this.bookingStorage = bookingStorage;
        this.customerService = customerService;
        smsService = new SmsServiceImpl();
    }

    @Override
    public int createBooking(int customerId, int employeeId, Date date, Time start, Time end) throws BookingServiceException {
        try {
            Customer c = customerService.getCustomerById(customerId);
            int bookingID = bookingStorage.createBooking(new BookingCreation(customerId, employeeId, date, start, end));
            if(bookingID > 0){
                SmsMessage message = smsService.constructMessage(c, date, start, end);
                smsService.sendSms(message);
            }
            return bookingID;
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
    public Collection<Booking> getBookingsForCustomer(int customerId) throws SQLException {
        return bookingStorage.getBookingsForCustomer(customerId);
    }

    @Override
    public Collection<Booking> getBookingsForEmployee(int employeeId) throws SQLException {
        return bookingStorage.getBookingsForEmployee(employeeId);
    }
}
