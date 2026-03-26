package com.pratham.util;

import javafx.scene.control.Alert;

public class AlertUtil {

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alert");
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alert");
        alert.setHeaderText("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }
}