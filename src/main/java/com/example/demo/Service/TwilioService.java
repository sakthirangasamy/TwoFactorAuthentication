package com.example.demo.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    public void sendSms(String toPhoneNumber, String messageBody) {
        try {
            Twilio.init(accountSid, authToken);
            System.out.println("Sending SMS from: " + twilioPhoneNumber); // Debugging
            System.out.println("Sending SMS to: " + toPhoneNumber); // Debugging
            Message message = Message.creator(
                    new PhoneNumber(toPhoneNumber), // To phone number
                    new PhoneNumber(twilioPhoneNumber), // From Twilio phone number
                    messageBody
            ).create();
            System.out.println("SMS Sent Successfully! SID: " + message.getSid());
        } catch (Exception e) {
            System.err.println("Failed to send SMS: " + e.getMessage());
            e.printStackTrace();
        }
    }
}