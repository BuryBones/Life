package application.model;

public class LifeTask extends LoopTask {

  public LifeTask(Logic logic) {
    super(logic);
  }

  public void prepareExecuteList() {
    toExecute = field.prepareNewbornList();
  }
}
