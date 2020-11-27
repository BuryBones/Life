package application.View.Alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class InfoAlert extends Alert {

  private static InfoAlert alert;

  private InfoAlert(String message) {
    super(
        AlertType.INFORMATION,
        message,
        ButtonType.OK);
  }

  public static void showInfoMessage(String message) {
    alert = new InfoAlert(message);
    alert.showAndWait();
  }
}
