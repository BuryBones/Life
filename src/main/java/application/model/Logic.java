package application.model;

import application.controller.ViewController;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadFactory;

public class Logic {

  private int count = 0;
  private Phaser barrier;
  private ExecutorService executor;
  private final ThreadFactory threadFactory;
  private final Field field;
  private final ViewController viewController;

  @Inject
  public Logic(Field field, ViewController viewController,
      @Named("LoopFactory") ThreadFactory threadFactory) {
    this.field = field;
    this.viewController = viewController;
    this.threadFactory = threadFactory;
  }

  public void runSimulation() {
    count = 0;
    populateFieldIfEmpty();

    LifeTask lifeTask = new LifeTask(this, field);
    DeathTask deathTask = new DeathTask(this, field);

    barrier = new LoopPhaser(this);
    executor = Executors.newFixedThreadPool(2, threadFactory);
    executor.submit(lifeTask);
    executor.submit(deathTask);
  }

  public void stopSimulation() {
    if (!executor.isShutdown()) {
      executor.shutdownNow();
    }
  }

  public void barrierAction() {
    count++;
    viewController.demandRepaint();
  }

  public int getCount() {
    return count;
  }

  public Phaser getBarrier() {
    return barrier;
  }

  public void reportTaskStop() {
    viewController.demandButtonsUnblock();
  }

  private void populateFieldIfEmpty() {
    if (field.numberOfCellsAlive() == 0) {
      field.randomize();
    }
  }
}
