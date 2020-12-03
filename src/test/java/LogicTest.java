import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import application.Configurations;
import application.controller.ModelController;
import application.controller.ViewController;
import application.model.Field;
import application.model.Logic;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LogicTest {

  @Test
  @DisplayName("Initializes Field")
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
  public void runSimulationInitializesTasksTest() {
    new Configurations(4,4);
    ViewController viewController = mock(ViewController.class);
    Logic logic = new Logic(viewController);
    logic.initField();
    logic.runSimulation();
    assertNotNull(logic.getLifeTask());
    assertNotNull(logic.getDeathTask());
  }

  @Test
  @DisplayName("Logic demands to block buttons when reported about task stop")
  public void reportTaskStopTest() {
    new Configurations(4,4);
    ViewController viewController = mock(ViewController.class);
    Logic logic = new Logic(viewController);
    logic.initField();
    logic.reportTaskStop();
    verify(viewController).demandButtonsUnblock();
  }

  // TODO: Test if stable (no)
  @Test
  @DisplayName("Simulation starts and stops")
  public void simulationStartAndStopTest() {
    new Configurations(20,20);
    ViewController viewController = mock(ViewController.class);
    Logic logic = new Logic(viewController);
    Field field = logic.initField();
    field.randomize();

    logic.runSimulation();
    Thread lifeThread = logic.getLifeThread();
    Thread deathThread = logic.getDeathThread();

    assertTrue(lifeThread.isAlive());
    assertTrue(deathThread.isAlive());
    logic.stopSimulation();

    CountDownLatch waiter = new CountDownLatch(1);
    try {
      waiter.await(2, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertTrue(lifeThread.isInterrupted());
    assertTrue(deathThread.isInterrupted());
  }

  // TODO: Test if stable
  @Test
  @DisplayName("Barrier action counts and demands repaint")
  public void barrierActionTest() {
    new Configurations(30,30,2);
    ViewController viewController = mock(ViewController.class);
    Logic logic = new Logic(viewController);
    Field field = logic.initField();
    field.randomize();
    int initialCounter = logic.getCount();
    logic.runSimulation();
    CountDownLatch waiter = new CountDownLatch(1);
    try {
      waiter.await(3, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    verify(viewController,times(2)).demandRepaint();
    assertNotEquals(initialCounter,logic.getCount());
  }

}
