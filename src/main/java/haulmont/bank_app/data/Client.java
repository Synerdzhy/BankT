package haulmont.bank_app.data;

import java.util.Objects;
import java.util.UUID;

public class Client {
    private UUID id;
    private String fio;
    private String phone;
    private String email;
    private String passportNum;

    public Client(UUID id, String fio, String phone, String email, String passportNum) {
        this.id = id;
        this.fio = fio;
        this.phone = phone;
        this.email = email;
        this.passportNum = passportNum;
    }


    public UUID getId() {
        return id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassportNum(String passportNum) {
        this.passportNum = passportNum;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassportNum() {
        return passportNum;
    }

    @Override
    public String toString() {
        return fio + " | " + passportNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) || Objects.equals(fio, client.fio) || Objects.equals(passportNum, client.passportNum);
    }

}
