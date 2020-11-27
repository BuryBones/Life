package application;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import javafx.application.Platform;

public abstract class LoopTask implements Runnable {

  boolean notStopped = true;  // 'stop' button not pressed
  boolean notFinished = true; // colony has not reached time limit
  Field field = Field.getInstance();
  List<Cell> toExecute;

  abstract void prepareExecuteList();

  @Override
  public void run() {
    try {
      while (notStopped && notFinished && !isColonyDead()) {
        notFinished = !Configurations.timeLimit || Logic.count < Configurations.steps - 1;
        prepareExecuteList();
        Logic.BARRIER.await();
        execute();
        Thread.sleep(Configurations.PERIOD);
      }
        Thread.currentThread().interrupt();
    } catch (InterruptedException | BrokenBarrierException e) {
      Platform.runLater(() -> Graphics.getInstance().showErrorMessageAndExit(e.getMessage()));
    }
    // if colony reached time limit or there is no alive cells
    if (!notFinished || isColonyDead()) {
      ViewController.getInstance().demandButtonsBlock();
      }
  }

  void execute() {
    toExecute.forEach(Cell::toggle);
  }

  void stop() {
    notStopped = false;
  }

  boolean isColonyDead() {
    return Field.getInstance().numberOfCellsAlive() == 0;
  }
}
