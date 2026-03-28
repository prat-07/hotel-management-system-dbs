package com.pratham.controller;

import com.pratham.dao.BookingDAO;
import com.pratham.model.Booking;
import com.pratham.model.BookingStatus;
import com.pratham.model.Customer;
import com.pratham.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.awt.print.Book;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class BookingController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private TableView<Booking> bookingTable;
    @FXML
    private TableColumn<Booking, Integer> colBId;
    @FXML
    private TableColumn<Booking, BookingStatus> colBookingStatus;
    @FXML
    private TableColumn<Booking, Long> colCContact;
    @FXML
    private TableColumn<Booking, Long> colCId;
    @FXML
    private TableColumn<Booking, LocalDate> colCheckin;
    @FXML
    private TableColumn<Booking, LocalDate> colCheckout;
    @FXML
    private TableColumn<Booking, Integer> colRoomNo;
    @FXML
    private TableColumn<Booking, Integer> colTotDays;


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
        List<Booking> bookings = BookingDAO.fetchAllBookings();

        ObservableList<Booking> list = FXCollections.observableArrayList(bookings);
        bookingTable.setItems(list);

        //sort whether staying or not.
        colBId.setSortType(TableColumn.SortType.ASCENDING);
        bookingTable.getSortOrder().add(colBId);
    }

    @FXML
    void addBookingAndUpdate(ActionEvent event) {

        if(textCID.getText().trim().isEmpty() ||
                textRoomNo.getText().trim().isEmpty()
                || dateCheckin.getValue() == null
                || dateCheckout.getValue() == null){

            AlertUtil.showWarning("Field(s) cannot be empty.");
            return;
        }

        try{
            long cid = Long.parseLong(textCID.getText().trim());
            int roomNo = Integer.parseInt(textRoomNo.getText().trim());
            LocalDate checkin = dateCheckin.getValue();
            LocalDate checkout = dateCheckout.getValue();

            BookingDAO.addBooking(cid, roomNo, checkin, checkout);

            showBookings();

        }catch (NumberFormatException e){
            AlertUtil.showWarning("Invalid Input.");
        }catch (Exception e){
            AlertUtil.showWarning(e.getMessage());
        }
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

        showBookings();
    }
}
