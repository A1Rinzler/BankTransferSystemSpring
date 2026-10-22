package ru.dmitrii.aston.banktransfersystemspring.DTO;

import java.math.BigDecimal;

/**
 * @author : Dmitrii Frolov, a.k.a. A1Rinzler
 * @created : 21.10.2026
 **/

public class PostAccountDTO {

    String accountHolder;


    String currency;

    public PostAccountDTO(String accountHolder, String currency) {
        this.accountHolder = accountHolder;
        this.currency = currency;
    }


    public String getAccountHolder() {
        return accountHolder;
    }

    public String getCurrency() {
        return currency;
    }

}
