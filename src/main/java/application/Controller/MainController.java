package application.Controller;

import application.Configurations;
import application.Main;
import application.MemoryMonitor;
import application.Model.Logic;

public class MainController {

  private static MainController instance = new MainController();

  private MainController() {

  }

  public static MainController getInstance() {
    if (instance == null) {
      instance = new MainController();
    }
    return instance;
  }

  public void setupConfigurations() {
    Configurations.setConfigurations(Main.getArguments());
    if (Configurations.ENABLE_TRACK_MEMORY) {
      launchMemoryTracking();
    }
    Logic.init();
  }

  public void launchMemoryTracking() {
    // launches a thread that writes occupied memory to console if enabled
    Thread memoryTrack = new Thread(new MemoryMonitor());
    memoryTrack.setDaemon(true);
    memoryTrack.start();
  }

  public void exit() {
    Main.exit();
  }

}
