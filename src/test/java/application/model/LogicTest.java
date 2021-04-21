package application.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import application.BasicModule;
import application.Configurations;
import application.controller.ViewController;
import application.view.ControlBar;
import application.view.Graphics;
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

public class LogicTest {

  private Field field;
  private ViewController viewController;
  private Logic logic;

  @BeforeEach
  void setup() {

    Module testModule = Modules.override(new BasicModule()).with(new AbstractModule() {
      @Override
      protected void configure() {
        bind(ViewController.class).toInstance(mock(ViewController.class));
        bind(Graphics.class).toInstance(mock(Graphics.class));
        bind(ControlBar.class).toInstance(mock(ControlBar.class));
      }
    });

    Injector injector = Guice.createInjector(testModule);

    field = injector.getInstance(Field.class);
    viewController = injector.getInstance(ViewController.class);
    logic = injector.getInstance(Logic.class);
  }

  @Test
  @DisplayName("Initializes tasks")
  void runSimulationInitializesTasksTest() {
    // given
    new Configurations(4, 4);

    // when
    logic.runSimulation();

    // then
    assertNotNull(logic.getLifeTask());
    assertNotNull(logic.getDeathTask());
  }

  @Test
  @DisplayName("Logic demands to block buttons when reported about task stop")
  void reportTaskStopTest() {
    // given
    new Configurations(4, 4);

    // when
    logic.reportTaskStop();

    // then
    verify(viewController).demandButtonsUnblock();
  }

  @Test
  @DisplayName("Simulation starts and stops")
  void simulationStartAndStopTest() {
    // given
    new Configurations(30, 30);
    field.randomize();

    // when
    logic.runSimulation();

    Thread lifeThread = logic.getLifeThread();
    Thread deathThread = logic.getDeathThread();

    // then
    CountDownLatch waiter = new CountDownLatch(1);
    try {
      waiter.await(100, TimeUnit.MILLISECONDS);

      assertTrue(lifeThread.isAlive());
      assertTrue(deathThread.isAlive());
      logic.stopSimulation();

      waiter.await(200, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    assertFalse(lifeThread.isAlive());
    assertFalse(deathThread.isAlive());
  }

  @Test
  @DisplayName("Barrier action counts and demands repaint")
  void barrierActionTest() {
    // given
    new Configurations(30, 30, 2);
    field.randomize();
    int initialCounter = logic.getCount();

    // when
    logic.runSimulation();

    // then
    CountDownLatch waiter = new CountDownLatch(1);
    try {
      waiter.await(150, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    verify(viewController, atLeastOnce()).demandRepaint();
    assertNotEquals(initialCounter, logic.getCount());
  }
}
