package ru.dmitrii.aston.banktransfersystemspring.DTO;

import jakarta.persistence.Column;

import java.math.BigDecimal;

/**
 * @author : Dmitrii Frolov, a.k.a. A1Rinzler
 * @created : 21.10.2026
 **/

public class NewAccountDTO {

    String accountHolder;

    BigDecimal balance;

    String currency;

    public NewAccountDTO(String accountHolder, BigDecimal balance, String currency) {
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.currency = currency;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }
}
