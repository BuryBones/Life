package application;

import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.scene.paint.Paint;

public class Cell {

  private final int x;
  private final int y;
  private ArrayList<Cell> neighbours;

  private final SimpleBooleanProperty isAliveProperty = new SimpleBooleanProperty(false);
  private final ObservableObjectValue<Paint> color;

  public Cell(int x, int y) {
    this.x = x;
    this.y = y;
    color = Bindings.when(isAliveProperty).then(Configurations.ALIVE).otherwise(Configurations.DEAD);
  }

  void kill() {
    isAliveProperty.set(false);
  }

  void revive() {
    isAliveProperty.set(true);
  }

  void toggle() {
    isAliveProperty.set(!isAliveProperty.get());
  }

  public ArrayList<Cell> getNeighbours() {
    return neighbours;
  }

  public void setNeighbours(ArrayList<Cell> neighbours) {
    this.neighbours = neighbours;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public boolean isAlive() {
    return isAliveProperty.get();
  }

  public ObservableObjectValue<Paint> colorProperty() {
    return color;
  }
}
