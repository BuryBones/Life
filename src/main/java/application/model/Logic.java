package application.model;

import application.controller.ViewController;
import java.util.concurrent.CyclicBarrier;

public class Logic {

  private int count = 0;
  public final CyclicBarrier barrier;

  private LoopTask lifeTask;
  private LoopTask deathTask;
  private Field field;

  public Logic() {
    barrier = new CyclicBarrier(2,() -> {
      count++;
      ViewController.getInstance().demandRepaint();
    });
  }

  public Field initField() {
    field = new Field();
    return field;
  }

  public void runSimulation() {
    count = 0;
    
    if (field.numberOfCellsAlive() == 0) {
      field.randomize();
    }

    lifeTask = new LifeTask(this, field);
    deathTask = new DeathTask(this, field);
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

  public LoopTask getLifeTask() {
    return lifeTask;
  }

  public LoopTask getDeathTask() {
    return deathTask;
  }
}
