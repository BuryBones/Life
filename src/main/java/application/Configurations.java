package application;

import java.util.ArrayList;
import javafx.scene.paint.Paint;

public class Configurations {

  public static Configurations currentConfigs;

  // common settings
  public final boolean ENABLE_TRACK_MEMORY = false;
  private final int ARGUMENTS_EXPECTED = 3;

  // colors
  public final Paint ALIVE = Paint.valueOf("#84d100");
  public final Paint DEAD = Paint.valueOf("#6e6e6e");
  public final Paint BACKGROUND = Paint.valueOf("#4a4a4a");
  public final Paint BORDER = Paint.valueOf("#00aeff");

  // simulations rules
  public final int BREED = 3;
  public final int FORLORN = 2;
  public final int OVERCROWD = 4;

  // field and cell settings
  public final int CELL_SIZE = 15; // pixels

    // width and height sizes are in cells
  public int width = 41;  // default
  private final int MIN_WIDTH = 10;
  private final int MAX_WIDTH = 50;

  public int height = 41;  // default
  private final int MIN_HEIGHT = 10;
  private final int MAX_HEIGHT = 50;

  public boolean timeLimit = false; // default
  public int steps = 100;  // default
  private final int MAX_STEPS = 1000;
  public  final long PERIOD = 1000; // in millis

  public final float RANDOM_CELLS = 0.10f; // % of cells to be set as 'alive' by random

  // string constants
  public final String APPLICATION_NAME = "Bacteria colony";
  public String argumentsWarning = String.format(
      "Invalid arguments entered. Program will start with default settings:%n"
    + "Area %d x %d cells, time limit %d steps", width,height,steps);
  public final String ERROR_EXIT_STRING = "\nApplication will be closed!";
  private final String WIDTH_SET_TO_DEFAULT =
      "Requested width is not between "
      + MIN_WIDTH + " and " + MAX_WIDTH + ". Width set to default: " + width;
  private final String HEIGHT_SET_TO_DEFAULT =
      "Requested height is not between "
          + MIN_HEIGHT + " and " + MAX_HEIGHT + ". Height set to default: " + height;
  private final String STEPS_SET_TO_DEFAULT =
      "Requested steps are greater than "
          + MAX_STEPS + ". Steps set to default: " + steps;
    // buttons' strings
  public final String START_BUTTON_TEXT = "Start";
  public final String START_BUTTON_RUNNING_TEXT = "Running";
  public final String STOP_BUTTON_TEXT = "Stop";
  public final String CLEAR_BUTTON_TEXT = "Clear";
  public final String RANDOM_BUTTON_TEXT = "Random";

  public boolean invalidArguments = false;
  public String setupMessage = "";

  public Configurations() {
    currentConfigs = this;
  }

  public void setConfigurations(String[] args) {
    int[] arguments;
    try {
      arguments = parseArguments(args);
      ArrayList<String> messages = new ArrayList<>(3);
      if (arguments[0] >= MIN_WIDTH && arguments[0] <= MAX_WIDTH) {
        width = arguments[0];
      } else {
        messages.add(WIDTH_SET_TO_DEFAULT);
      }
      if (arguments[1] >= MIN_HEIGHT && arguments[1] <= MAX_HEIGHT) {
        height = arguments[1];
      } else {
        messages.add(HEIGHT_SET_TO_DEFAULT);
      }
      if (arguments[2] == 0) {
        timeLimit = false;
      } else if (arguments[2] <= MAX_STEPS) {
        steps = arguments[2];
        timeLimit = true;
      } else {
        messages.add(STEPS_SET_TO_DEFAULT);
      }
      if (messages.size() > 0) {
        StringBuilder completeMessage = new StringBuilder();
        messages.forEach(str -> completeMessage.append(str).append("\n"));
        setupMessage = completeMessage.toString();
      }
    } catch (InvalidArgumentsException e) {
      invalidArguments = true;
      setupMessage = e.getMessage();
    }

  }

  public int[] parseArguments(String[] args) throws InvalidArgumentsException {
    int expectedArguments = ARGUMENTS_EXPECTED;
    int[] result = new int[expectedArguments];
    if (args.length != expectedArguments) {
      throw new InvalidArgumentsException("Found " + args.length + " arguments, expected " + expectedArguments);
    } else {
      for (int i = 0; i < args.length;i ++) {
        try {
          int argument = Integer.parseInt(args[i]);
          if (argument < 0) {
            throw new InvalidArgumentsException("Argument must not be less than zero!");
          }
          result[i] = argument;
        } catch (NumberFormatException nfe) {
          throw new InvalidArgumentsException("Numbers expected!",nfe);
        }
      }
    }
    return result;
  }

  public static Configurations getCurrentConfigs() {
    return currentConfigs;
  }

  public boolean isMemoryTrackingEnabled() {
    return ENABLE_TRACK_MEMORY;
  }

  public Paint getAlive() {
    return ALIVE;
  }

  public Paint getDead() {
    return DEAD;
  }

  public Paint getBackground() {
    return BACKGROUND;
  }

  public Paint getBorder() {
    return BORDER;
  }

  public int getBreed() {
    return BREED;
  }

  public int getForlorn() {
    return FORLORN;
  }

  public int getOvercrowd() {
    return OVERCROWD;
  }

  public int getCellSize() {
    return CELL_SIZE;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public boolean isTimeLimit() {
    return timeLimit;
  }

  public int getSteps() {
    return steps;
  }

  public long getPeriod() {
    return PERIOD;
  }

  public float getRandomCells() {
    return RANDOM_CELLS;
  }

  public String getApplicationName() {
    return APPLICATION_NAME;
  }

  public String getArgumentsWarning() {
    return argumentsWarning;
  }

  public String getErrorExitString() {
    return ERROR_EXIT_STRING;
  }

  public String getStartButtonText() {
    return START_BUTTON_TEXT;
  }

  public String getStartButtonRunningText() {
    return START_BUTTON_RUNNING_TEXT;
  }

  public String getStopButtonText() {
    return STOP_BUTTON_TEXT;
  }

  public String getClearButtonText() {
    return CLEAR_BUTTON_TEXT;
  }

  public String getRandomButtonText() {
    return RANDOM_BUTTON_TEXT;
  }

  public boolean isInvalidArguments() {
    return invalidArguments;
  }

  public String getSetupMessage() {
    return setupMessage;
  }
}
