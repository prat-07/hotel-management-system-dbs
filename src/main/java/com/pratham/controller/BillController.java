package com.pratham.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class BillController {
    @FXML
    private Button backButton;

    @FXML
    private TableColumn<?, ?> colBID;

    @FXML
    private TableColumn<?, ?> colCID;

    @FXML
    private TableColumn<?, ?> colCheckIn;

    @FXML
    private TableColumn<?, ?> colCheckOut;

    @FXML
    private TableColumn<?, ?> colDaysStayed;

    @FXML
    private TableColumn<?, ?> colPNo;

    @FXML
    private TableColumn<?, ?> colRNo;

    @FXML
    private TableColumn<?, ?> colTotPrice;

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
