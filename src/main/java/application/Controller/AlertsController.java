package application.Controller;

import application.View.Alerts.ConfigAlert;
import application.View.Alerts.ErrorAlert;
import application.View.Alerts.InfoAlert;
import javafx.application.Platform;

public class AlertsController {

  private static AlertsController instance = new AlertsController();

  private AlertsController() {

  }

  public static AlertsController getInstance() {
    if (instance == null) {
      instance = new AlertsController();
    }
    return instance;
  }

  public void showInfoMessage(String message) {
    Platform.runLater(() -> InfoAlert.showInfoMessage(message));
  }

  public void showErrorMessageAndExit(String message) {
    Platform.runLater(() -> ErrorAlert.showErrorMessageAndExit(message));
  }

  public void showConfigWarning (String message) {
    ConfigAlert.showConfigWarning(message);
  }

}
