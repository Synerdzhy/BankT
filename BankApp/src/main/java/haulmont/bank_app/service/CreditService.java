package haulmont.bank_app.service;

import haulmont.bank_app.dao.CreditDAO;
import haulmont.bank_app.data.Credit;

import java.util.List;
import java.util.UUID;

public class CreditService {
    private final CreditDAO creditDAO;

    public CreditService() {
        this.creditDAO = new CreditDAO();
    }

    public Credit getID(UUID id){
        return creditDAO.getById(id);
    }

    public List<Credit> getAllCredits(){
        return creditDAO.getAll();
    }

    public void save(Credit credit){
        creditDAO.save(credit);
    }

    public boolean remove(Credit credit){
        return creditDAO.remove(credit);
    }
}

