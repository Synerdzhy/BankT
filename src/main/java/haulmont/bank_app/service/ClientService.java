package haulmont.bank_app.service;


import haulmont.bank_app.dao.ClientDAO;
import haulmont.bank_app.data.Client;

import java.util.List;
import java.util.UUID;

public class ClientService {
    private final ClientDAO clientDAO;

    public ClientService() {
        clientDAO = new ClientDAO();
    }

    public Client getID(UUID id){
        return clientDAO.getById(id);
    }

    public List<Client> getAllClients() {
        return clientDAO.getAll();
    }

    public void save (Client client){
        clientDAO.save(client);
    }

    public boolean remove (Client client){
        return clientDAO.remove(client);
    }


}
