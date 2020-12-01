package application.controller;

import application.view.alerts.ConfigAlert;
import application.view.alerts.ErrorAlert;
import application.view.alerts.InfoAlert;

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

  public InfoAlert getInfoAlert(String message) {
    return new InfoAlert(message);
  }

  public ErrorAlert getErrorAlert(String message) {
    return new ErrorAlert(message);
  }

  public ConfigAlert getConfigAlert(String message) {
    return new ConfigAlert(message);
  }

}
