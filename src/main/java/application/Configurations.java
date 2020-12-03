package application;

import java.util.ArrayList;
import javafx.scene.paint.Paint;

public class Configurations {

  private static Configurations currentConfigs;

  // common settings
  public final boolean enableTrackMemory = false;
  private final int argumentsExpected = 3;

  // colors
  private final Paint alive = Paint.valueOf("#84d100");
  private final Paint dead = Paint.valueOf("#6e6e6e");
  private final Paint background = Paint.valueOf("#4a4a4a");
  private final Paint border = Paint.valueOf("#00aeff");

  // simulations rules
  private final int breed = 3;
  private final int forlorn = 2;
  private final int overcrowd = 4;

  // field and cell settings
  private final int cellSize = 15; // pixels

    // width and height sizes are in cells
  private int width = 41;  // default
  private final int minWidth = 10;
  private final int maxWidth = 50;

  private int height = 41;  // default
  private final int minHeight = 10;
  private final int maxHeight = 50;

  private boolean timeLimit = true; // default
  private int steps = 100;  // default
  private final int maxSteps = 1000;
  private  final long period = 1000; // in millis

  private final float randomCells = 0.10f; // % of cells to be set as 'alive' by random

  // string constants
  private final String applicationName = "Bacteria colony";
  private String argumentsWarning = String.format(
      "Invalid arguments entered. Program will start with default settings:%n"
    + "Area %d x %d cells, time limit %d steps", width,height,steps);
  private final String errorExitString = "\nApplication will be closed!";
  private final String widthSetToDefault =
      "Requested width is not between "
      + minWidth + " and " + maxWidth + ". Width set to default: " + width;
  private final String heightSetToDefault =
      "Requested height is not between "
          + minHeight + " and " + maxHeight + ". Height set to default: " + height;
  private final String stepsSetToDefault =
      "Requested steps are greater than "
          + maxSteps + ". Steps set to default: " + steps;
    // buttons' strings
  private final String startButtonText = "Start";
  private final String startButtonRunningText = "Running";
  private final String stopButtonText = "Stop";
  private final String clearButtonText = "Clear";
  private final String randomButtonText = "Random";

  private boolean invalidArguments = false;
  private String setupMessage = "";

  public Configurations() {
    currentConfigs = this;
  }

  public Configurations(int width, int height) {
    this.width = width;
    this.height = height;
    currentConfigs = this;
  }

  public Configurations(int width, int height, int steps) {
    this.width = width;
    this.height = height;
    this.steps = steps;
    timeLimit = true;
    currentConfigs = this;
  }
  public void setConfigurations(String[] args) {
    int[] arguments;
    try {
      arguments = parseArguments(args);
      ArrayList<String> messages = new ArrayList<>(3);
      if (arguments[0] >= minWidth && arguments[0] <= maxWidth) {
        width = arguments[0];
      } else {
        messages.add(widthSetToDefault);
      }
      if (arguments[1] >= minHeight && arguments[1] <= maxHeight) {
        height = arguments[1];
      } else {
        messages.add(heightSetToDefault);
      }
      if (arguments[2] == 0) {
        timeLimit = false;
      } else if (arguments[2] <= maxSteps) {
        steps = arguments[2];
        timeLimit = true;
      } else {
        messages.add(stepsSetToDefault);
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
    int expectedArguments = argumentsExpected;
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

  public static Configurations get() {
    return currentConfigs;
  }

  public boolean isMemoryTrackingEnabled() {
    return enableTrackMemory;
  }

  public Paint getAlive() {
    return alive;
  }

  public Paint getDead() {
    return dead;
  }

  public Paint getBackground() {
    return background;
  }

  public Paint getBorder() {
    return border;
  }

  public int getBreed() {
    return breed;
  }

  public int getForlorn() {
    return forlorn;
  }

  public int getOvercrowd() {
    return overcrowd;
  }

  public int getCellSize() {
    return cellSize;
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
    return period;
  }

  public float getRandomCells() {
    return randomCells;
  }

  public String getApplicationName() {
    return applicationName;
  }

  public String getArgumentsWarning() {
    return argumentsWarning;
  }

  public String getErrorExitString() {
    return errorExitString;
  }

  public String getStartButtonText() {
    return startButtonText;
  }

  public String getStartButtonRunningText() {
    return startButtonRunningText;
  }

  public String getStopButtonText() {
    return stopButtonText;
  }

  public String getClearButtonText() {
    return clearButtonText;
  }

  public String getRandomButtonText() {
    return randomButtonText;
  }

  public boolean isInvalidArguments() {
    return invalidArguments;
  }

  public String getSetupMessage() {
    return setupMessage;
  }
}
