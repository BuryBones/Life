package application;

import application.Controller.AlertsController;
import javafx.application.Platform;

public class Main {

  private static String[] arguments;

  public static void main(String[] args) {
    arguments = args;
    try {
      App.launch(App.class);
    } catch (Exception e) {
      AlertsController.getInstance().showErrorMessageAndExit(e.getMessage());
    }
  }

  public static void exit() {
    Platform.exit();
  }

  public static String[] getArguments() {
    return arguments;
  }
}