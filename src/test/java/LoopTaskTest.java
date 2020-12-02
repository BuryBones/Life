import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    new Configurations(4,4);
    modelController = new ModelController();
    viewController = new ViewController(modelController);
    logic = new Logic(viewController);
    field = logic.initField();
    modelController.setLogic(logic);
    modelController.setField(field);
  }

//  @Mock
//  ViewController viewController;
//  AlertsController alertsController;
//  Logic logic;
//  Field field;
//
//  @InjectMocks
//  DeathTask deathTask;
//  LifeTask lifeTask;

//  @Test
//  public void runTest() {
////    Logic logic = new Logic();
////    Field field = logic.initField();
////    field.initCells();
////    field.randomize();
////    DeathTask deathTask = new DeathTask(logic,field);
////    LifeTask lifeTask = new LifeTask(logic,field);
//    DeathTask deathSpy = spy(deathTask);
//    LifeTask lifeSpy = spy(lifeTask);
//
//    verify(lifeSpy).isColonyDead();
//    verify(deathSpy).isColonyDead();
//  }

  @Test
  @DisplayName("Tasks ask field for according lists")
  public void prepareExecuteListTest() {
    Field fieldSpy = spy(field);

    DeathTask deathTask = new DeathTask(logic,fieldSpy);
    LifeTask lifeTask = new LifeTask(logic,fieldSpy);

    deathTask.prepareExecuteList();
    verify(fieldSpy).prepareDeathList();

    lifeTask.prepareExecuteList();
    verify(fieldSpy).prepareNewbornList();
  }

  @Test
  @DisplayName("Cells' isAlive value toggles upon invoking execute()")
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
  @DisplayName("Checks if there are any alive cells")
  public void isColonyDeadTest() {
    DeathTask deathTask = new DeathTask(logic,field);
    LifeTask lifeTask = new LifeTask(logic,field);
    assertTrue(deathTask.isColonyDead());
    assertTrue(lifeTask.isColonyDead());
    field.randomize();
    assertFalse(deathTask.isColonyDead());
    assertFalse(lifeTask.isColonyDead());
  }

}
