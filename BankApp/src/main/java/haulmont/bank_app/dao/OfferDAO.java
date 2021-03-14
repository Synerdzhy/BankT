package haulmont.bank_app.dao;

import haulmont.bank_app.data.Client;
import haulmont.bank_app.data.Credit;
import haulmont.bank_app.data.Offer;
import haulmont.bank_app.service.ConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OfferDAO implements DAO<Offer> {

    private final ConnectionService connectionService;


    public OfferDAO() {
        this.connectionService = ConnectionService.getInstance();
    }

    @Override
    public Offer getById(UUID id) {
        try {
            Statement statement = connectionService.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM OFFER WHERE ID = '%s'", id.toString()));
            while (resultSet.next()) {
                String clientUuid = resultSet.getString(2);
                Client client = new ClientDAO().getById(UUID.fromString(clientUuid));
                String creditUuid = resultSet.getString(3);
                Credit credit = new CreditDAO().getById(UUID.fromString(creditUuid));
                int date = resultSet.getInt(4);
                return new Offer(id, client, credit, date);
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return null;
    }

    @Override
    public void save(Offer offer) {
        try {
            Offer offerById = getById(offer.getId());
            Connection connection = connectionService.getConnection();
            if (offerById == null) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO OFFER (ID, CLIENT_ID, CREDIT_ID, DATE, BODY_SUM, PERCENT_SUM) " +
                                "VALUES (?, ?, ?, ?, ?, ?)"
                );
                preparedStatement.setString(1, offer.getId().toString());
                preparedStatement.setString(2, offer.getClient().getId().toString());
                preparedStatement.setString(3, offer.getCredit().getId().toString());
                preparedStatement.setInt(4, offer.getDate());
                preparedStatement.setDouble(5, offer.getOfferBody());
                preparedStatement.setDouble(6, offer.getOfferPercent());
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    @Override
    public boolean remove(Offer offer) {
        try {
            Offer offerById = getById(offer.getId());
            Statement statement = connectionService.getConnection().createStatement();
            statement.executeUpdate("DELETE FROM OFFER WHERE ID = '" + offerById.getId() + "'");
            return true;
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return false;
    }

    @Override
    public List<Offer> getAll() {
        List<Offer> offerList = new ArrayList<>();
        try {
            Statement statement = connectionService.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM OFFER");
            while (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getString(1));
                Client clientById = new ClientDAO().getById(UUID.fromString(resultSet.getString(2)));
                Credit creditById = new CreditDAO().getById(UUID.fromString(resultSet.getString(3)));
                int date = Integer.parseInt(resultSet.getString(4));
                offerList.add(new Offer(uuid, clientById, creditById, date));
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return offerList;
    }
}