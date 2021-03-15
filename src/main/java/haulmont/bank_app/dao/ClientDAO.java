package haulmont.bank_app.dao;

import haulmont.bank_app.data.Client;
import haulmont.bank_app.service.ConnectionService;


import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientDAO implements DAO<Client> {
    private final ConnectionService connectionService;

    public ClientDAO() {
        this.connectionService = ConnectionService.getInstance();
    }

    @Override
    public Client getById(UUID id) {
        try {
            Statement statement = connectionService.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM CLIENT WHERE ID = '%s'", id.toString()));
            while (resultSet.next()) {
                String fio = resultSet.getString(2);
                String phone = resultSet.getString(3);
                String email = resultSet.getString(4);
                String passport = resultSet.getString(5);
                return new Client(id, fio, phone, email, passport);
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return null;
    }

    @Override
    public void save(Client client) {
        try {
            Client clientById = getById(client.getId());
            Statement statement = connectionService.getConnection().createStatement();
            if (clientById == null) {
                statement.executeUpdate(String.format("INSERT INTO client (id,fio, phone, email, passport) VALUES ('%s','%s', '%s', '%s','%s')",
                        client.getId().toString(), client.getFio(), client.getPhone(), client.getEmail(), client.getPassportNum()));
            } else {
                statement.executeUpdate(String.format("UPDATE client SET fio = '%s',  phone = '%s', email = '%s', passport = '%s' WHERE id = '%s'",
                        client.getFio(), client.getPhone(), client.getEmail(), client.getPassportNum(), client.getId().toString()));
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    @Override
    public boolean remove(Client client) {
        try {
            Client clientById = getById(client.getId());
            Statement statement = connectionService.getConnection().createStatement();
            if (clientById != null) {
                statement.executeUpdate("DELETE FROM CLIENT WHERE ID = '" + clientById.getId() + "'");
                return true;
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return false;
    }

    @Override
    public List<Client> getAll() {
        List<Client> clientList = new ArrayList<>();
        try {
            Statement statement = connectionService.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CLIENT");
            while (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getString(1));
                String fio = resultSet.getString(2);
                String phone = resultSet.getString(3);
                String email = resultSet.getString(4);
                String passport = resultSet.getString(5);
                clientList.add(new Client(uuid,fio,phone,email,passport));
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return clientList;
    }

}
