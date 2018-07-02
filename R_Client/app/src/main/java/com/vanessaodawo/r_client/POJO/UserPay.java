package com.vanessaodawo.r_client.POJO;

import java.time.MonthDay;
import java.util.Date;

public class UserPay {

    int cardNumber, cardCode, billingCode;
    Date monthYear;

    public UserPay(int cardNumber, int cardCode, int billingCode, Date monthYear) {
        this.cardNumber = cardNumber;
        this.cardCode = cardCode;
        this.billingCode = billingCode;
        this.monthYear = monthYear;
    }

    public UserPay() {
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCardCode() {
        return cardCode;
    }

    public void setCardCode(int cardCode) {
        this.cardCode = cardCode;
    }

    public int getBillingCode() {
        return billingCode;
    }

    public void setBillingCode(int billingCode) {
        this.billingCode = billingCode;
    }

    public Date getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(Date monthYear) {
        this.monthYear = monthYear;
    }
}
