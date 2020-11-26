package application;

import javafx.scene.paint.Paint;

public class Configurations {

  // common settings
  public static final boolean TRACK_MEMORY = false;
  private static final int ARGUMENTS_EXPECTED = 3;

  // colors
  public static final Paint ALIVE = Paint.valueOf("#84d100");
  public static final Paint DEAD = Paint.valueOf("#6e6e6e");
  public static final Paint BACKGROUND = Paint.valueOf("#4a4a4a");
  public static final Paint BORDER = Paint.valueOf("#00aeff");

  // simulations rules
  public static final int BREED = 3;
  public static final int FORLORN = 2;
  public static final int OVERCROWD = 4;

  // field and cell settings
  public static final int CELL_SIZE = 15; // pixels

    // width and height sizes are in cells
  public static int width = 50;  // default
  private static final int MIN_WIDTH = 10;
  private static final int MAX_WIDTH = 50;

  public static int height = 40;  // default
  private static final int MIN_HEIGHT = 10;
  private static final int MAX_HEIGHT = 50;

  public static boolean timeLimit = false; // default
  public static int steps = 100;  // default
  public static final long PERIOD = 1000; // in millis

  public static final float RANDOM_CELLS = 0.10f; // % of cells to be set as 'alive' by random

  // string constants
  public static final String APPLICATION_NAME = "Bacteria colony";
  public static String argumentsWarning = String.format(
      "Invalid arguments entered. Program will start with default settings:%n"
    + "Area %d x %d cells, time limit %d steps", width,height,steps);
  public static final String ERROR_EXIT_STRING = "\nApplication will be closed!";
    // buttons' strings
  public static final String START_BUTTON_TEXT = "Start";
  public static final String START_BUTTON_RUNNING_TEXT = "Running";
  public static final String STOP_BUTTON_TEXT = "Stop";
  public static final String CLEAR_BUTTON_TEXT = "Clear";
  public static final String RANDOM_BUTTON_TEXT = "Random";

  // TODO: warning if arguments passed but are beyond the limits
  public static void setConfigurations(String[] args) throws InvalidArgumentsException {
    int[] arguments = parseArguments(args);
    if (arguments[0] >= MIN_WIDTH && arguments[0] <= MAX_WIDTH) {
      width = arguments[0];
    }
    if (arguments[1] >= MIN_HEIGHT && arguments[1] <= MAX_HEIGHT) {
      height = arguments[1];
    }
    if (arguments[2] == 0) {
      timeLimit = false;
    } else {
      timeLimit = true;
      steps = arguments[2];
    }
  }

  private static int[] parseArguments(String[] args) throws InvalidArgumentsException {
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
}
