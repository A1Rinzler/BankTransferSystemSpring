package ru.dmitrii.aston.banktransfersystemspring.DTO;

import java.math.BigDecimal;

/**
 * @author : Dmitrii Frolov, a.k.a. A1Rinzler
 * @created : 22.10.2026
 **/

public class ResponseAccountDTO {
    int id;

    String accountHolder;

    BigDecimal balance;

    String currency;


    public ResponseAccountDTO(int id, String accountHolder, BigDecimal balance, String currency) {
        this.id = id;
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.currency = currency;
    }

    public int getId() {
        return id;
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
