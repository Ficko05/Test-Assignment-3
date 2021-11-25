package as.servicelayer.notifications;

import as.dto.customer.Customer;
import dto.customer.SmsMessage;

import java.sql.Time;
import java.util.Date;

public interface SmsService {
    /**
     * Sends an sms message.
     * @param message
     * @return true if success, false otherwise
     */
    boolean sendSms(SmsMessage message);

    SmsMessage constructMessage(Customer customer, Date date, Time start, Time end);
}
