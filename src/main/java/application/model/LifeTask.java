package application.model;

public class LifeTask extends LoopTask {

  public LifeTask(Logic logic, Field field) {
    super(logic, field);
  }

  public void prepareExecuteList() {
    toExecute = field.prepareNewbornList();
  }
}
