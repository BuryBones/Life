public class CounterTask implements Runnable {

  static int counter = 0;

  @Override
  public void run() {
    counter++;
    // TODO: delete sout
    System.out.println(counter);
  }
  public static void resetCounter() {
    counter = 0;
  }
}
