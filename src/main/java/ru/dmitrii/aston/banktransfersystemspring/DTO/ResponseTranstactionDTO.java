package ru.dmitrii.aston.banktransfersystemspring.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author : Dmitrii Frolov, a.k.a. A1Rinzler
 * @created : 26.10.2026
 **/

public class ResponseTranstactionDTO {

    int transactionId;

    int fromAccount;

    int toAccount;

    BigDecimal amount;

    LocalDateTime timeStamp;

    String status;

    public ResponseTranstactionDTO(int transactionId, int fromAccount, int toAccount, BigDecimal amount, LocalDateTime timeStamp, String status) {
        this.transactionId = transactionId;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.timeStamp = timeStamp;
        this.status = status;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getFromAccount() {
        return fromAccount;
    }

    public int getToAccount() {
        return toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getStatus() {
        return status;
    }
}
