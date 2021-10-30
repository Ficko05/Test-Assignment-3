package dto.booking;

import java.sql.Time;
import java.util.Date;

public class BookingCreation {
    public int getCustomerId() {
        return customerId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public Date getDate() {
        return date;
    }

    public Time getStart() {
        return start;
    }

    public Time getEnd() {
        return end;
    }

    private int customerId;
    private int employeeId;
    private Date date;
    private Time start;
    private Time end;


    public BookingCreation(int customerId, int employeeId, Date date, Time start, Time end) {
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.date = date;
        this.start = start;
        this.end = end;
    }
}
