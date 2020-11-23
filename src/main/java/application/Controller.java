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
    blockButtons();
  }

  public void stop() {
    Logic.stopSimulation();
    unblockButtons();
  }

  public void clear() {
    Field.getInstance().initCells();
    Main.initArea();
  }

  public void random() {
    Field.getInstance().randomize();
  }

  public void blockButtons() {
    ControlBar.getInstance().blockButtons();
  }

  public void unblockButtons() {
    ControlBar.getInstance().unblockButtons();
  }
}
