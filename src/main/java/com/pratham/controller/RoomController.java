package com.pratham.controller;

import com.pratham.dao.RoomDAO;
import com.pratham.model.Room;
import com.pratham.model.RoomType;
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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class RoomController implements Initializable{

    @FXML
    private Button backBtn;

    @FXML
    private TableView<Room> roomTable;
    @FXML
    private TableColumn<Room, Integer> colRNo;
    @FXML
    private TableColumn<Room, RoomType> colRType;
    @FXML
    private TableColumn<Room, Double> colPrice;
    @FXML
    private TableColumn<Room, Boolean> colAvail;

    @FXML
    private TextField textAddRNo;
    @FXML
    private ComboBox<RoomType> comboBoxRType;
    @FXML
    private TextField textPrice;
    @FXML
    private Button addBtn;

    @FXML
    private TextField textRemRNo;
    @FXML
    private Button remBtn;
    @FXML

    private Button showAllBtn;
    @FXML
    private Button showAvailBtn;

    @FXML
    void gotoDashboard(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/dashboard.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    @FXML
    void showAllRooms(ActionEvent event) {
        List<Room> rooms = RoomDAO.getAllRooms();

        ObservableList<Room> list = FXCollections.observableArrayList(rooms);
        roomTable.setItems(list);

        //sort in ascending order of room number.
        colRNo.setSortType(TableColumn.SortType.ASCENDING);
        roomTable.getSortOrder().add(colRNo);
    }

    @FXML
    void showAvailRooms(ActionEvent event) {
        List<Room> rooms = RoomDAO.getAvailRooms();

        ObservableList<Room> list = FXCollections.observableArrayList(rooms);
        roomTable.setItems(list);

        //sort in ascending order of room number.
        colRNo.setSortType(TableColumn.SortType.ASCENDING);
        roomTable.getSortOrder().add(colRNo);
    }

    @FXML
    void addRoomAndUpdate(ActionEvent event){

        if(textAddRNo.getText().isEmpty() || (comboBoxRType.getValue() == null) || textPrice.getText().isEmpty()){
            AlertUtil.showWarning("Field(s) cannot be empty.");
            return;
        }

        try{
            int roomNo = Integer.parseInt(textAddRNo.getText());
            RoomType roomType = comboBoxRType.getValue();
            double roomPrice = Double.parseDouble(textPrice.getText());

            RoomDAO.addRoom(new Room(roomNo, roomType, roomPrice));

            showAllRooms(event);

        }catch(NumberFormatException e){
            AlertUtil.showError("Invalid Input.");
        }

    }

    @FXML
    void removeRoomAndUpdate(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Initializing combo box
        RoomType[] roomTypes = RoomType.values();
        comboBoxRType.getItems().addAll(roomTypes);

        //Initializing table view
        colRNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        colRType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colAvail.setCellValueFactory(new PropertyValueFactory<>("available"));

    }
}
