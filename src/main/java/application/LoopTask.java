package application;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import javafx.application.Platform;

public abstract class LoopTask implements Runnable {

  boolean notStopped = true;
  boolean notFinished = true;
  Field field = Field.getInstance();
  List<Cell> toExecute;

  @Override
  public void run() {
    try {
      while (notStopped && notFinished) {
        notFinished = !Configurations.timeLimit || Logic.count < Configurations.steps - 1;
        prepareExecuteList();
        Logic.BARRIER.await();
        execute();
        Thread.sleep(Configurations.PERIOD);
      }
        Thread.currentThread().interrupt();
    } catch (InterruptedException | BrokenBarrierException e) {
      // TODO: exception handling
      e.printStackTrace();
    }
    if (!notFinished) {
      Platform.runLater(() -> {
        Controller.getInstance().unblockButtons();
        Controller.getInstance().blockStop();
      });
    }
  }
  abstract void prepareExecuteList();

  void execute() {
    toExecute.forEach(Cell::toggle);
  }

  void stop() {
    notStopped = false;
  }

}
