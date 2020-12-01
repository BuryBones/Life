import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import application.model.Cell;
import application.model.Field;
import application.model.Logic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FieldTest {

  @Test
  @DisplayName("Initialized cells are not nulls and not alive")
  public void initCellsTest() {
    Logic logic = new Logic();
    Field field = logic.initField();
    field.initCells();
    for (Cell cell : field.getCells()) {
      assertNotNull(cell);
      assertFalse(cell.isAlive());
    }
  }
}
