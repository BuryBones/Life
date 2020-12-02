import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import application.Configurations;
import application.controller.ModelController;
import application.controller.ViewController;
import application.model.Field;
import application.model.Logic;
import application.model.LoopTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LogicTest {

  ModelController modelController;
  ViewController viewController;
  Logic logic;
  Field field;

  @BeforeEach
  public void init() {
    new Configurations(4,4);
    modelController = new ModelController();
    viewController = new ViewController(modelController);
    logic = new Logic(viewController);
    field = logic.initField();
    modelController.setLogic(logic);
    modelController.setField(field);
  }

  @Test
  @DisplayName("initField() initializes Field")
  public void initFieldTest() {
    assertNotNull(field);
  }

  // TODO: throws an exception!
  @Test
  @DisplayName("Initializes tasks")
  public void runSimulationRunsTasksTest() {
    logic.runSimulation();
    assertNotNull(logic.getLifeTask());
    assertNotNull(logic.getDeathTask());
  }

  @Test
  @DisplayName("Stops tasks")
  public void stopSimulationStopsTasksTest() {
    logic.runSimulation();
    LoopTask lifeSpy = spy(logic.getLifeTask());
    LoopTask deathSpy = spy(logic.getDeathTask());
    logic.stopSimulation();
    verify(deathSpy).stop();
    verify(lifeSpy).stop();
  }

}
