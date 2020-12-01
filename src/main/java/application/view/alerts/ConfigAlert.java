package application.view.alerts;

import application.Configurations;
import application.Main;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ConfigAlert extends Alert {

  public ConfigAlert(String message) {
    super(
        AlertType.WARNING,
        message + "\n" + Configurations.get().getArgumentsWarning(),
        ButtonType.OK, ButtonType.CLOSE);
  }

  public void pop() {
    Optional<ButtonType> result = showAndWait();
    if (result.isEmpty() || result.get() == ButtonType.CLOSE) {
      Main.exit();
    } else if (result.get() == ButtonType.OK) {
      System.out.println("Default settings");
    }
  }
}
