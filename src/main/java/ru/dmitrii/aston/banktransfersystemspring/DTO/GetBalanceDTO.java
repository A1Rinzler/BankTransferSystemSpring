package ru.dmitrii.aston.banktransfersystemspring.DTO;

import java.math.BigDecimal;

/**
 * @author : Dmitrii Frolov, a.k.a. A1Rinzler
 * @created : 21.10.2026
 **/


public class GetBalanceDTO {

    private BigDecimal balance;

    public GetBalanceDTO(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
