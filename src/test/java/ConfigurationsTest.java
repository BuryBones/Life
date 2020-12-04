import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import application.Configurations;
import application.InvalidArgumentsException;
import org.junit.jupiter.api.Test;

public class ConfigurationsTest {

  @Test
  public void parseCorrectArgumentsTest() throws InvalidArgumentsException {
    String[] args = {"30","20","15"};
    Configurations config = new Configurations();

    int[] result = config.parseArguments(args);
    assertEquals(result[0],30);
    assertEquals(result[1],20);
    assertEquals(result[2],15);
  }

  @Test
  public void parseNegativeNumbersArgumentsTest() {
    String[] args = {"-30","20","15"};
    Configurations config = new Configurations();
    Throwable thrown = assertThrows(InvalidArgumentsException.class, () ->
    {
      config.parseArguments(args);
    });
    assertNotNull(thrown);
  }

  @Test
  public void parseNotNumbersArgumentsTest() {
    String[] args = {"30","20","text"};
    Configurations config = new Configurations();
    Throwable thrown = assertThrows(InvalidArgumentsException.class, () ->
    {
      config.parseArguments(args);
    });
    assertNotNull(thrown);
  }

  @Test
  public void parseWrongAmountArgumentsTest() {
    String[] args = {"20","15"};
    Configurations config = new Configurations();
    Throwable thrown = assertThrows(InvalidArgumentsException.class, () ->
    {
      config.parseArguments(args);
    });
    assertNotNull(thrown);
  }

  @Test
  public void setConfigurationsNoDefaultTest() {
    Configurations config = new Configurations();
    String[] args = {"35","30","50"};

    config.setConfigurations(args);
    assertFalse(config.isInvalidArguments());
    assertTrue(config.isTimeLimit());
    assertEquals(35,config.getWidth());
    assertEquals(30,config.getHeight());
    assertEquals(50,config.getSteps());
  }

  @Test
  public void setConfigurationsNoDefaultNoTimeLimitTest() {
    Configurations config = new Configurations();
    String[] args = {"35","30","0"};

    config.setConfigurations(args);
    assertFalse(config.isInvalidArguments());
    assertFalse(config.isTimeLimit());
    assertEquals(35,config.getWidth());
    assertEquals(30,config.getHeight());
    assertEquals(100,config.getSteps());
  }

  @Test
  public void setConfigurationsWithDefaultWidthTest() {
    Configurations config = new Configurations();
    String[] args = {"100","30","50"};

    config.setConfigurations(args);
    assertFalse(config.isInvalidArguments());
    assertTrue(config.isTimeLimit());
    assertEquals(41,config.getWidth());
    assertEquals(30,config.getHeight());
    assertEquals(50,config.getSteps());
  }

  @Test
  public void setConfigurationsWithDefaultHeightTest() {
    Configurations config = new Configurations();
    String[] args = {"30","100","50"};

    config.setConfigurations(args);
    assertFalse(config.isInvalidArguments());
    assertTrue(config.isTimeLimit());
    assertEquals(30,config.getWidth());
    assertEquals(41,config.getHeight());
    assertEquals(50,config.getSteps());
  }

  @Test
  public void setConfigurationsWithDefaultStepsTest() {
    Configurations config = new Configurations();
    String[] args = {"25","35","1001"};

    config.setConfigurations(args);
    assertFalse(config.isInvalidArguments());
    assertTrue(config.isTimeLimit());
    assertEquals(25,config.getWidth());
    assertEquals(35,config.getHeight());
    assertEquals(100,config.getSteps());
  }

  @Test
  public void setConfigurationsWithDefaultEverythingTest() {
    Configurations config = new Configurations();
    String[] args = {"5","1","12000"};

    config.setConfigurations(args);
    assertFalse(config.isInvalidArguments());
    assertTrue(config.isTimeLimit());
    assertEquals(41,config.getWidth());
    assertEquals(41,config.getHeight());
    assertEquals(100,config.getSteps());
  }

  @Test
  public void setConfigurationsWithInvalidArgumentsTest() {
    Configurations config = new Configurations();
    String[] args = {"not","valid","arguments"};

    config.setConfigurations(args);
    assertTrue(config.isInvalidArguments());
    assertTrue(config.isTimeLimit());
    assertEquals(41,config.getWidth());
    assertEquals(41,config.getHeight());
    assertEquals(100,config.getSteps());
  }
}
