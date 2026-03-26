package com.pratham;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Objects;

public class App extends Application {

    public static void main( String[] args ) throws Exception {

        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/login.fxml")));
        Scene scene = new Scene(root);
        Image icon = new Image("/images/hotel-icon.png");

        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Login");
        primaryStage.setResizable(false);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
