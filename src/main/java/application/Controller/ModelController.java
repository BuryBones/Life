package application.Controller;

import application.Model.Field;
import application.Model.Logic;

public class ModelController {

  private static ModelController instance;

  public static ModelController getInstance() {
    if (instance == null) {
      instance = new ModelController();
    }
    return instance;
  }

  private ModelController() {}

  public void start() {
    Logic.runSimulation();
  }

  public void stop() {
    Logic.stopSimulation();
  }

  public void clear() {
    Field.getInstance().initCells();
  }

  public void random() {
    Field.getInstance().randomize();
  }

}
