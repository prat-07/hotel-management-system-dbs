package com.pratham.controller;

import com.pratham.dao.CustomerDAO;
import com.pratham.model.Customer;
import com.pratham.model.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private TextField textCIdAdd;
    @FXML
    private TextField textCity;
    @FXML
    private TextField textFName;
    @FXML
    private TextField textLName;
    @FXML
    private TextField textPNo;
    @FXML
    private TextField textPincode;
    @FXML
    private TextField textState;
    @FXML
    private TextField textStreet;
    @FXML
    private Button btnAddCustomer;

    @FXML
    private TextField textCIdRem;
    @FXML
    private Button btnRemCustomer;

    @FXML
    private Button btnShowAll;
    @FXML
    private Button btnShowStaying;

    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Long> colCID;
    @FXML
    private TableColumn<Customer, String> colCity;
    @FXML
    private TableColumn<Customer, String> colFName;
    @FXML
    private TableColumn<Customer, String> colLName;
    @FXML
    private TableColumn<Customer, Long> colPNo;
    @FXML
    private TableColumn<Customer, Integer> colPincode;
    @FXML
    private TableColumn<Customer, String> colState;
    @FXML
    private TableColumn<Customer, Boolean> colStaying;
    @FXML
    private TableColumn<Customer, String> colStreetNo;


    @FXML
    void addCustomerAndUpdate(ActionEvent event) {

    }

    @FXML
    void removeCustomerAndUpdate(ActionEvent event) {

    }

    @FXML
    void showAllCustomers(ActionEvent event) {
        List<Customer> customers = CustomerDAO.getAllCustomers();

        ObservableList<Customer> list = FXCollections.observableArrayList(customers);
        customerTable.setItems(list);

        //sort in ascending order of room number.
        colCID.setSortType(TableColumn.SortType.ASCENDING);
        customerTable.getSortOrder().add(colCID);

    }

    @FXML
    void showStayingCustomers(ActionEvent event) {
        List<Customer> customers = CustomerDAO.getStayingCustomers();

        ObservableList<Customer> list = FXCollections.observableArrayList(customers);
        customerTable.setItems(list);

        //sort in ascending order of room number.
        colCID.setSortType(TableColumn.SortType.ASCENDING);
        customerTable.getSortOrder().add(colCID);

    }


    @FXML
    void gotoDashboard(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/dashboard.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Initializing table view
        colCID.setCellValueFactory(new PropertyValueFactory<>("cid"));
        colFName.setCellValueFactory(new PropertyValueFactory<>("fName"));
        colLName.setCellValueFactory(new PropertyValueFactory<>("lName"));
        colPNo.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        colStreetNo.setCellValueFactory(new PropertyValueFactory<>("streetNo"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colState.setCellValueFactory(new PropertyValueFactory<>("state"));
        colPincode.setCellValueFactory(new PropertyValueFactory<>("pincode"));
        colStaying.setCellValueFactory(new PropertyValueFactory<>("staying"));
    }
}
