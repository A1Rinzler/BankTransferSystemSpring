package ru.dmitrii.aston.banktransfersystemspring.Event;

/**
 * @author : Dmitrii Frolov, a.k.a. A1Rinzler
 * @created : 23.10.2026
 **/

public class TransferExistEvent {

    private final int id;

    public TransferExistEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
