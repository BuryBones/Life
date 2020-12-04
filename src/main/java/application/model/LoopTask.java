package application.model;

import application.Configurations;
import application.controller.AlertsController;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;

public abstract class LoopTask implements Runnable {

  private boolean notStopped = true;  // 'stop' button not pressed
  private boolean notFinished = true; // colony has not reached time limit
  private final Logic logic;
  public final Field field;
  protected List<Cell> toExecute;

  public abstract void prepareExecuteList();

  public LoopTask(Logic logic, Field field) {
    this.logic = logic;
    this.field = field;
  }

  @Override
  public void run() {
    try {
      while (notStopped && notFinished && !isColonyDead()) {
        notFinished = !Configurations.get().isTimeLimit()
            || logic.getCount() < Configurations.get().getSteps() - 1;
        prepareExecuteList();
        logic.getBarrier().await();
        execute();
        Thread.sleep(Configurations.get().getPeriod());
      }
      Thread.currentThread().interrupt();
    } catch (InterruptedException | BrokenBarrierException e) {
      AlertsController.getInstance().getErrorAlert(e.getMessage()).pop();
    }
    // if colony reached time limit or there is no alive cells
    if (!notFinished || isColonyDead()) {
      logic.reportTaskStop();
    }
  }

  public void execute() {
    toExecute.forEach(Cell::toggle);
  }

  public void stop() {
    notStopped = false;
  }

  public boolean isColonyDead() {
    return field.numberOfCellsAlive() == 0;
  }

  public List<Cell> getExecuteList() {
    return toExecute;
  }
}
