package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Account {

    private final StringProperty firstName = new SimpleStringProperty(this, "firstName", "");
    private final StringProperty lastName = new SimpleStringProperty(this, "lastName", "");
    private final StringProperty address = new SimpleStringProperty(this, "address", "");
    private final StringProperty email = new SimpleStringProperty(this, "email", "");
    private final StringProperty speed = new SimpleStringProperty(this, "speed", "");
    private final StringProperty capacity = new SimpleStringProperty(this, "capacity", "");
    private final StringProperty contract = new SimpleStringProperty(this, "contract", "");
    private final StringProperty accId = new SimpleStringProperty(this, "accId", "");
    private final StringProperty firstNameDelete = new SimpleStringProperty(this, "firstNameDelete", "");
    private final StringProperty lastNameDelete = new SimpleStringProperty(this, "lastNameDelete", "");
    private final StringProperty accIdDelete = new SimpleStringProperty(this, "accIdDelete", "");

    public Account() {
    }

    public Account(String firstName, String lastName, String address, String email, String speed, String capacity, String contract, String accId) {
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.address.set(address);
        this.email.set(email);
        this.speed.set(speed);
        this.capacity.set(capacity);
        this.contract.set(contract);
        this.accId.set(accId);
    }

    public Account(String firstName) {
        this.firstName.set(firstName);
    }

    public Account(String firstName, String lastName) {
        this.firstName.set(firstName);
        this.lastName.set(lastName);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getSpeed() {
        return speed.get();
    }

    public void setSpeed(String speed) {
        this.speed.set(speed);
    }

    public StringProperty speedProperty() {
        return speed;
    }

    public String getCapacity() {
        return capacity.get();
    }

    public void setCapacity(String capacity) {
        this.capacity.set(capacity);
    }

    public StringProperty capacityProperty() {
        return capacity;
    }

    public String getContract() {
        return contract.get();
    }

    public void setContract(String contract) {
        this.contract.set(contract);
    }

    public StringProperty contractProperty() {
        return contract;
    }

    public String getAccId() {
        return accId.get();
    }

    public void setAccId(String accId) {
        this.accId.set(accId);
    }

    public StringProperty accIdProperty() {
        return accId;
    }

    public String getFirstNameDelete() {
        return firstNameDelete.get();
    }

    public void setFirstNameDelete(String firstNameDelete) {
        this.firstNameDelete.set(firstNameDelete);
    }

    public Property firstNameDeleteProperty() {
        return firstNameDelete;
    }

    public String getLastNameDelete() {
        return lastNameDelete.get();
    }

    public void setLastNameDelete(String lastNameDelete) {
        this.lastNameDelete.set(lastNameDelete);
    }

    public Property lastNameDeleteProperty() {
        return lastNameDelete;
    }

    public String getAccIdDelete() {
        return accIdDelete.get();
    }

    public void setAccIdDelete(String accIdDelete) {
        this.accIdDelete.set(accIdDelete);
    }

    public StringProperty accIdDeleteProperty() {
        return accIdDelete;
    }

    private final ObjectProperty<ArrayList<String>> errorList = new SimpleObjectProperty<>(this, "errorList", new ArrayList<>());

    public ObjectProperty<ArrayList<String>> errorsProperty() {
        return errorList;
    }

    private final ObjectProperty<ArrayList<String>> errorDeleteList = new SimpleObjectProperty<>(this, "errorDeleteList", new ArrayList<>());

    public ObjectProperty<ArrayList<String>> errorsDeleteProperty() {
        return errorDeleteList;
    }

    public boolean isValid() {

        boolean isValid = true;

        if (firstName.get() != null && firstName.get().equals("")) {
            errorList.getValue().add("First name can't be empty! ");
            isValid = false;
        }
        if (lastName.get().equals("")) {
            errorList.getValue().add("Last name can't be empty! ");
            isValid = false;
        }
        if (address.get().equals("")) {
            errorList.getValue().add("Address can't be empty! ");
            isValid = false;
        }
        if (email.get().equals("")) {
            errorList.getValue().add("Email can't be empty! ");
            isValid = false;
        }

        return isValid;
    }

    public boolean isValidForDelete() {

        boolean isValidForDelete = true;

        if (firstNameDelete.get() != null && firstNameDelete.get().equals("")) {
            errorDeleteList.getValue().add("First name can't be empty! ");
            isValidForDelete = false;
        }
        if (lastNameDelete.get().equals("")) {
            errorDeleteList.getValue().add("Last name can't be empty! ");
            isValidForDelete = false;
        }
        if (accIdDelete.get().equals("")) {
            errorDeleteList.getValue().add("Account ID can't be empty! ");
            isValidForDelete = false;
        }
        if (!searchForDelete()) {
            errorDeleteList.getValue().add("Such person or account ID does not exist!");
            isValidForDelete = false;
        }

        return isValidForDelete;
    }

    public boolean save() {
        if (isValid()) {
            //System.out.println("Account details: \n" + this + "\nsaved!");

            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/internet_packages", "root", "mysqluservezbanje");
                String sql = "insert into contracts (firstName,lastName,address,email,speed,capacity,contract,accId) values(?,?,?,?,?,?,?,?)";
                PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pst.setString(1, firstName.get());
                pst.setString(2, lastName.get());
                pst.setString(3, address.get());
                pst.setString(4, email.get());
                pst.setString(5, speed.get());
                pst.setString(6, capacity.get());
                pst.setString(7, contract.get());
                pst.setString(8, accId.get());
                pst.execute();
                conn.close();
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "First name: " + firstName.get() + "\n" + "Last name: " + lastName.get() + "\n" + "Address: " + address.get() + "\n" + "Email: " + email.get() + "\n" + "Account ID: " + accId.get() + "\n" + "Contract: " + contract.get() + "\n" + "Speed: " + speed.get() + "\n" + "Capacity: " + capacity.get() + "\n";
    }

    public boolean delete() {
        if (isValidForDelete()) {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/internet_packages", "root", "mysqluservezbanje");
                String sql = "delete from contracts where firstName= ? and lastName=? and accId=?";
                PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pst.setString(1, firstNameDelete.get());
                pst.setString(2, lastNameDelete.get());
                pst.setString(3, accIdDelete.get());
                pst.execute();
                conn.close();
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return false;
    }

    private boolean searchForDelete() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/internet_packages", "root", "mysqluservezbanje");
            String sql = "select * from contracts where firstName= ? and lastName=? and accId=?";
            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, firstNameDelete.get());
            pst.setString(2, lastNameDelete.get());
            pst.setString(3, accIdDelete.get());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                conn.close();
                pst.close();
                rs.close();
                return true;
            } else {
                conn.close();
                pst.close();
                rs.close();
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
