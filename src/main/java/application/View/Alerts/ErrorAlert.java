package application.View.Alerts;

import application.Configurations;
import application.Controller.MainController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ErrorAlert extends Alert {

  private static ErrorAlert alert;

  private ErrorAlert(String message) {
    super(
        AlertType.ERROR,
        "Error message: " + message + Configurations.ERROR_EXIT_STRING,
        ButtonType.CLOSE);
  }

  public static void showErrorMessageAndExit(String message) {
    alert = new ErrorAlert(message);
    alert.showAndWait();
    MainController.getInstance().exit();
  }

}
