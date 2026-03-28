package com.pratham.controller;

import com.pratham.dao.BillDAO;
import com.pratham.dao.BookingDAO;
import com.pratham.model.Bill;
import com.pratham.model.Booking;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class BillController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private TableView<Bill> billTable;
    @FXML
    private TableColumn<Bill, Integer> colBID;
    @FXML
    private TableColumn<Bill, Long> colCID;
    @FXML
    private TableColumn<Bill, Integer> colRNo;
    @FXML
    private TableColumn<Bill, LocalDate> colCheckIn;
    @FXML
    private TableColumn<Bill, LocalDate> colCheckOut;
    @FXML
    private TableColumn<Bill, Integer> colDaysStayed;
    @FXML
    private TableColumn<Bill, Long> colPNo;
    @FXML
    private TableColumn<Bill, Double> colTotPrice;

    @FXML
    void gotoDashboard(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/dashboard.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();

    }

    void showBills(){
        List<Bill> bills = BillDAO.fetchBills();

        ObservableList<Bill> list = FXCollections.observableArrayList(bills);
        billTable.setItems(list);

        //sort whether staying or not.
        colBID.setSortType(TableColumn.SortType.ASCENDING);
        billTable.getSortOrder().add(colBID);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colBID.setCellValueFactory(new PropertyValueFactory<>("bid"));
        colCID.setCellValueFactory(new PropertyValueFactory<>("cid"));
        colPNo.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        colRNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        colCheckIn.setCellValueFactory(new PropertyValueFactory<>("checkin"));
        colCheckOut.setCellValueFactory(new PropertyValueFactory<>("checkout"));
        colDaysStayed.setCellValueFactory(new PropertyValueFactory<>("daysStayed"));
        colTotPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        showBills();
    }
}
