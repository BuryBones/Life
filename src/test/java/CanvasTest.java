import static org.junit.jupiter.api.Assertions.assertEquals;

import application.Configurations;
import application.model.Field;
import application.model.Logic;
import application.view.Canvas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CanvasTest {

  static Field field;

  @BeforeEach
  public void init() {
    new Configurations();
    Logic logic = new Logic();
    field = logic.initField();
  }

  @Test
  @DisplayName("Paint method puts width * height cells to canvas")
  public void paneSizeTest() {
    Canvas canvas = new Canvas();
    canvas.paint();
    assertEquals(Configurations.get().getWidth() * Configurations.get().getHeight(),
        canvas.getChildren().size());
  }

}
