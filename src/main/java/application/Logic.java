package application;

import java.util.concurrent.CyclicBarrier;

public class Logic {

  public static int count = 0;
  public static final CyclicBarrier BARRIER = new CyclicBarrier(2,() -> {
    count++;
    Graphics.getInstance().triggerPaint();
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
}
