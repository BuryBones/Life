package application.model;

import application.Configurations;
import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.scene.paint.Paint;

public class Cell {

  private ArrayList<Cell> neighbours;

  private final SimpleBooleanProperty isAliveProperty = new SimpleBooleanProperty();
  private final ObservableObjectValue<Paint> color;

  public Cell() {
    color = Bindings.when(isAliveProperty)
        .then(Configurations.getCurrentConfigs().getAlive())
        .otherwise(Configurations.getCurrentConfigs().getDead());
  }

  public void kill() {
    isAliveProperty.set(false);
  }

  public void revive() {
    isAliveProperty.set(true);
  }

  public void toggle() {
    isAliveProperty.set(!isAliveProperty.get());
  }

  public ArrayList<Cell> getNeighbours() {
    return neighbours;
  }

  public void setNeighbours(ArrayList<Cell> neighbours) {
    this.neighbours = neighbours;
  }

  public boolean isAlive() {
    return isAliveProperty.get();
  }

  public ObservableObjectValue<Paint> colorProperty() {
    return color;
  }
}
