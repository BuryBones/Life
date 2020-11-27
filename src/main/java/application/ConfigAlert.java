package application;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ConfigAlert extends Alert {

  private static ConfigAlert alert;

  private ConfigAlert(String message) {
    super(
        AlertType.WARNING,
        message + "\n" + Configurations.argumentsWarning,
        ButtonType.OK, ButtonType.CLOSE);
  }

  public static void showConfigWarning(String message) {
    alert = new ConfigAlert(message);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isEmpty() || result.get() == ButtonType.CLOSE) {
      Main.exit();
    } else if (result.get() == ButtonType.OK) {
      System.out.println("Default settings");
    }
  }
}
