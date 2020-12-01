import static org.junit.jupiter.api.Assertions.assertEquals;

import application.model.Logic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LogicTest {

  @Test
  @DisplayName("Resets count to zero")
  public void runSimulationTimerResetTest() {
    Logic logic = new Logic();
    logic.initField();
    logic.runSimulation();
    assertEquals(logic.getCount(), 0);
  }

}
