package ru.dmitrii.aston.banktransfersystemspring.Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.dmitrii.aston.banktransfersystemspring.DTO.RequestAccountDTO;
import ru.dmitrii.aston.banktransfersystemspring.DTO.ResponseAccountDTO;
import ru.dmitrii.aston.banktransfersystemspring.Model.BankAccount;
import ru.dmitrii.aston.banktransfersystemspring.Repository.BankAccountRepository;
import ru.dmitrii.aston.banktransfersystemspring.Repository.TransactionRepository;

import java.math.BigDecimal;
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
    }
}