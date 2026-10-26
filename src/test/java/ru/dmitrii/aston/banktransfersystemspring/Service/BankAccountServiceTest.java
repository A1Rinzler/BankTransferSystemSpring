package ru.dmitrii.aston.banktransfersystemspring.Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.dmitrii.aston.banktransfersystemspring.DTO.RequestAccountDTO;
import ru.dmitrii.aston.banktransfersystemspring.DTO.ResponseAccountDTO;
import ru.dmitrii.aston.banktransfersystemspring.DTO.ResponseTranstactionDTO;
import ru.dmitrii.aston.banktransfersystemspring.Model.BankAccount;
import ru.dmitrii.aston.banktransfersystemspring.Model.Transaction;
import ru.dmitrii.aston.banktransfersystemspring.Repository.BankAccountRepository;
import ru.dmitrii.aston.banktransfersystemspring.Repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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