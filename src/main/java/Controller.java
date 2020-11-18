public class Controller {

  public static Controller instance;

  public static Controller getInstance() {
    if (instance == null) {
      instance = new Controller();
    }
    return instance;
  }

  private Controller() {}

  public void start() {
    Main.runLogic();
  }
  public void stop() {
    Main.stopThreads();
  }
  public void clear() {
    Field.instance.initCells();
  }
  public void random() {
    Field.instance.randomize();
  }
}
