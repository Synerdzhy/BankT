package haulmont.bank_app.controller;


import haulmont.bank_app.data.Client;
import haulmont.bank_app.data.Credit;
import haulmont.bank_app.data.Offer;
import haulmont.bank_app.service.ClientService;
import haulmont.bank_app.service.CreditService;
import haulmont.bank_app.service.OfferService;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.util.UUID;

public class Controller {
    private final ClientService clientService = new ClientService();
    public TextField fioWrite;
    public TextField phoneWrite;
    public TextField emailWrite;
    public TextField passportWrite;
    public TableView<Client> clientTable;
    public TableColumn<Client, String> fio;
    public TableColumn<Client, String> phone;
    public TableColumn<Client, String> email;
    public TableColumn<Client, String> passportNum;
    public Button delClient;
    public TextField tfPayDate;
    @FXML
    private Button btnAddClient;

    private final CreditService creditService = new CreditService();
    public TextField limitWrite;
    public TextField percentWrite;
    public TextField monthWrite;
    public TableView<Credit> creditTable;
    public TableColumn<Credit, Integer> lim;
    public TableColumn<Credit, Integer> percent;
    public TableColumn<Credit, Integer> month;
    public Button delCredit;
    @FXML
    public Button btnAddCredit;

    private final OfferService offerService = new OfferService();
    public TableView<Offer> offerTable;
    public TableColumn<Offer, String> offerClient;
    public TableColumn<Offer, Double> offerSum;
    public TableColumn<Offer, String> offerDate;
    public TableColumn<Offer, Double> offerBody;
    public TableColumn<Offer, Double> offerPercent;

    public Button delOffer;
    public Button btnAddOffer;
    public ComboBox<Client> cbClient;
    public ComboBox<Credit> cbCredit;


