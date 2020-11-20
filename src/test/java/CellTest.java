import static org.junit.jupiter.api.Assertions.*;

import application.Cell;
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

}
