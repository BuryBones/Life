package application.model;

import application.controller.ViewController;
import com.google.inject.Inject;
import java.util.concurrent.Phaser;

public class Logic {

  private int count = 0;
  private final Phaser barrier = new Phaser() {
    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
      barrierAction();
      return false;
    }
  };

  private LifeTask lifeTask;
  private DeathTask deathTask;
  private Thread lifeThread;
  private Thread deathThread;
  private Field field;
  private ViewController viewController;

  @Inject
  public void setField(Field field) {
    this.field = field;
  }

  @Inject
  public void setViewController(ViewController viewController) {
    this.viewController = viewController;
  }

  public void runSimulation() {
    count = 0;

    if (field.numberOfCellsAlive() == 0) {
      field.randomize();
    }

    lifeTask = new LifeTask(this, field);
    deathTask = new DeathTask(this, field);
    lifeThread = new Thread(lifeTask);
    deathThread = new Thread(deathTask);
    lifeThread.setDaemon(true);
    deathThread.setDaemon(true);
    lifeThread.start();
    deathThread.start();
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

  public Phaser getBarrier() {
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

  public Thread getLifeThread() {
    return lifeThread;
  }

  public Thread getDeathThread() {
    return deathThread;
  }
}
