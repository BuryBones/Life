package application;

import javafx.application.Platform;

public class Main {

  public static String[] arguments;

  public static void main(String[] args) {
    arguments = args;
    if (Configurations.TRACK_MEMORY) {
      Thread memoryTrack = new Thread(new MemoryMonitor());
      memoryTrack.setDaemon(true);
      memoryTrack.start();
    }
    App.launch(App.class);
  }
  public static void setConfigs() {
    try {
      Configurations.setConfigurations(arguments);
    } catch (InvalidArgumentsException e) {
      Graphics.getInstance().showConfigWarning(e.getMessage());
    }
    Logic.init();
  }

  public static void exit() {
    Platform.exit();
  }

}