package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Account;

public class FXMLController implements Initializable {

    ObservableList<String> speedList = FXCollections.observableArrayList("2 Mbps", "5 Mbps", "10 Mbps", "50 Mbps", "100 Mbps");
    ObservableList<String> capacityList = FXCollections.observableArrayList("1 GB", "5 GB", "10 GB", "20 GB", "30 GB", "50 GB", "100 GB", "200 GB", "300 GB", "Flat");

    @FXML
    private ComboBox speedComboBox;

    @FXML
    private ComboBox capacityComboBox;

    @FXML
    private RadioButton oneYearRBtn;

    @FXML
    private RadioButton twoYearsRBtn;

    @FXML
    private ToggleGroup contractGroup;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField address;

    @FXML
    private TextField email;

    @FXML
    private Label accId;

    @FXML
    private TextField firstNameDelete;

    @FXML
    private TextField lastNameDelete;

    @FXML
    private TextField accIdDelete;

    @FXML
    public TableView<Account> table;

    @FXML
    private TableColumn<Account, String> column1;

    @FXML
    private TableColumn<Account, String> column2;

    @FXML
    private TableColumn<Account, String> column3;

    @FXML
    private TableColumn<Account, String> column4;

    @FXML
    private TableColumn<Account, String> column5;

    @FXML
    private TableColumn<Account, String> column6;

    @FXML
    private TableColumn<Account, String> column7;

    @FXML
    private TableColumn<Account, String> column8;

    @FXML
    private Button save;

    ObservableList<Account> accounts = FXCollections.<Account>observableArrayList();
    ObservableList<Account> data = FXCollections.<Account>observableArrayList();
    Account account;

    public FXMLController() {
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        account = new Account();

        firstName.textProperty().bindBidirectional(account.firstNameProperty());
        lastName.textProperty().bindBidirectional(account.lastNameProperty());
        address.textProperty().bindBidirectional(account.addressProperty());
        email.textProperty().bindBidirectional(account.emailProperty());
        firstNameDelete.textProperty().bindBidirectional(account.firstNameDeleteProperty());
        lastNameDelete.textProperty().bindBidirectional(account.lastNameDeleteProperty());
        accIdDelete.textProperty().bindBidirectional(account.accIdDeleteProperty());

        speedComboBox.setValue("10 Mbps");
        speedComboBox.setItems(speedList);
        speedComboBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                account.speedProperty().set(newValue.toString());
                //System.out.println(account.speedProperty().get());
            }
        });

        capacityComboBox.setValue("100 GB");
        capacityComboBox.setItems(capacityList);
        capacityComboBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                account.capacityProperty().set(newValue.toString());
                //System.out.println(account.capacityProperty().get());
            }
        });

        account.speedProperty().set("10 Mbps");
        account.capacityProperty().set("100 GB");
        account.contractProperty().set("one year");

        contractGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (contractGroup.getSelectedToggle() != null) {
                    if (newValue.toString().contains("oneYearRBtn")) {
                        account.contractProperty().set("one year");
                    } else {
                        account.contractProperty().set("two years");
                    }
                    //System.out.println(account.contractProperty().get());
                }
            }
        });
        column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        column2.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        column3.setCellValueFactory(new PropertyValueFactory<>("address"));
        column4.setCellValueFactory(new PropertyValueFactory<>("email"));
        column5.setCellValueFactory(new PropertyValueFactory<>("accId"));
        column6.setCellValueFactory(new PropertyValueFactory<>("speed"));
        column7.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        column8.setCellValueFactory(new PropertyValueFactory<>("contract"));
        loadTable();
    }

    @FXML
    private void saveAccount() {
        if (account.isValid()) {
            Random r = new Random();
            StringBuilder id = new StringBuilder("acc_");
            id.append(account.firstNameProperty().get().charAt(0));
            id.append(account.lastNameProperty().get().charAt(0));
            id.append("_");
            id.append(r.nextInt(1000));
            account.accIdProperty().set(id.toString());
            accId.setText(id.toString());
            account.save();
            loadTable();
        } else {

            StringBuilder errMsg = new StringBuilder();

            ArrayList<String> errList = account.errorsProperty().get();

            for (String errList1 : errList) {
                errMsg.append(errList1);
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Account can't be saved!");
            alert.setHeaderText(null);
            alert.setContentText(errMsg.toString());
            alert.showAndWait();
            errList.clear();

        }
    }

    @FXML
    private void deleteAccount() {
        if (account.isValidForDelete()) {
            account.delete();
            loadTable();
        } else {

            StringBuilder errMsg = new StringBuilder();

            ArrayList<String> errList = account.errorsDeleteProperty().get();

            for (String errList1 : errList) {
                errMsg.append(errList1);
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Account can't be deleted!");
            alert.setHeaderText(null);
            alert.setContentText(errMsg.toString());
            alert.showAndWait();
            errList.clear();
        }
    }

    @FXML
    private void clearAccount() {

        //System.out.println(account);
        accId.setText("");
        account.accIdProperty().set("");
        account.firstNameProperty().set("");
        account.lastNameProperty().set("");
        account.addressProperty().set("");
        account.emailProperty().set("");
    }

    @FXML
    private void clearAccountDelete() {

        //System.out.println(account);
        account.setFirstNameDelete("");
        account.setLastNameDelete("");
        account.setAccIdDelete("");
    }

    @FXML
    private void loadTable() {
        try {
            data.clear();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/internet_packages", "root", "mysqluservezbanje");
            String query = "select * from contracts";
            PreparedStatement pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                data.add(new Account(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("speed"),
                        rs.getString("capacity"),
                        rs.getString("contract"),
                        rs.getString("accId")));
                table.setItems(data);
            }
            pst.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
