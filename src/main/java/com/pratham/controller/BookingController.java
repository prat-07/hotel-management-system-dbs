package com.pratham.controller;

import com.pratham.model.Booking;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class BookingController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private TableView<?> bookingTable;
    @FXML
    private TableColumn<?, ?> colBId;
    @FXML
    private TableColumn<?, ?> colBookingStatus;
    @FXML
    private TableColumn<?, ?> colCContact;
    @FXML
    private TableColumn<?, ?> colCId;
    @FXML
    private TableColumn<?, ?> colCheckin;
    @FXML
    private TableColumn<?, ?> colCheckout;
    @FXML
    private TableColumn<?, ?> colRoomNo;
    @FXML
    private TableColumn<?, ?> colTotDays;


    @FXML
    private TextField textCID;
    @FXML
    private TextField textRoomNo;
    @FXML
    private DatePicker dateCheckin;
    @FXML
    private DatePicker dateCheckout;
    @FXML
    private Button addButton;

    @FXML
    private TextField textCheckinCheckout;
    @FXML
    private Button checkinButton;
    @FXML
    private Button checkoutButton;

    @FXML
    private TextField textRemoveBooking;
    @FXML
    private Button removeBookingButton;


    public void showBookings(){

    }

    @FXML
    void addBookingAndUpdate(ActionEvent event) {

    }

    @FXML
    void checkinAndUpdate(ActionEvent event) {

    }

    @FXML
    void checkoutAndUpdate(ActionEvent event) {

    }



    @FXML
    void removeBookingAndUpdate(ActionEvent event) {

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

        colBId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colCId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCContact.setCellValueFactory(new PropertyValueFactory<>("customerContact"));
        colRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        colCheckin.setCellValueFactory(new PropertyValueFactory<>("checkinDate"));
        colCheckout.setCellValueFactory(new PropertyValueFactory<>("checkoutDate"));
        colTotDays.setCellValueFactory(new PropertyValueFactory<>("noOfDays"));
        colBookingStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
}
