package as.servicelayer.notifications;

import as.dto.customer.Customer;
import dto.customer.SmsMessage;

import java.sql.Time;
import java.util.Date;

public class SmsServiceImpl implements SmsService{


    @Override
    public boolean sendSms(SmsMessage message){
        if(!message.getMessage().isBlank() && !message.getRecipient().isBlank()){
            System.out.println(message);
            return true;
        }
        return false;
    }

    @Override
    public SmsMessage constructMessage(Customer customer, Date date, Time start, Time end) {
        return new SmsMessage(customer.getFirstname() + " " + customer.getLastname(),
                "Dear " + customer.getFirstname() + " " + customer.getLastname() + ", your booking at: " +
                date.toString() + " " + start.toString() + "-" + end.toString() + ". Have been confirmed and approved in our system, have a nice day");
    }
}
