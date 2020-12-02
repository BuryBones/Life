import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import application.Configurations;
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
    Logic logic = new Logic();
    Field field = logic.initField();
    assertNotNull(field);
  }

  // TODO: throws an exception!
  @Test
  @DisplayName("Initializes tasks")
  public void runSimulationRunsTasksTest() {
    new Configurations(4,4);
    Logic logic = new Logic();
    logic.initField();
    logic.runSimulation();
    assertNotNull(logic.getLifeTask());
    assertNotNull(logic.getDeathTask());
  }

  @Test
  @DisplayName("Stops tasks")
  public void stopSimulationStopsTasksTest() {
    new Configurations(4,4);
    Logic logic = new Logic();
    logic.initField();
    logic.runSimulation();
    LoopTask lifeSpy = spy(logic.getLifeTask());
    LoopTask deathSpy = spy(logic.getDeathTask());
    logic.stopSimulation();
    verify(deathSpy).stop();
    verify(lifeSpy).stop();
  }

}
