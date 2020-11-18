import java.util.List;

public class DeathRunnable implements Runnable {

  private Field field;
  private List<Cell> toKill;

  public DeathRunnable(Field field) {
    this.field = field;
  }

  @Override
  public void run() {
    try {
      // TODO: make counter available and increments correctly
//      while (!Configurations.timeLimit || counter < Configurations.steps) {
      while (!Configurations.timeLimit) {
        toKill = field.prepareDeathList();
        // TODO: Wait somehow for 'toRevive' is ready????
        execute();
        Thread.sleep(Configurations.PERIOD);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  private void execute() {
    toKill.forEach(Cell::kill);
  }
  public void stop() {
    Thread.currentThread().interrupt();
  }
}
