package ru.dmitrii.aston.banktransfersystemspring.Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import ru.dmitrii.aston.banktransfersystemspring.DTO.*;
import ru.dmitrii.aston.banktransfersystemspring.Model.BankAccount;
import ru.dmitrii.aston.banktransfersystemspring.Model.Transaction;
import ru.dmitrii.aston.banktransfersystemspring.Repository.BankAccountRepository;
import ru.dmitrii.aston.banktransfersystemspring.Repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author : Dmitrii Frolov, a.k.a. A1Rinzler
 * @created : 26.10.2026
 **/
@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {

    @Mock
    BankAccountRepository bankAccountRepository;
    @Mock
    TransactionRepository transactionRepository;
    @InjectMocks
    BankAccountService bankAccountService;

    @Test
    void saveNewAccount() {
        //BankAccount bankAccount = new BankAccount("Holder 1", "USD");
        RequestAccountDTO requestAccountDTO = new RequestAccountDTO("Holder 1", "USD");


        when (bankAccountRepository.save(any(BankAccount.class)))
                .thenAnswer(returnMyRequestAccountDTO -> returnMyRequestAccountDTO.getArgument(0));
        ResponseAccountDTO responseAccountDTOCompare = bankAccountService.saveNewAccount(requestAccountDTO);

        assertEquals(0, responseAccountDTOCompare.getId()); //короче возвращаем тут 0, потому что id не инкрементируется в моке, надо руками как то передавать.
        assertEquals("Holder 1", responseAccountDTOCompare.getAccountHolder());
        assertEquals(new BigDecimal(0), responseAccountDTOCompare.getBalance());
        assertEquals("USD", responseAccountDTOCompare.getCurrency());
    }

    @Test
    void getOwnerBalance() {

        BankAccount bankAccount = new BankAccount("Holder 1", "USD");
        bankAccount.setBalance(new BigDecimal(200));

        when(bankAccountRepository.findById(1)).thenReturn(Optional.of(bankAccount));

        BigDecimal balance = bankAccountService.getOwnerBalance(1);

        assertEquals(new BigDecimal(200), balance);
    }

//    @Test
//    void notEnoughMoney() {
//
//        BankAccount from = new BankAccount("Holder1", "USD");
//        from.setBalance(new BigDecimal(100));
//
//        BankAccount to = new BankAccount("Holder2", "USD");
//
//        RequestTransferDTO requestTransferDTO = new RequestTransferDTO(1, 2 , new BigDecimal(200));
//
//        when(bankAccountRepository.findById(1)).thenReturn(Optional.of(from));
//        when(bankAccountRepository.findById(2)).thenReturn(Optional.of(to));
//
//        // responseTranstactionDTO = bankAccountService.transfer(requestTransferDTO);
//        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> bankAccountService.transfer(requestTransferDTO)); //если вылетает исключение, значит поймали эксепшен
//
//        assertEquals("Недостаточно средств", responseStatusException.getReason());
//
//    }

    @Test
    void isEnoughMoney() {

        BankAccount from = new BankAccount("Holder1", "USD");
        from.setBalance(new BigDecimal(200));

        BankAccount to = new BankAccount("Holder2", "USD");

        RequestTransferDTO requestTransferDTO = new RequestTransferDTO(1, 2 , new BigDecimal(200));

        when(bankAccountRepository.findById(1)).thenReturn(Optional.of(from));
        when(bankAccountRepository.findById(2)).thenReturn(Optional.of(to));

        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertDoesNotThrow(() -> bankAccountService.transfer(requestTransferDTO)); //так, если не будет никакого исключения, то денег хватает. Отдельно ловит так се.

        // responseTranstactionDTO = bankAccountService.transfer(requestTransferDTO);
        //ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> bankAccountService.transfer(requestTransferDTO)); //если вылетает исключение, значит поймали эксепшен

        //assertNotEquals("Недостаточно средств", responseStatusException.getReason());
        //assertDoesNotThrow(() -> bankAccountService.transfer(requestTransferDTO));
    }

    @Test
    void transfer() {

    }

    @Test
    void getTransactionHistory() {
        List<Transaction> transactionList = List.of(
                new Transaction(1, 2, new BigDecimal(200), "completed"),
                //new Transaction(1, 3, new BigDecimal(300), "USD"),
                //new Transaction(1, 4, new BigDecimal(400), "USD"),
                new Transaction(1, 5, new BigDecimal(500), "completed")
        );


        when(transactionRepository.findByFromAccount(1)).thenReturn(transactionList);

        List<ResponseTranstactionDTO> transactions = bankAccountService.getTransactionHistory(1);

        ResponseTranstactionDTO responseTranstactionDTO1 = transactions.get(0);
        ResponseTranstactionDTO responseTranstactionDTO2 = transactions.get(1);

        assertEquals(0, responseTranstactionDTO1.getTransactionId()); //id транзакции в моем случае 0. не хочу сетер добавлять для теста.
        assertEquals(1, responseTranstactionDTO1.getFromAccount());
        assertEquals(2, responseTranstactionDTO1.getToAccount());
        assertEquals(new BigDecimal(200), responseTranstactionDTO1.getAmount());
        assertEquals(transactionList.get(0).getTimeStamp(), responseTranstactionDTO1.getTimeStamp());
        assertEquals("completed", responseTranstactionDTO1.getStatus());

        assertEquals(0, responseTranstactionDTO2.getTransactionId()); //id транзакции в моем случае 0. не хочу сетер добавлять для теста.
        assertEquals(1, responseTranstactionDTO2.getFromAccount());
        assertEquals(5, responseTranstactionDTO2.getToAccount());
        assertEquals(new BigDecimal(500), responseTranstactionDTO2.getAmount());
        assertEquals(transactionList.get(1).getTimeStamp(), responseTranstactionDTO2.getTimeStamp());
        assertEquals("completed", responseTranstactionDTO2.getStatus());



    }
}