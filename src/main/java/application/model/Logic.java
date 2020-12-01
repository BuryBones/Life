package application.model;

import application.controller.ViewController;
import java.util.concurrent.CyclicBarrier;

public class Logic {

  private int count = 0;
  public final CyclicBarrier barrier;

  private static LoopTask lifeTask;
  private static LoopTask deathTask;

  public Logic() {
    barrier = new CyclicBarrier(2,() -> {
      count++;
      ViewController.getInstance().demandRepaint();
    });
  }

  public void init() {
    Field.getInstance().initCells();
  }

  public void runSimulation() {
    count = 0;
    
    if (Field.getInstance().numberOfCellsAlive() == 0) {
      Field.getInstance().randomize();
    }

    lifeTask = new LifeTask(this);
    deathTask = new DeathTask(this);
    Thread life = new Thread(lifeTask);
    Thread death = new Thread(deathTask);
    life.setDaemon(true);
    death.setDaemon(true);
    life.start();
    death.start();
  }

  public void stopSimulation() {
    lifeTask.stop();
    deathTask.stop();
  }

  public int getCount() {
    return count;
  }

  public CyclicBarrier getBarrier() {
    return barrier;
  }
}
