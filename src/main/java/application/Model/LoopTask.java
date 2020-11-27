package application.Model;

import application.Configurations;
import application.Controller.ViewController;
import application.View.Alerts.ErrorAlert;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import javafx.application.Platform;

public abstract class LoopTask implements Runnable {

  private boolean notStopped = true;  // 'stop' button not pressed
  private boolean notFinished = true; // colony has not reached time limit
  protected Field field = Field.getInstance();
  protected List<Cell> toExecute;

  abstract void prepareExecuteList();

  @Override
  public void run() {
    try {
      while (notStopped && notFinished && !isColonyDead()) {
        notFinished = !Configurations.timeLimit || Logic.getCount() < Configurations.steps - 1;
        prepareExecuteList();
        Logic.BARRIER.await();
        execute();
        Thread.sleep(Configurations.PERIOD);
      }
        Thread.currentThread().interrupt();
    } catch (InterruptedException | BrokenBarrierException e) {
      Platform.runLater(() -> ErrorAlert.showErrorMessageAndExit(e.getMessage()));
    }
    // if colony reached time limit or there is no alive cells
    if (!notFinished || isColonyDead()) {
      ViewController.getInstance().demandButtonsBlock();
      }
  }

  public void execute() {
    toExecute.forEach(Cell::toggle);
  }

  public void stop() {
    notStopped = false;
  }

  public boolean isColonyDead() {
    return Field.getInstance().numberOfCellsAlive() == 0;
  }
}
