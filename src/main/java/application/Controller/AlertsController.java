package application.Controller;

import application.View.Alerts.ConfigAlert;
import application.View.Alerts.ErrorAlert;
import application.View.Alerts.InfoAlert;

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
    InfoAlert.showInfoMessage(message);
  }

  public void showErrorMessageAndExit(String message) {
    ErrorAlert.showErrorMessageAndExit(message);
  }

  public void showConfigWarning (String message) {
    ConfigAlert.showConfigWarning(message);
  }

}
