package application;

import javafx.scene.paint.Paint;

public class Configurations {

  private static final int ARGUMENTS_EXPECTED = 3;

  public static final int CELL_SIZE = 15;
  public static final float RANDOM_CELLS = 0.10f;

  public static final Paint ALIVE = Paint.valueOf("#84d100");
  public static final Paint DEAD = Paint.valueOf("#6e6e6e");
  public static final Paint BACKGROUND = Paint.valueOf("#4a4a4a");
  public static final Paint BORDER = Paint.valueOf("#00aeff");

  public static final int BREED = 3;
  public static final int FORLORN = 2;
  public static final int OVERCROWD = 4;

  public static int width = 50;  // default
  private static final int MIN_WIDTH = 10;
  private static final int MAX_WIDTH = 50;

  public static int height = 40;  // default
  private static final int MIN_HEIGHT = 10;
  private static final int MAX_HEIGHT = 50;

  public static boolean timeLimit = false; // default
  public static int steps = 100;  // default
  private static final int MIN_STEPS = 1; //  TODO: why?
  public static final long PERIOD = 1000;

  public static String argumentsWarning = String.format(
      "Invalid arguments entered. Program will start with default settings:%n"
    + "Area %d x %d cells, time limit %d steps", width,height,steps);

  public static void setConfigurations(String[] args) throws InvalidArgumentsException {
    int[] arguments = parseArguments(args);
    if (arguments[0] >= MIN_WIDTH && arguments[0] <= MAX_WIDTH) {
      width = arguments[0];
    }
    if (arguments[1] >= MIN_HEIGHT && arguments[0] <= MAX_HEIGHT) {
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
