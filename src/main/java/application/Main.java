package application;

import javafx.application.Platform;

public class Main {

  public static void main(String[] args) {
    Configurations configurations = new Configurations();
    configurations.setConfigurations(args);
    if (configurations.isMemoryTrackingEnabled()) {
      launchMemoryTracking();
    }
    try {
      App.launch(App.class);
    } catch (Exception e) {
      e.printStackTrace();
      exit();
    }
  }

  public static void launchMemoryTracking() {
    // launches a thread that writes occupied memory to console if enabled
    Thread memoryTrack = new Thread(new MemoryMonitor());
    memoryTrack.setDaemon(true);
    memoryTrack.start();
  }

  public static void exit() {
    Platform.exit();
  }

}