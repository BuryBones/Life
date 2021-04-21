package application.controller;

import application.model.Cell;
import application.model.Field;
import application.model.Logic;
import com.google.inject.Inject;
import java.util.List;
import javafx.scene.paint.Paint;

public class ModelController {

  private final Logic logic;
  private final Field field;

  @Inject
  public ModelController(Logic logic, Field field) {
    this.logic = logic;
    this.field = field;
  }

  public void start() {
    logic.runSimulation();
  }

  public void stop() {
    logic.stopSimulation();
  }

  public void clear() {
    field.initCells();
  }

  public void random() {
    field.randomize();
  }

  public List<Cell> getCells() {
    return field.getCells();
  }

  public void toggleCellByIndex(int index) {
    getCells().get(index).toggle();
  }

  public Paint getCellColorProperty(int index) {
    return getCells().get(index).colorProperty().get();
  }

}
