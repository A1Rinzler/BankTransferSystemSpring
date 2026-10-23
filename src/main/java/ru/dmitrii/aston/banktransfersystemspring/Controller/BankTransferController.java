package ru.dmitrii.aston.banktransfersystemspring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmitrii.aston.banktransfersystemspring.DTO.RequestTransferDTO;
import ru.dmitrii.aston.banktransfersystemspring.DTO.ResponseAccountDTO;
import ru.dmitrii.aston.banktransfersystemspring.DTO.ResponseBalanceDTO;
import ru.dmitrii.aston.banktransfersystemspring.DTO.ResponseTransferDTO;
import ru.dmitrii.aston.banktransfersystemspring.Service.BankAccountService;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

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

    @GetMapping("/{id}/balance")
    public ResponseBalanceDTO getOwnerBalance(@PathVariable int id){
        BigDecimal balance = bankAccountService.getOwnerBalance(id);
        return new ResponseBalanceDTO(balance);
    }

    @PostMapping("/accounts")
    public ResponseAccountDTO createNewAccount(@RequestBody ResponseAccountDTO responseAccountDTO){
        return  bankAccountService.saveNewAccount(responseAccountDTO);
    }

    @PostMapping("/transactions")
    public ResponseEntity<ResponseTransferDTO> doTransfer(@RequestBody RequestTransferDTO requestTransferDTO) throws ExecutionException, InterruptedException {
        ResponseTransferDTO responseTransferDTO = bankAccountService.transfer(requestTransferDTO).get();
        return ResponseEntity.ok(responseTransferDTO);
    }

}
