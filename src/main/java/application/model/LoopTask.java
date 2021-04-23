package application.model;

import application.Configurations;
import application.controller.AlertsController;
import java.util.List;

public abstract class LoopTask implements Runnable {

  protected final Logic logic;
  public final Field field;
  protected List<Cell> toExecute;

  public abstract void prepareExecuteList();

  public LoopTask(Logic logic, Field field) {
    this.logic = logic;
    this.field = field;
  }

  @Override
  public void run() {
    logic.getBarrier().register();
    try {
      while (!Thread.interrupted() && timeLimitNotReached() && !isColonyDead()) {
        prepareExecuteList();
        logic.getBarrier().arriveAndAwaitAdvance();
        execute();
        Thread.sleep(Configurations.get().getPeriod());
      }
    } catch (InterruptedException e) {
      AlertsController.getInstance().getErrorAlert(e.getMessage()).pop();
    }
    logic.getBarrier().arriveAndDeregister();
    if (!Thread.interrupted()) {
      logic.reportTaskStop();
      Thread.currentThread().interrupt();
    }
  }

  public boolean timeLimitNotReached() {
    return !Configurations.get().isTimeLimit() ||               // if time limit is true
        logic.getCount() < Configurations.get().getSteps();     // check counter < limit
  }

  public boolean isColonyDead() {
    return field.numberOfCellsAlive() == 0;
  }

  public void execute() {
    toExecute.forEach(Cell::toggle);
  }

  public List<Cell> getExecuteList() {
    return toExecute;
  }
}
