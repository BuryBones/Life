import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import application.model.Cell;
import application.model.Field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FieldTest {

  @Test
  @DisplayName("Initialized cells are not nulls and not alive")
  public void initCellsTest() {
    Field.getInstance().initCells();
    for (Cell cell : Field.getInstance().getCells()) {
      assertNotNull(cell);
      assertFalse(cell.isAlive());
    }
  }
}
