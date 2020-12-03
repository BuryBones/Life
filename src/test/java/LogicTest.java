import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import application.Configurations;
import application.controller.ModelController;
import application.controller.ViewController;
import application.model.Field;
import application.model.Logic;
import application.model.LoopTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LogicTest {

  @Test
  @DisplayName("initField() initializes Field")
  public void initFieldTest() {
    new Configurations(4,4);
    ModelController modelController = new ModelController();
    ViewController viewController = new ViewController(modelController);
    Logic logic = new Logic(viewController);
    Field field = logic.initField();

    assertNotNull(field);
  }

  @Test
  @DisplayName("Initializes tasks")
  public void runSimulationRunsTasksTest() {
    new Configurations(4,4);
    ModelController modelController = mock(ModelController.class);
    ViewController viewController = mock(ViewController.class);
    Logic logic = new Logic(viewController);
    Field field = logic.initField();
    logic.runSimulation();
    assertNotNull(logic.getLifeTask());
    assertNotNull(logic.getDeathTask());
  }

  @Test
  @DisplayName("Logic demands to block buttons when reported about task stop")
  public void reportTaskStopTest() {
    new Configurations(4,4);
    ModelController modelController = mock(ModelController.class);
    ViewController viewController = mock(ViewController.class);
    Logic logic = new Logic(viewController);
    Field field = logic.initField();
    logic.reportTaskStop();
    verify(viewController).demandButtonsUnblock();
  }

  // DOESN'T PASS
  @Test
  @DisplayName("Stops tasks")
  public void stopSimulationStopsTasksTest() {
    new Configurations(4,4);
    ModelController modelController = mock(ModelController.class);
    ViewController viewController = mock(ViewController.class);
    Logic logic = new Logic(viewController);
    Field field = logic.initField();
    logic.runSimulation();
    LoopTask lifeSpy = spy(logic.getLifeTask());
    LoopTask deathSpy = spy(logic.getDeathTask());
    logic.stopSimulation();
    verify(lifeSpy).stop();
    verify(deathSpy).stop();
  }

  // DOESN'T PASS
  @Test
  @DisplayName("Threads use barrierAction()")
  public void barrierActionTest() {
    new Configurations(4,4,3);
    ModelController modelController = mock(ModelController.class);
    ViewController viewController = mock(ViewController.class);
    Logic logicSpy = spy(new Logic(viewController));
    Field field = logicSpy.initField();
    field.randomize();
    logicSpy.runSimulation();
    verify(logicSpy,atLeastOnce()).barrierAction();
  }

}
