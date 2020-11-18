import java.util.List;
import java.util.concurrent.BrokenBarrierException;

public class DeathTask implements Runnable {

  private boolean notStopped = true;
  private Field field;
  private List<Cell> toKill;

  public DeathTask(Field field) {
    this.field = field;
  }

  @Override
  public void run() {
    try {
      // TODO: refactor Counter task?
      while (notStopped && (!Configurations.timeLimit || CounterTask.counter < Configurations.steps)) {
        toKill = field.prepareDeathList();
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
    toKill.forEach(Cell::kill);
  }

  public void stop() {
    notStopped = false;
  }
}
