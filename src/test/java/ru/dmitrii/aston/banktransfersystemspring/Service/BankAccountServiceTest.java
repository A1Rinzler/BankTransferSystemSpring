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
        BankAccount bankAccount = new BankAccount("Holder 1", "USD");
        RequestAccountDTO responseAccountDTO = new RequestAccountDTO("Holder 1", "USD");

        when (bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);
        ResponseAccountDTO responseAccountDTOCompare = bankAccountService.saveNewAccount(responseAccountDTO);

        assertEquals(bankAccount.getId(), responseAccountDTOCompare.getId());
        assertEquals(bankAccount.getAccountHolder(), responseAccountDTOCompare.getAccountHolder());
        assertEquals(bankAccount.getBalance(), responseAccountDTOCompare.getBalance());
        assertEquals(bankAccount.getCurrency(), responseAccountDTOCompare.getCurrency());
    }

    @Test
    void getOwnerBalance() {
    }

    @Test
    void transfer() {
    }

    @Test
    void getTransactionHistory() {
    }
}