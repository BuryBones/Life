public class Configurations {

  public static final int pxs = 15;

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

  public static void setConfigurations(int[] args) {
    if (args[0] >= MIN_WIDTH && args[0] <= MAX_WIDTH) {
      width = args[0];
    }
    if (args[1] >= MIN_HEIGHT && args[0] <= MAX_HEIGHT) {
      height = args[1];
    }
    if (args[2] == 0) {
      timeLimit = false;
    } else {
      timeLimit = true;
      steps = args[2];
    }
  }

}
