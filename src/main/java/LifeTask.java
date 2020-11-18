import java.util.List;
import java.util.concurrent.BrokenBarrierException;

public class LifeTask implements Runnable {

  private boolean notStopped = true;
  private Field field;
  private List<Cell> toRevive;

  public LifeTask(Field field) {
    this.field = field;
  }

  @Override
  public void run() {
    try {
      // TODO: refactor Counter task?
      while (notStopped && (!Configurations.timeLimit || CounterTask.counter < Configurations.steps)) {
        toRevive = field.prepareNewbornList();
        Main.barrier.await();
        execute();
        Thread.sleep(Configurations.PERIOD);
      }
      Thread.currentThread().interrupt();
    } catch (InterruptedException | BrokenBarrierException e) {
      e.printStackTrace();
    }
  }
  private void execute() {
    toRevive.forEach(Cell::revive);
  }

  public void stop() {
    notStopped = false;
  }
}
