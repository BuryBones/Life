import static org.junit.jupiter.api.Assertions.assertEquals;

import application.Configurations;
import application.controller.ModelController;
import application.controller.ViewController;
import application.model.Field;
import application.model.Logic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LogicTest {

  @Test
  @DisplayName("Resets count to zero")
  public void runSimulationTimerResetTest() {
    new Configurations();
    ModelController modelController = new ModelController();
    ViewController viewController = new ViewController(modelController);
    Logic logic = new Logic(viewController);
    Field field = logic.initField();
    modelController.setLogic(logic);
    modelController.setField(field);
    logic.runSimulation();
    assertEquals(logic.getCount(), 0);
  }

}
