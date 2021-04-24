package application.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import application.BasicModule;
import application.Configurations;
import application.controller.ModelController;
import application.controller.ViewController;
import application.view.ControlBar;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoopTaskTest {

  private Logic logic;
  private Field field;

  @BeforeEach
  public void setup() {
    new Configurations(20, 20, 2);

    Module testModule = Modules.override(new BasicModule()).with(new AbstractModule() {
      @Override
      protected void configure() {
        bind(ViewController.class).toInstance(mock(ViewController.class));
        bind(ModelController.class).toInstance(mock(ModelController.class));
        bind(ControlBar.class).toInstance(mock(ControlBar.class));
        bind(Logic.class).toInstance(mock(Logic.class));
      }
    });

    Injector mockInjector = Guice.createInjector(testModule);

    field = mockInjector.getInstance(Field.class);
    logic = mockInjector.getInstance(Logic.class);
    when(logic.getBarrier()).thenReturn(new LoopPhaser(logic));
    doNothing().when(logic).reportTaskStop();
  }

  @Test
  @DisplayName("Tasks get field for according lists")
  public void prepareExecuteListTest() {
    field.randomize();
    Field fieldSpy = spy(field);

    DeathTask deathTask = new DeathTask(logic, fieldSpy);
    LifeTask lifeTask = new LifeTask(logic, fieldSpy);

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
    DeathTask deathTask = new DeathTask(logic, field);
    LifeTask lifeTask = new LifeTask(logic, field);
    deathTask.prepareExecuteList();
    lifeTask.prepareExecuteList();

    for (Cell cell : deathTask.getExecuteList()) {
      assertTrue(cell.isAlive());
    }
    deathTask.execute();
    for (Cell cell : deathTask.getExecuteList()) {
      assertFalse(cell.isAlive());
    }

    for (Cell cell : lifeTask.getExecuteList()) {
      assertFalse(cell.isAlive());
    }
    lifeTask.execute();
    for (Cell cell : lifeTask.getExecuteList()) {
      assertTrue(cell.isAlive());
    }
  }

  @Test
  @DisplayName("Checks if there no living cells")
  public void isColonyDeadTest() {
    DeathTask deathTask = new DeathTask(logic, field);
    LifeTask lifeTask = new LifeTask(logic, field);

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
    LifeTask spyLifeTask = spy(new LifeTask(logic, field));
    DeathTask spyDeathTask = spy(new DeathTask(logic, field));

    new Thread(spyLifeTask).start();
    new Thread(spyDeathTask).start();

    CountDownLatch waiter = new CountDownLatch(1);
    try {
      waiter.await(100, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    verify(spyLifeTask, atLeastOnce()).isColonyDead();
    verify(spyDeathTask, atLeastOnce()).isColonyDead();
    verify(spyLifeTask, atLeastOnce()).prepareExecuteList();
    verify(spyDeathTask, atLeastOnce()).prepareExecuteList();
    verify(spyLifeTask, atLeastOnce()).execute();
    verify(spyDeathTask, atLeastOnce()).execute();
  }

  @Test
  @DisplayName("Tasks stop themselves if no more cells are alive")
  public void tasksStopWhenColonyIsDead() {
    LifeTask lifeTask = new LifeTask(logic, field);
    DeathTask deathTask = new DeathTask(logic, field);
    Thread lifeThread = new Thread(lifeTask);
    Thread deathThread = new Thread(deathTask);

    lifeThread.start();
    deathThread.start();
    assertTrue(lifeThread.isAlive());
    assertTrue(deathThread.isAlive());

    CountDownLatch waiter = new CountDownLatch(1);
    try {
      waiter.await(200, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    assertFalse(lifeThread.isAlive());
    assertFalse(deathThread.isAlive());
  }

  @Test
  @DisplayName("Tasks stop themselves if no cells changes")
  public void tasksStopWhenColonyIsStable() {
    LifeTask lifeTask = new LifeTask(logic, field);
    DeathTask deathTask = new DeathTask(logic, field);
    Thread lifeThread = new Thread(lifeTask);
    Thread deathThread = new Thread(deathTask);

    lifeThread.start();
    deathThread.start();
    assertTrue(lifeThread.isAlive());
    assertTrue(deathThread.isAlive());

    CountDownLatch waiter = new CountDownLatch(1);
    try {
      waiter.await(200, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    assertFalse(lifeThread.isAlive());
    assertFalse(deathThread.isAlive());
  }

  @Test
  @DisplayName("Tasks stop themselves if time limit is reached")
  public void tasksStopWhenTimeLimitReached() {
    new Configurations(20, 20, 2);
    field.randomize();
    LifeTask lifeTask = new LifeTask(logic, field);
    DeathTask deathTask = new DeathTask(logic, field);
    Thread lifeThread = new Thread(lifeTask);
    Thread deathThread = new Thread(deathTask);
    lifeThread.start();
    deathThread.start();

    assertTrue(lifeThread.isAlive());
    assertTrue(deathThread.isAlive());

    when(logic.getCount()).thenReturn(2);

    CountDownLatch waiter = new CountDownLatch(1);
    try {
      waiter.await(Configurations.get().getPeriod(), TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    assertFalse(lifeThread.isAlive());
    assertFalse(deathThread.isAlive());
  }

  @Test
  @DisplayName("timeLimitNotReached() returns true if counter less than a limit")
  public void timeLimitNotReachedReturnsTrueIfLessThanLimit() {
    // given
    new Configurations(20, 20, 10);
    DeathTask deathTask = new DeathTask(logic, field);
    LifeTask lifeTask = new LifeTask(logic, field);

    when(logic.getCount()).thenReturn(1);

    // when
    boolean lifeTaskNotReachedLimit = lifeTask.timeLimitNotReached();
    boolean deathTaskNotReachedLimit = deathTask.timeLimitNotReached();

    // then
    assertTrue(lifeTaskNotReachedLimit);
    assertTrue(deathTaskNotReachedLimit);
  }

  @Test
  @DisplayName("timeLimitNotReached() returns false if counter equals a limit")
  public void timeLimitNotReachedReturnsFalseIfEqualsLimit() {
    // given
    new Configurations(20, 20, 10);
    DeathTask deathTask = new DeathTask(logic, field);
    LifeTask lifeTask = new LifeTask(logic, field);

    when(logic.getCount()).thenReturn(10);

    // when
    boolean lifeTaskNotReachedLimit = lifeTask.timeLimitNotReached();
    boolean deathTaskNotReachedLimit = deathTask.timeLimitNotReached();

    // then
    assertFalse(lifeTaskNotReachedLimit);
    assertFalse(deathTaskNotReachedLimit);
  }

  @Test
  @DisplayName("timeLimitNotReached() returns false if counter greater than a limit")
  public void timeLimitNotReachedReturnsFalseIfGreaterThanLimit() {
    // given
    new Configurations(20, 20, 10);
    DeathTask deathTask = new DeathTask(logic, field);
    LifeTask lifeTask = new LifeTask(logic, field);

    when(logic.getCount()).thenReturn(11);

    // when
    boolean lifeTaskNotReachedLimit = lifeTask.timeLimitNotReached();
    boolean deathTaskNotReachedLimit = deathTask.timeLimitNotReached();

    // then
    assertFalse(lifeTaskNotReachedLimit);
    assertFalse(deathTaskNotReachedLimit);
  }
}
