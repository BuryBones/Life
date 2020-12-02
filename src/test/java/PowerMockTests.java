import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import application.Configurations;
import application.controller.ViewController;
import application.model.Logic;
import application.model.LoopTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ViewController.class})
public class PowerMockTests {

  private final ViewController vc = PowerMockito.mock(ViewController.class);

  @Test
  @DisplayName("Stops tasks")
  public void stopSimulationStopsTasksTest() {
    new Configurations(4,4);
    Logic logic = new Logic();
    logic.initField();
    logic.runSimulation();

    PowerMockito.doNothing().when(vc).demandRepaint();
    PowerMockito.doNothing().when(vc).demandRepaint();
    PowerMockito.doNothing().when(vc).demandButtonsBlock();
    PowerMockito.doNothing().when(vc).demandButtonsBlock();

    LoopTask lifeSpy = spy(logic.getLifeTask());
    LoopTask deathSpy = spy(logic.getDeathTask());
    logic.stopSimulation();
    verify(deathSpy).stop();
    verify(lifeSpy).stop();
  }
}
