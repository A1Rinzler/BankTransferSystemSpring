package ru.dmitrii.aston.banktransfersystemspring.Service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.dmitrii.aston.banktransfersystemspring.DTO.RequestTransferDTO;
import ru.dmitrii.aston.banktransfersystemspring.DTO.ResponseAccountDTO;
import ru.dmitrii.aston.banktransfersystemspring.DTO.ResponseTransferDTO;
import ru.dmitrii.aston.banktransfersystemspring.Event.TransferExistEvent;
import ru.dmitrii.aston.banktransfersystemspring.Model.BankAccount;
import ru.dmitrii.aston.banktransfersystemspring.Model.Transaction;
import ru.dmitrii.aston.banktransfersystemspring.Repository.BankAccountRepository;
import ru.dmitrii.aston.banktransfersystemspring.Repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @author : Dmitrii Frolov, a.k.a. A1Rinzler
 * @created : 21.10.2026
 **/

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;
    private final AsyncTransferService asyncTransferService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public BankAccountService(BankAccountRepository bankAccountRepository, TransactionRepository transactionRepository, AsyncTransferService asyncTransferService, ApplicationEventPublisher applicationEventPublisher) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
        this.asyncTransferService = asyncTransferService;
        this.applicationEventPublisher = applicationEventPublisher;
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
        //todo над переделать возвращать статус с ошибкой, а не просто текст
        if (bankAccountOptional.isEmpty()){
            throw new RuntimeException("Owner not found " + id);
        }
        return bankAccountOptional.get().getBalance();
    }

    @Async
    @Transactional
    public CompletableFuture<ResponseTransferDTO> transfer(RequestTransferDTO requestTransferDTO){
        int idFrom = requestTransferDTO.getFromAccount();
        int idTo = requestTransferDTO.getToAccount();
        String transferStatus = "completed";

        BankAccount transferFrom = bankAccountRepository.findById(idFrom).orElseThrow(() -> new IllegalArgumentException("Нет такого владельца"));
        BankAccount transfetTo = bankAccountRepository.findById(idTo).orElseThrow(() -> new IllegalArgumentException("Нет такого владельца"));

        BigDecimal amount = requestTransferDTO.getAmount();

        if (transferFrom.getBalance().compareTo(requestTransferDTO.getAmount()) < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недостаточно средств");
        }

        transferFrom.setBalance(transferFrom.getBalance().subtract(amount));
        transfetTo.setBalance(transfetTo.getBalance().add(amount));

//        int fromId = transferFrom.getId();
//        int toId = transfetTo.getId();

        //над наверно какой-то emum сделать и выставлять статус оттуда, сделать проверку или наф, реши утром.


        Transaction transaction = new Transaction(idFrom, idTo, amount, transferStatus);

        //Transaction transaction1 = new Transaction(transferFrom.getId(), transfetTo.getId(), amount, "completed");


//        Transaction transaction = new Transaction();
//        transaction.setFromAccount(transferFrom.getId());
//        transaction.setToAccount(transfetTo.getId());
//        transaction.setAmount(amount);
//        transaction.setStatus("completed");


        Transaction save = transactionRepository.save(transaction);

        //applicationEventPublisher.publishEvent(new TransferExistEvent(save.getId()));
        //asyncTransferService.asyncTransfer(save.getId());

        ResponseTransferDTO responseTransferDTO = new ResponseTransferDTO(save.getId(), save.getStatus());
        return  CompletableFuture.completedFuture(responseTransferDTO);
    }



}
