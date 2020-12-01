import static org.junit.jupiter.api.Assertions.assertEquals;

import application.Configurations;
import application.model.Logic;
import application.view.Canvas;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CanvasTest {

  @Test
  @DisplayName("Paint method puts width * height cells to canvas")
  public void paneSizeTest() {
    Logic logic = new Logic();
    logic.initField().initCells();
    Canvas canvas = new Canvas();
    canvas.paint();
    assertEquals(Configurations.getCurrentConfigs().getWidth() * Configurations.getCurrentConfigs().getHeight(),
        canvas.getChildren().size());
  }

}
