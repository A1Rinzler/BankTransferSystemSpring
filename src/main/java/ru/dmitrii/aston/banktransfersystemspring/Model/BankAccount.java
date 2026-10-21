package ru.dmitrii.aston.banktransfersystemspring.Model;


import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * @author : Dmitrii Frolov, a.k.a. A1Rinzler
 * @created : 21.10.2026
 **/

@Entity
@Table
public class BankAccount {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column (name = "accountholder")
    String accountHolder;

    @Column (name = "balance")
    BigDecimal balance;

    @Column (name = "currency")
    String currency;

    public BankAccount(String accountHolder, BigDecimal balance, String currency) {
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

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BankAccount() {
    }
}
