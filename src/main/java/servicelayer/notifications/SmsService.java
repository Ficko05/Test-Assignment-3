package servicelayer.notifications;

import dto.customer.SmsMessage;

public interface SmsService {
    /**
     * Sends an sms message.
     * @param message
     * @return true if success, false otherwise
     */
    boolean sendSms(SmsMessage message);
}
