package application.controller;

import application.model.Cell;
import application.model.Field;
import application.model.Logic;
import java.util.List;
import javafx.scene.paint.Paint;

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

  public List<Cell> getCells() {
    return Field.getInstance().getCells();
  }

  public void toggleCellByIndex(int index) {
    getCells().get(index).toggle();
  }

  public Paint getCellColorProperty(int index) {
    return getCells().get(index).colorProperty().get();
  }

}
