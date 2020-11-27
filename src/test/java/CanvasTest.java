import static org.junit.jupiter.api.Assertions.assertEquals;

import application.Canvas;
import application.Configurations;
import application.Field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CanvasTest {

  @Test
  @DisplayName("Paint method puts width * height cells to canvas")
  public void paneSizeTest() {
    Field.getInstance().initCells();
    Canvas canvas = new Canvas();
    canvas.paint();
    assertEquals(Configurations.width * Configurations.height,
        canvas.getChildren().size());
  }

}
