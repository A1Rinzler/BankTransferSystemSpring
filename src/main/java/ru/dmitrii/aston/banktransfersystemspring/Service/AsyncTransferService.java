package ru.dmitrii.aston.banktransfersystemspring.Service;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.dmitrii.aston.banktransfersystemspring.Model.BankAccount;
import ru.dmitrii.aston.banktransfersystemspring.Model.Transaction;
import ru.dmitrii.aston.banktransfersystemspring.Repository.BankAccountRepository;
import ru.dmitrii.aston.banktransfersystemspring.Repository.TransactionRepository;

import java.math.BigDecimal;

/**
 * @author : Dmitrii Frolov, a.k.a. A1Rinzler
 * @created : 23.10.2026
 **/

@Service
public class AsyncTransferService {

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;

    public AsyncTransferService(TransactionRepository transactionRepository, BankAccountRepository bankAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Async
    @Transactional
    public void asyncTransfer(int tansferId){

        Transaction transaction = transactionRepository.findById(tansferId).orElseThrow(() -> new IllegalArgumentException("Транзакция не найдена"));

        int idFrom = transaction.getFromAccount();
        int idTo = transaction.getToAccount();

        BankAccount transferFrom = bankAccountRepository.findById(idFrom).orElseThrow(() -> new IllegalArgumentException("Нет такого владельца"));
        BankAccount transferTo = bankAccountRepository.findById(idTo).orElseThrow(() -> new IllegalArgumentException("Нет такого владельца"));

        BigDecimal amount = transaction.getAmount();

        if (transferFrom.getBalance().compareTo(amount) < 0){
//            transaction.setStatus("failed");
//            return;
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недостаточно средств");
        }

        transferFrom.setBalance(transferFrom.getBalance().subtract(amount));
        transferTo.setBalance(transferTo.getBalance().add(amount));

        transaction.setStatus("completed");
    }



}
