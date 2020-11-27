package application;

import application.Controller.AlertsController;
import application.Model.Logic;
import javafx.application.Platform;

public class Main {

  private static String[] arguments;

  public static void main(String[] args) {
    arguments = args;
    if (Configurations.TRACK_MEMORY) {
      // launches a thread that writes occupied memory to console
      Thread memoryTrack = new Thread(new MemoryMonitor());
      memoryTrack.setDaemon(true);
      memoryTrack.start();
    }
    try {
      App.launch(App.class);
    } catch (Exception e) {
      AlertsController.getInstance().showErrorMessageAndExit(e.getMessage());
    }
  }
  public static void setConfigs() {
    // TODO: move to some controller
    Configurations.setConfigurations(arguments);
    Logic.init();
  }

  public static void exit() {
    Platform.exit();
  }

}