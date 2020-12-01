package application.model;

public class DeathTask extends LoopTask {

  public DeathTask(Logic logic) {
    super(logic);
  }

  public void prepareExecuteList() {
    toExecute = field.prepareDeathList();
  }
}
