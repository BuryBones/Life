package application.model;

public class DeathTask extends LoopTask {

  public DeathTask(Logic logic, Field field) {
    super(logic, field);
  }

  public void prepareExecuteList() {
    toExecute = field.prepareDeathList();
  }
}
