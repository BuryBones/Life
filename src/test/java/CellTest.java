import static org.junit.jupiter.api.Assertions.*;

import application.Cell;
import application.Configurations;
import application.Field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CellTest {

  @Test
  @DisplayName("No null neighbours")
  public void getNeighboursNoNullCellsTest() {
    Field.getInstance().initCells();
    for (Cell cell: Field.getInstance().getCells()) {
      for (Cell neighbour: cell.getNeighbours()) {
        assertNotNull(neighbour);
      }
    }
  }

  @Test
  @DisplayName("Constructor test")
  public void constructorTest() {
    Cell cell = new Cell();
    assertNotNull(cell);
    assertFalse(cell.isAlive());
    assertNull(cell.getNeighbours());
    assertNotNull(cell.colorProperty());
    assertSame(cell.colorProperty().get(), Configurations.DEAD);
  }

  @Test
  @DisplayName("Toggle changes 'isAlive'")
  public void toggleTest() {
    Cell cell = new Cell();
    cell.toggle();
    assertTrue(cell.isAlive());
    cell.toggle();
    assertFalse(cell.isAlive());
  }

  @Test
  @DisplayName("Revive sets 'isAlive' to true")
  public void reviveTest() {
    Cell cell = new Cell();
    cell.revive();
    assertTrue(cell.isAlive());
  }

  @Test
  @DisplayName("Kill sets 'isAlive' to false")
  public void killTest() {
    Cell cell = new Cell();
    cell.revive();
    cell.kill();
    assertFalse(cell.isAlive());
  }

  @Test
  @DisplayName("Color reflects 'isAlive' value")
  public void colorTest() {
    Cell cell = new Cell();
    assertSame(cell.colorProperty().get(), Configurations.DEAD);
    cell.revive();
    assertSame(cell.colorProperty().get(), Configurations.ALIVE);
    cell.kill();
    assertSame(cell.colorProperty().get(), Configurations.DEAD);
  }
}
