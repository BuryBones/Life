package application.Model;

import application.Controller.ViewController;
import java.util.concurrent.CyclicBarrier;

public class Logic {

  private static int count = 0;
  public static final CyclicBarrier BARRIER = new CyclicBarrier(2,() -> {
    count++;
    ViewController.getInstance().demandRepaint();
  });

  private static LoopTask lifeTask;
  private static LoopTask deathTask;

  public static void init() {
    Field.getInstance().initCells();
  }

  public static void runSimulation() {
    count = 0;
    
    if (Field.getInstance().numberOfCellsAlive() == 0) {
      Field.getInstance().randomize();
    }

    lifeTask = new LifeTask();
    deathTask = new DeathTask();
    Thread life = new Thread(lifeTask);
    Thread death = new Thread(deathTask);
    life.setDaemon(true);
    death.setDaemon(true);
    life.start();
    death.start();
  }

  public static void stopSimulation() {
    lifeTask.stop();
    deathTask.stop();
  }

  public static int getCount() {
    return count;
  }
}