    private void initClientTable() {
        fio.setCellValueFactory(new PropertyValueFactory<>("fio"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        passportNum.setCellValueFactory(new PropertyValueFactory<>("passportNum"));
        clientTable.setItems(FXCollections.observableArrayList(clientService.getAllClients()));
    }

    private void initCreditTable() {
        lim.setCellValueFactory(new PropertyValueFactory<>("lim"));
        percent.setCellValueFactory(new PropertyValueFactory<>("percent"));
        month.setCellValueFactory(new PropertyValueFactory<>("month"));
        creditTable.setItems(FXCollections.observableArrayList(creditService.getAllCredits()));
    }

    private void initOfferTable() {
        offerClient.setCellValueFactory(new PropertyValueFactory<>("fio"));
        offerSum.setCellValueFactory(new PropertyValueFactory<>("sum"));
        offerDate.setCellValueFactory(new PropertyValueFactory<>("time"));
        offerBody.setCellValueFactory(new PropertyValueFactory<>("offerBody"));
        offerPercent.setCellValueFactory(new PropertyValueFactory<>("offerPercent"));
        offerTable.setItems(FXCollections.observableArrayList(offerService.getAllOffers()));
    }

    private void fillClientComboBox() {
        cbClient.getItems().clear();
        clientService.getAllClients().forEach(client -> cbClient.getItems().add(client));
    }

    private void fillCreditComboBox() {
        cbCredit.getItems().clear();
        creditService.getAllCredits().forEach(credit -> cbCredit.getItems().add(credit));
    }

    @FXML
    private void initialize() {
        initClientTable();
        initCreditTable();
        initOfferTable();

        addActionToButton(btnAddClient, addClientEvent());
        addActionToButton(btnAddCredit, addCreditEvent());
        addActionToButton(btnAddOffer, addOfferEvent());

        addActionToButton(delClient, deleteClientRowEvent());
        addActionToButton(delCredit, deleteCreditRowEvent());
        addActionToButton(delOffer, deleteOfferRowEvent());

        fillClientComboBox();
        fillCreditComboBox();

        editClientTable();
        editCreditTable();
    }

    private void addActionToButton(Button btn, EventHandler<ActionEvent> event) {
        btn.setOnAction(event);
    }

    private void editCreditTable() {
        creditTable.setEditable(true);

        lim.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        lim.setOnEditCommit(t -> {
                    Credit credit = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    credit.setLim(t.getNewValue());
                    creditService.save(credit);
                    fillCreditComboBox();
                }
        );

        percent.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        percent.setOnEditCommit(t -> {
                    Credit credit = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    credit.setPercent(t.getNewValue());
                    creditService.save(credit);
                    fillCreditComboBox();
                }
        );

        month.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        month.setOnEditCommit(t -> {
                    Credit credit = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    credit.setMonth(t.getNewValue());
                    creditService.save(credit);
                    fillCreditComboBox();
                }
        );
    }

    private void editClientTable() {
        clientTable.setEditable(true);

        fio.setCellFactory(TextFieldTableCell.forTableColumn());
        fio.setOnEditCommit(t -> {
                    Client client = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    client.setFio(t.getNewValue());
                    clientService.save(client);
                    fillClientComboBox();
                }
        );

        phone.setCellFactory(TextFieldTableCell.forTableColumn());
        phone.setOnEditCommit(t -> {
                    Client client = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    client.setPhone(t.getNewValue());
                    clientService.save(client);
                    fillClientComboBox();
                }
        );

        email.setCellFactory(TextFieldTableCell.forTableColumn());
        email.setOnEditCommit(t -> {
                    Client client = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    client.setEmail(t.getNewValue());
                    clientService.save(client);
                    fillClientComboBox();
                }
        );

        passportNum.setCellFactory(TextFieldTableCell.forTableColumn());
        passportNum.setOnEditCommit(t -> {
                    Client client = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    client.setPassportNum(t.getNewValue());
                    clientService.save(client);
                    fillClientComboBox();
                }
        );
    }

    private EventHandler<ActionEvent> deleteClientRowEvent() {
        return event -> {
            Client selectedItem = clientTable.getSelectionModel().getSelectedItem();
            if (clientService.remove(selectedItem)) {
                initClientTable();
                fillClientComboBox();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setContentText("Выберите клиента!");
                alert.show();
            }
        };
    }

    private EventHandler<ActionEvent> deleteCreditRowEvent() {
        return event -> {
            Credit selectedItem = creditTable.getSelectionModel().getSelectedItem();
            if (creditService.remove(selectedItem)) {
                initCreditTable();
                fillCreditComboBox();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setContentText("Выберите кредит!");
                alert.show();
            }
        };
    }

    private EventHandler<ActionEvent> deleteOfferRowEvent() {
        return event -> {
            Offer selectedItem = offerTable.getSelectionModel().getSelectedItem();
            if (offerService.remove(selectedItem)) {
                initOfferTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setContentText("Выберите кредитное предложение!");
                alert.show();
            }
        };
    }


    private EventHandler<ActionEvent> addOfferEvent() {
        return event -> {
            Client client = cbClient.getSelectionModel().getSelectedItem();
            Credit credit = cbCredit.getSelectionModel().getSelectedItem();
            if (client != null && credit != null) {
                try {
                    UUID uuid = UUID.randomUUID();
                    Offer offer = new Offer(uuid, client, credit, Integer.parseInt(tfPayDate.getText()));
                    if (noUnique(offer)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(null);
                        alert.setContentText("Такое предложение уже существует");
                        alert.show();
                    } else {
                        offerService.save(offer);
                        initOfferTable();
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(null);
                    alert.setContentText("Дата платежа должна быть числом");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setContentText("Клиент/кредит не выбран");
                alert.show();
            }
        };
    }

    private EventHandler<ActionEvent> addCreditEvent() {
        return event -> {
            UUID uuid = UUID.randomUUID();
            String lim = limitWrite.getText();
            String percent = percentWrite.getText();
            String month = monthWrite.getText();
            if (lim.matches("\\d+") && percent.matches("\\d{1,2}") && month.matches("\\d{1,2}")) {
                Credit credit = new Credit(uuid, Integer.parseInt(lim), Integer.parseInt(percent), Integer.parseInt(month));
                if (noUnique(credit)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(null);
                    alert.setContentText("Такой кредит есть в базе");
                    alert.show();
                } else {
                    creditService.save(credit);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(null);
                    alert.setContentText("Кредит добавлен");
                    alert.show();
                    initCreditTable();
                    fillCreditComboBox();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setContentText("Не корректные данные");
                alert.show();
            }
        };
    }

    private EventHandler<ActionEvent> addClientEvent() {
        return event -> {
            UUID uuid = UUID.randomUUID();
            String fioClient = fioWrite.getText();
            String phoneClient = phoneWrite.getText();
            String emailClient = emailWrite.getText();
            String passportClient = passportWrite.getText();
            if (fioClient.matches("\\D+")
                    && phoneClient.matches("\\d{11}")
                    && emailClient.matches("\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*\\.\\w{2,4}")
                    && passportClient.matches("\\d{6}+")) {
                Client client = new Client(uuid, fioClient, phoneClient, emailClient, passportClient);
                if (noUnique(client)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(null);
                    alert.setContentText("Данные не уникальны! Клиент есть в базе");
                    alert.show();
                } else {
                    clientService.save(client);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(null);
                    alert.setContentText("Клиент добавлен");
                    alert.show();
                    initClientTable();
                    fillClientComboBox();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setContentText("Не корректные данные");
                alert.show();
            }
        };
    }

    private boolean noUnique(Credit credit) {
        return creditService.getAllCredits().stream().anyMatch(val -> val.equals(credit));
    }

    private boolean noUnique(Offer offer) {
        return offerService.getAllOffers().stream().anyMatch(val -> val.equals(offer));
    }

    private boolean noUnique(Client client) {
        return clientService.getAllClients().stream()
                .anyMatch(val -> val.equals(client));
    }
}