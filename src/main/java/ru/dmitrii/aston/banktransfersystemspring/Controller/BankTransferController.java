package ru.dmitrii.aston.banktransfersystemspring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dmitrii.aston.banktransfersystemspring.DTO.GetBalanceDTO;
import ru.dmitrii.aston.banktransfersystemspring.Model.BankAccount;
import ru.dmitrii.aston.banktransfersystemspring.Service.BankAccountService;

import java.math.BigDecimal;

/**
 * @author : Dmitrii Frolov, a.k.a. A1Rinzler
 * @created : 21.10.2026
 **/

@RestController
@RequestMapping("api/bank")
public class BankTransferController {

    private final BankAccountService bankAccountService;

    @Autowired
    public BankTransferController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/balance{id}")
    public GetBalanceDTO getOwnerBalance(@PathVariable int id){
        BigDecimal balance = bankAccountService.getOwnerBalance(id);
        return new GetBalanceDTO(balance);
    }

}
