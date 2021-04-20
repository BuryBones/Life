package application.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import application.BasicModule;
import application.Configurations;
import application.controller.ModelController;
import application.controller.ViewController;
import application.model.Field;
import application.model.Logic;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CanvasTest {

  private final Injector injector = Guice.createInjector(new BasicModule());

  Field field = injector.getInstance(Field.class);
  ModelController modelController = injector.getInstance(ModelController.class);
  ViewController viewController = injector.getInstance(ViewController.class);
  Logic logic = injector.getInstance(Logic.class);

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
