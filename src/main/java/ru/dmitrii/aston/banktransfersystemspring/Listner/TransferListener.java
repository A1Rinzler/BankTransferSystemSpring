package ru.dmitrii.aston.banktransfersystemspring.Listner;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import ru.dmitrii.aston.banktransfersystemspring.Event.TransferExistEvent;
import ru.dmitrii.aston.banktransfersystemspring.Service.AsyncTransferService;

/**
 * @author : Dmitrii Frolov, a.k.a. A1Rinzler
 * @created : 23.10.2026
 **/

@Component
public class TransferListener {

    private final AsyncTransferService asyncTransferService;

    public TransferListener(AsyncTransferService asyncTransferService) {
        this.asyncTransferService = asyncTransferService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void checkTransfer(TransferExistEvent transferExistEvent){
        asyncTransferService.asyncTransfer(transferExistEvent.getId());
    }
}
