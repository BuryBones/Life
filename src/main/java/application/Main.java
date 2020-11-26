package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

  public static String[] arguments;

  @Override
  public void start(Stage stage) {
    setConfigs(arguments);
    Graphics.getInstance().start(stage);
  }

  public static void main(String[] args) {
    arguments = args;
    if (Configurations.TRACK_MEMORY) {
      Thread memoryTrack = new Thread(new MemoryMonitor());
      memoryTrack.setDaemon(true);
      memoryTrack.start();
    }
    launch();
  }

  private static void setConfigs(String[] args) {
    try {
      Configurations.setConfigurations(args);
    } catch (InvalidArgumentsException e) {
      Graphics.getInstance().showConfigWarning(e.getMessage());
    }
    Logic.init();
  }

  public static void exit() {
    System.exit(0);
  }

}