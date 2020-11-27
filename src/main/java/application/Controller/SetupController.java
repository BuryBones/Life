package application.Controller;

import application.Configurations;
import application.Main;
import application.MemoryMonitor;
import application.Model.Logic;

public class SetupController {

  private static SetupController instance = new SetupController();

  private SetupController() {

  }

  public static SetupController getInstance() {
    if (instance == null) {
      instance = new SetupController();
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

}
