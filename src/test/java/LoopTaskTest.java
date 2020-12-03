import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import application.Configurations;
import application.controller.ModelController;
import application.controller.ViewController;
import application.model.Cell;
import application.model.DeathTask;
import application.model.Field;
import application.model.LifeTask;
import application.model.Logic;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoopTaskTest {

  ModelController modelController;
  ViewController viewController;
  Logic logic;
  Field field;

  @BeforeEach
  public void init() {
    new Configurations(20,20,2);
    modelController = mock(ModelController.class);
    viewController = mock(ViewController.class);
    logic = new Logic(viewController);
    field = logic.initField();
    modelController.setLogic(logic);
    modelController.setField(field);
  }

  @Test
  @DisplayName("Tasks get field for according lists")
  public void prepareExecuteListTest() {
    field.randomize();
    Field fieldSpy = spy(field);

    DeathTask deathTask = new DeathTask(logic,fieldSpy);
    LifeTask lifeTask = new LifeTask(logic,fieldSpy);

    deathTask.prepareExecuteList();
    assertFalse(deathTask.getExecuteList().isEmpty());
    verify(fieldSpy).prepareDeathList();

    lifeTask.prepareExecuteList();
    assertFalse(lifeTask.getExecuteList().isEmpty());
    verify(fieldSpy).prepareNewbornList();
  }

  @Test
  @DisplayName("Toggles cell's state")
  public void executeTest() {
    field.randomize();
    DeathTask deathTask = new DeathTask(logic,field);
    LifeTask lifeTask = new LifeTask(logic,field);
    deathTask.prepareExecuteList();
    lifeTask.prepareExecuteList();
    for (Cell cell: deathTask.getExecuteList()) {
      assertTrue(cell.isAlive());
    }
    deathTask.execute();
    for (Cell cell: deathTask.getExecuteList()) {
      assertFalse(cell.isAlive());
    }

    for (Cell cell: lifeTask.getExecuteList()) {
      assertFalse(cell.isAlive());
    }
    lifeTask.execute();
    for (Cell cell: lifeTask.getExecuteList()) {
      assertTrue(cell.isAlive());
    }
  }

  @Test
  @DisplayName("Checks if there no living cells")
  public void isColonyDeadTest() {
    DeathTask deathTask = new DeathTask(logic,field);
    LifeTask lifeTask = new LifeTask(logic,field);
    assertTrue(deathTask.isColonyDead());
    assertTrue(lifeTask.isColonyDead());
    field.randomize();
    assertFalse(deathTask.isColonyDead());
    assertFalse(lifeTask.isColonyDead());
  }

  @Test
  @DisplayName("Tasks go through all stages")
  public void runTest() {
    field.randomize();
    LifeTask spyLifeTask = spy(new LifeTask(logic,field));
    DeathTask spyDeathTask = spy(new DeathTask(logic,field));
    new Thread(spyLifeTask).start();
    new Thread(spyDeathTask).start();
    CountDownLatch waiter = new CountDownLatch(1);
    try {
      waiter.await(2, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    verify(spyLifeTask,atLeastOnce()).isColonyDead();
    verify(spyDeathTask,atLeastOnce()).isColonyDead();
    verify(spyLifeTask,atLeastOnce()).prepareExecuteList();
    verify(spyDeathTask,atLeastOnce()).prepareExecuteList();
    verify(spyLifeTask,atLeastOnce()).execute();
    verify(spyDeathTask,atLeastOnce()).execute();
  }

  @Test
  public void tasksStopWhenColonyIsDead() {
    LifeTask lifeTask = new LifeTask(logic,field);
    DeathTask deathTask = new DeathTask(logic,field);
    Thread lifeThread = new Thread(lifeTask);
    Thread deathThread = new Thread(deathTask);
    lifeThread.start();
    deathThread.start();
    assertTrue(lifeThread.isAlive());
    assertTrue(deathThread.isAlive());
    CountDownLatch waiter = new CountDownLatch(1);
    try {
      waiter.await(1, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertTrue(lifeThread.isInterrupted());
    assertTrue(deathThread.isInterrupted());
  }

  @Test
  public void tasksStopWhenTimeLimitReached() {
    new Configurations(20,20,2);
    field.randomize();
    LifeTask lifeTask = new LifeTask(logic,field);
    DeathTask deathTask = new DeathTask(logic,field);
    Thread lifeThread = new Thread(lifeTask);
    Thread deathThread = new Thread(deathTask);
    lifeThread.start();
    deathThread.start();
    assertTrue(lifeThread.isAlive());
    assertTrue(deathThread.isAlive());
    CountDownLatch waiter = new CountDownLatch(1);
    try {
      waiter.await(3, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertTrue(lifeThread.isInterrupted());
    assertTrue(deathThread.isInterrupted());
  }
}
