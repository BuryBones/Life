import static org.junit.jupiter.api.Assertions.assertEquals;

import application.Configurations;
import application.controller.ModelController;
import application.controller.ViewController;
import application.model.Field;
import application.model.Logic;
import application.view.Canvas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CanvasTest {

  Field field;
  ModelController modelController;

  @BeforeEach
  public void init() {
    new Configurations();
    modelController = new ModelController();
    ViewController viewController = new ViewController(modelController);
    Logic logic = new Logic(viewController);
    field = logic.initField();
    modelController.setLogic(logic);
    modelController.setField(field);
  }

  @Test
  @DisplayName("Paint method puts width * height cells to canvas")
  public void paneSizeTest() {
    Canvas canvas = new Canvas(modelController);
    canvas.paint();
    assertEquals(Configurations.get().getWidth() * Configurations.get().getHeight(),
        canvas.getChildren().size());
  }

}
