package application.model;

import application.controller.ViewController;
import java.util.concurrent.CyclicBarrier;

public class Logic {

  private int count = 0;
  public final CyclicBarrier barrier;

  private LifeTask lifeTask;
  private DeathTask deathTask;
  private Field field;
  private final ViewController viewController;

  public Logic(ViewController viewController) {
    this.viewController = viewController;
    barrier = new CyclicBarrier(2, this::barrierAction);
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

  public void barrierAction() {
    count++;
    viewController.demandRepaint();
  }

  public int getCount() {
    return count;
  }

  public CyclicBarrier getBarrier() {
    return barrier;
  }

  public void reportTaskStop() {
    viewController.demandButtonsUnblock();
  }

  public LifeTask getLifeTask() {
    return lifeTask;
  }

  public DeathTask getDeathTask() {
    return deathTask;
  }
}
