import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import application.model.Cell;
import application.model.DeathTask;
import application.model.Field;
import application.model.LifeTask;
import application.model.Logic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoopTaskTest {

  @Test
  public void runTest() {
    Logic logic = new Logic();
    Field field = logic.initField();
    field.initCells();
    field.randomize();
    DeathTask deathTask = new DeathTask(logic,field);
    LifeTask lifeTask = new LifeTask(logic,field);
    DeathTask deathSpy = spy(deathTask);
    LifeTask lifeSpy = spy(lifeTask);

    doNothing().when(lifeSpy).prepareExecuteList();
    doNothing().when(deathSpy).prepareExecuteList();
//    DeathTask deathTask = Mockito.mock(DeathTask.class);
//    doNothing().doCallRealMethod().when(deathTask).prepareExecuteList();
//    doNothing().when(deathTask).prepareExecuteList();
//    deathTask.run();
//    verify(deathTask, times(1)).prepareExecuteList();
    new Thread(deathTask).start();
    new Thread(lifeTask).start();
    verify(lifeSpy).isColonyDead();
    verify(deathSpy).isColonyDead();
  }

  @Test
  public void prepareExecuteListTest() {

  }

  @Test
  @DisplayName("Cells' isAlive value toggles upon invoking execute()")
  public void executeTest() {
    Logic logic = new Logic();
    Field field = logic.initField();
    field.initCells();
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
    Logic logic = new Logic();
    Field field = logic.initField();
    field.initCells();
    DeathTask deathTask = new DeathTask(logic,field);
    LifeTask lifeTask = new LifeTask(logic,field);
    assertTrue(deathTask.isColonyDead());
    assertTrue(lifeTask.isColonyDead());
    field.randomize();
    assertFalse(deathTask.isColonyDead());
    assertFalse(lifeTask.isColonyDead());
  }

}
