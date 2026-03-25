package com.pratham.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class BookingController {
    @FXML
    private Button addButton;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<?, ?> colBId;

    @FXML
    private TableColumn<?, ?> colCContact;

    @FXML
    private TableColumn<?, ?> colCId;

    @FXML
    private TableColumn<?, ?> colCheckin;

    @FXML
    private TableColumn<?, ?> colCheckout;

    @FXML
    private TableColumn<?, ?> colRoonNo;

    @FXML
    private TableColumn<?, ?> colTotDays;

    @FXML
    private Button removeButton;

    @FXML
    private TextField textBID;

    @FXML
    private TextField textCID;

    @FXML
    private TextField textCheckIN;

    @FXML
    private TextField textCheckOUT;

    @FXML
    private TextField textRoomNo;

    @FXML
    void gotoDashboard(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/dashboard.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();

    }
}
