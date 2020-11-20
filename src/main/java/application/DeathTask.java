package application;

public class DeathTask extends LoopTask {

  public void prepareExecuteList() {
    toExecute = field.prepareDeathList();
  }
}
