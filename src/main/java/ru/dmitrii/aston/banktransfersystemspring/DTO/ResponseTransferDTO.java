package ru.dmitrii.aston.banktransfersystemspring.DTO;

/**
 * @author : Dmitrii Frolov, a.k.a. A1Rinzler
 * @created : 22.10.2026
 **/

public class ResponseTransferDTO {
    int id;
    String status;

    public ResponseTransferDTO(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
