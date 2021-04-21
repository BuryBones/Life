package application.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import application.Configurations;
import application.controller.ModelController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CanvasTest {

  private final ModelController modelController = mock(ModelController.class);

  @Test
  @DisplayName("Paint method puts width * height cells to canvas")
  public void paneSizeTest() {
    new Configurations(4, 4);
    Canvas canvas = new Canvas(modelController);
    canvas.paint();
    assertEquals(Configurations.get().getWidth() * Configurations.get().getHeight(),
        canvas.getChildren().size());
  }
}
