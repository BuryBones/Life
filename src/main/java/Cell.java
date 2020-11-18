import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableObjectValue;
import javafx.scene.paint.Paint;
import jdk.jshell.spi.ExecutionControl.NotImplementedException;

public class Cell {

  private final int x;
  private final int y;
  private final ArrayList<Cell> neighbours;

  static final Paint green = Configurations.ALIVE;
  static final Paint gray = Configurations.DEAD;

  ObservableBooleanValue isAlive = new SimpleBooleanProperty(false);
  SimpleBooleanProperty isAliveProp = (SimpleBooleanProperty) isAlive;

  public void setIsAliveProp(boolean isAliveProp) {
    this.isAliveProp.set(isAliveProp);
  }

  public boolean isIsAliveProp() {
    return isAliveProp.get();
  }

  public SimpleBooleanProperty isAlivePropProperty() {
    return isAliveProp;
  }

  ObservableObjectValue<Paint> color;

  void kill() {
    isAliveProp.set(false);
  }
  void revive() {
    isAliveProp.set(true);
  }
  void toggle() {
    isAliveProp.set(!isAliveProp.get());
    System.out.println(isIsAliveProp());
  }

  public Cell(int x, int y, ArrayList<Cell> neighbours) {
    this.x = x;
    this.y = y;
    this.neighbours = neighbours;
    // TODO: put property
    color = Bindings.when(isAlive).then(green).otherwise(gray);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public ArrayList<Cell> getNeighbours() throws NotImplementedException {
    throw new NotImplementedException("Not yet implemented!");
  }
  public String toString() {
    return isAlive.get() ? " + " : "   ";
  }
}
