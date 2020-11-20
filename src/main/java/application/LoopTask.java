package application;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;

public abstract class LoopTask implements Runnable {

  boolean notStopped = true;
  Field field = Field.getInstance();
  List<Cell> toExecute;

  @Override
  public void run() {
    try {
      while (notStopped && (!Configurations.timeLimit || Logic.count < Configurations.steps)) {
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
  }
  abstract void prepareExecuteList();

  void execute() {
    toExecute.forEach(Cell::toggle);
  }

  void stop() {
    notStopped = false;
  }

}
