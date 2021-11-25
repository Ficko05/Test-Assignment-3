package datalayer.booking;

import dto.booking.Booking;
import dto.booking.BookingCreation;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class BookingStorageImpl implements datalayer.booking.BookingStorage {
    private String connectionString;
    private String username, password;

    public BookingStorageImpl(String conStr, String user, String pass) {
        connectionString = conStr;
        username = user;
        password = pass;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }

    @Override
    public Booking getBookingWithId(int bookingId) throws SQLException {
        var sql = "select ID, customerId, employeeId, date, start, end from Bookings where id = ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);

            try (var resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    var id = resultSet.getInt("ID");
                    var customerId = resultSet.getInt("customerId");
                    var employeeId = resultSet.getInt("employeeId");
                    var date = resultSet.getDate("date");
                    var start = resultSet.getTime("start");
                    var end = resultSet.getTime("end");
                    return new Booking(id, customerId, employeeId, date, start, end);
                }
                return null;
            }
        }
    }

    @Override
    public Collection<Booking> getBookings() throws SQLException {
        try (var con = getConnection();
             var stmt = con.createStatement()) {
            var results = new ArrayList<Booking>();

            try (ResultSet resultSet = stmt.executeQuery("select ID, customerId, employeeId, date, start, end from Bookings")) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    int customerId = resultSet.getInt("customerId");
                    int employeeId = resultSet.getInt("employeeId");
                    Date date = resultSet.getDate("date");
                    Time start = resultSet.getTime("start");
                    Time end = resultSet.getTime("end");

                    Booking c = new Booking(id, customerId, employeeId, date, start, end);
                    results.add(c);
                }
            }

            return results;
        }
    }

    @Override
    public int createBooking(BookingCreation bookingToCreate) throws SQLException {
        var sql = "insert into Bookings(customerId, employeeId, date, start, end) values (?, ?, ?, ?, ?)";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, bookingToCreate.getCustomerId());
            stmt.setInt(2, bookingToCreate.getEmployeeId());

            // passes it from util.Date to sql.Date for the sql string above
            java.util.Date utilDate = bookingToCreate.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            stmt.setDate(3, sqlDate);
            stmt.setTime(4, bookingToCreate.getStart());
            stmt.setTime(5, bookingToCreate.getEnd());

            stmt.executeUpdate();

            // get the newly created id
            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                int newId = resultSet.getInt(1);
                return newId;
            }
        }
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) throws SQLException {
        var sql = "select ID, customerId, employeeId,date,start,end from Bookings where customerId= ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            var results = new ArrayList<Booking>();
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int employeeId = resultSet.getInt("employeeId");
                Date date = resultSet.getDate("date");
                Time start = resultSet.getTime("start");
                Time end = resultSet.getTime("end");
                Booking c = new Booking(id, customerId, employeeId, date, start, end);
                results.add(c);
            }
            return results;
        }
    }

    @Override
    public Collection<Booking> getBookingsForEmployee(int employeeId) throws SQLException {
        var sql = "select ID, customerId, employeeId,date,start,end from Bookings where employeeId= ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            var results = new ArrayList<Booking>();
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int customerId = resultSet.getInt("customerId");
                Date date = resultSet.getDate("date");
                Time start = resultSet.getTime("start");
                Time end = resultSet.getTime("end");
                Booking e = new Booking(id, customerId, employeeId, date, start, end);
                results.add(e);
            }
            return results;
        }
    }
}
