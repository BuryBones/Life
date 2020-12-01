package application.view.alerts;

import application.Configurations;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ErrorAlert extends Alert {

  public ErrorAlert(String message) {
    super(
        AlertType.ERROR,
        "Error message: " + message + Configurations.get().getErrorExitString(),
        ButtonType.CLOSE);
  }

  public void pop() {
    showAndWait();
  }

}
