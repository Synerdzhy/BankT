package haulmont.bank_app.dao;

import haulmont.bank_app.data.Credit;
import haulmont.bank_app.service.ConnectionService;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreditDAO implements DAO<Credit> {
    private final ConnectionService connectionService;

    public CreditDAO() {
        connectionService = ConnectionService.getInstance();
    }

    @Override
    public Credit getById(UUID id) {
        try {
            Statement statement = connectionService.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM CREDIT WHERE ID = '%s'", id.toString()));
            while (resultSet.next()) {
                int limit = resultSet.getInt(2);
                int percent = resultSet.getInt(3);
                int month = resultSet.getInt(4);
                return new Credit(id, limit, percent, month);
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return null;
    }

    @Override
    public void save(Credit credit) {
        try {
            Credit creditById = getById(credit.getId());
            Statement statement = connectionService.getConnection().createStatement();
            if (creditById == null) {
                statement.executeUpdate(String.format("INSERT INTO credit (id,lim,percent,month) VALUES ('%s', %d, %d, %d)",
                        credit.getId().toString(), credit.getLim(), credit.getPercent(), credit.getMonth()));
            } else {
                statement.executeUpdate(String.format("UPDATE credit SET lim = %d, percent = %d, month = %d WHERE ID = '%s'",
                        credit.getLim(), credit.getPercent(), credit.getMonth(), credit.getId().toString()));
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    @Override
    public boolean remove(Credit credit) {
        try {
            Credit creditById = getById(credit.getId());
            Statement statement = connectionService.getConnection().createStatement();
            if (creditById != null) {
                statement.executeUpdate("DELETE FROM CREDIT WHERE ID = '" + creditById.getId() + "'");
                return true;
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return false;
    }

    @Override
    public List<Credit> getAll() {
        List<Credit> creditList = new ArrayList<>();
        try {
            Statement statement = connectionService.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CREDIT");
            while (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getString(1));
                int lim = resultSet.getInt(2);
                int percent = resultSet.getInt(3);
                int month = resultSet.getInt(4);
                creditList.add(new Credit(uuid, lim, percent, month));
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return creditList;
    }
}
