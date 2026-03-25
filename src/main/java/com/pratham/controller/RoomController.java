package com.pratham.controller;

import com.pratham.model.RoomType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class RoomController {
    @FXML
    private Button addBtn;

    @FXML
    private Button backBtn;

    @FXML
    private TableColumn<?, ?> colAvail;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colRNo;

    @FXML
    private TableColumn<?, ?> colRType;

    @FXML
    private ComboBox<?> comboBoxRType;

    @FXML
    private Button remBtn;

    @FXML
    private Button showAllBtn;

    @FXML
    private Button showAvailBtn;

    @FXML
    private TextField textAddRNo;

    @FXML
    private TextField textPrice;

    @FXML
    private TextField textRemRNo;

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
