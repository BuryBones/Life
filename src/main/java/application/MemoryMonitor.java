package application;

public class MemoryMonitor implements Runnable {

  @Override
  public void run() {
    Runtime runtime = Runtime.getRuntime();
    while (true) {
      try {
        long busyMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
        System.out.println(busyMemory + " MB");
        if (busyMemory >= 50) {
          System.gc();
          System.out.println("GC called!");
        }
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
