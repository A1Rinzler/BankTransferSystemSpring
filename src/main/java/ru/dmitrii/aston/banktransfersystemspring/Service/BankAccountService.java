package ru.dmitrii.aston.banktransfersystemspring.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmitrii.aston.banktransfersystemspring.DTO.GetAccountDTO;
import ru.dmitrii.aston.banktransfersystemspring.DTO.PostAccountDTO;
import ru.dmitrii.aston.banktransfersystemspring.Model.BankAccount;
import ru.dmitrii.aston.banktransfersystemspring.Repository.BankAccountRepository;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author : Dmitrii Frolov, a.k.a. A1Rinzler
 * @created : 21.10.2026
 **/

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }


    @Transactional
    public GetAccountDTO saveNewAccount(PostAccountDTO accountDTO){
        BankAccount bankAccount = new BankAccount(accountDTO.getAccountHolder(), accountDTO.getCurrency());

        BankAccount bankAccountSaved = bankAccountRepository.save(bankAccount);

        return new GetAccountDTO(bankAccountSaved.getId(), bankAccountSaved.getAccountHolder(), bankAccountSaved.getBalance(), bankAccountSaved.getCurrency());

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
