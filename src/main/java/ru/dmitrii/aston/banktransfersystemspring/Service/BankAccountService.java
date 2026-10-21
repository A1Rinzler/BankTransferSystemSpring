package ru.dmitrii.aston.banktransfersystemspring.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.dmitrii.aston.banktransfersystemspring.Model.BankAccount;
import ru.dmitrii.aston.banktransfersystemspring.Repository.BankAccountRepository;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author : Dmitrii Frolov, a.k.a. A1Rinzler
 * @created : 21.10.2026
 **/

public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional
    public BigDecimal getOwnerBalance(int id){
        Optional<BankAccount> bankAccountOptional = bankAccountRepository.findById(id);
        if (bankAccountOptional.isEmpty()){
            throw new RuntimeException("Owner not found " + id);
        }
        return bankAccountOptional.get().getBalance();
    }
}
