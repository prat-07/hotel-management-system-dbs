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

public class CustomerController {
    @FXML
    private Button backButton;

    @FXML
    private Button btnAddCustomer;

    @FXML
    private Button btnRemCustomer;

    @FXML
    private TableColumn<?, ?> colCID;

    @FXML
    private TableColumn<?, ?> colCity;

    @FXML
    private TableColumn<?, ?> colFName;

    @FXML
    private TableColumn<?, ?> colLName;

    @FXML
    private TableColumn<?, ?> colPNo;

    @FXML
    private TableColumn<?, ?> colPincode;

    @FXML
    private TableColumn<?, ?> colState;

    @FXML
    private TableColumn<?, ?> colStreetNo;

    @FXML
    private TextField textCIdAdd;

    @FXML
    private TextField textCIdRem;

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
    void gotoDashboard(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/dashboard.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();

    }
}
