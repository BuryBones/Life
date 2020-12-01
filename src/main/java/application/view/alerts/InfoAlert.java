package application.view.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class InfoAlert extends Alert {

  public InfoAlert(String message) {
    super(
        AlertType.INFORMATION,
        message,
        ButtonType.OK);
  }

  public void pop() {
    showAndWait();
  }
}
