package ru.dmitrii.aston.banktransfersystemspring.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmitrii.aston.banktransfersystemspring.DTO.RequestTransferDTO;
import ru.dmitrii.aston.banktransfersystemspring.DTO.ResponseAccountDTO;
import ru.dmitrii.aston.banktransfersystemspring.DTO.ResponseTransferDTO;
import ru.dmitrii.aston.banktransfersystemspring.Model.BankAccount;
import ru.dmitrii.aston.banktransfersystemspring.Model.Transaction;
import ru.dmitrii.aston.banktransfersystemspring.Repository.BankAccountRepository;
import ru.dmitrii.aston.banktransfersystemspring.Repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author : Dmitrii Frolov, a.k.a. A1Rinzler
 * @created : 21.10.2026
 **/

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository, TransactionRepository transactionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
    }


    @Transactional
    public ResponseAccountDTO saveNewAccount(ResponseAccountDTO accountDTO){
        BankAccount bankAccount = new BankAccount(accountDTO.getAccountHolder(), accountDTO.getCurrency());

        BankAccount bankAccountSaved = bankAccountRepository.save(bankAccount);

        return new ResponseAccountDTO(bankAccountSaved.getId(), bankAccountSaved.getAccountHolder(), bankAccountSaved.getBalance(), bankAccountSaved.getCurrency());

    }

    @Transactional
    public BigDecimal getOwnerBalance(int id){
        Optional<BankAccount> bankAccountOptional = bankAccountRepository.findById(id);
        if (bankAccountOptional.isEmpty()){
            throw new RuntimeException("Owner not found " + id);
        }
        return bankAccountOptional.get().getBalance();
    }

    public ResponseTransferDTO transfer(RequestTransferDTO requestTransferDTO){

        BankAccount transferFrom = bankAccountRepository.findById(requestTransferDTO.getFromAccount()).orElse(null);

        BankAccount transfetTo = bankAccountRepository.findById(requestTransferDTO.getToAccount()).orElse(null);

        BigDecimal amount = requestTransferDTO.getAmount();

        transferFrom.setBalance(transferFrom.getBalance().subtract(amount));
        transfetTo.setBalance(transfetTo.getBalance().add(amount));

        int fromId = transferFrom.getId();
        int toId = transfetTo.getId();

        //над наверно какой-то emum сделать и выставлять статус оттуда, сделать проверку или наф, реши утром.
        String transferStatus = "completed";

        Transaction transaction = new Transaction(fromId, toId, amount,transferStatus);

        //Transaction transaction1 = new Transaction(transferFrom.getId(), transfetTo.getId(), amount, "completed");


//        Transaction transaction = new Transaction();
//        transaction.setFromAccount(transferFrom.getId());
//        transaction.setToAccount(transfetTo.getId());
//        transaction.setAmount(amount);
//        transaction.setStatus("completed");

        Transaction save = transactionRepository.save(transaction);

        return new ResponseTransferDTO(save.getId(), save.getStatus());
    }



}
