package application;

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
    Logic.runSimulation();
  }
  public void stop() {
    Logic.stopSimulation();
  }
  public void clear() {
    Field.getInstance().initCells();
    Main.initArea();
  }
  public void random() {
    Field.getInstance().randomize();
  }
}
