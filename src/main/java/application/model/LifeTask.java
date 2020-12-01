package application.model;

public class LifeTask extends LoopTask {

  public void prepareExecuteList() {
    toExecute = field.prepareNewbornList();
  }
}
