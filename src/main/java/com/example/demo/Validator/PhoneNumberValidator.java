package com.example.demo.Validator;


import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class PhoneNumberValidator {

    public static String formatPhoneNumber(String phoneNumber, String countryCode) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            // Parse the phone number with the given country code
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phoneNumber, countryCode);

            // Check if the phone number is valid
            if (phoneNumberUtil.isValidNumber(number)) {
                // Format the phone number in E.164 format
                return phoneNumberUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);
            } else {
                throw new IllegalArgumentException("Invalid phone number: " + phoneNumber);
            }
        } catch (NumberParseException e) {
            throw new IllegalArgumentException("Failed to parse phone number: " + phoneNumber, e);
        }
    }
}