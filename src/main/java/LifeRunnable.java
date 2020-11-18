import java.util.List;

// TODO: rename
public class LifeRunnable implements Runnable {

  private Field field;
  private List<Cell> toRevive;

  public LifeRunnable(Field field) {
    this.field = field;
  }

  @Override
  public void run() {
    try {
      // TODO: make counter available and increments correctly
//      while (!Configurations.timeLimit || counter < Configurations.steps) {
      while (!Configurations.timeLimit) {
        toRevive = field.prepareNewbornList();
        // TODO: Wait somehow for 'toKill' is ready????
        execute();
        Thread.sleep(Configurations.PERIOD);
      }
    } catch (InterruptedException e){
      e.printStackTrace();
    }
  }
  private void execute() {
    toRevive.forEach(Cell::revive);
  }
  public void stop() {
    Thread.currentThread().interrupt();
  }
}
