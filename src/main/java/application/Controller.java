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
    unblockStop();
  }

  public void stop() {
    Logic.stopSimulation();
    unblockButtons();
    blockStop();
  }

  public void clear() {
    Field.getInstance().initCells();
    Graphics.getInstance().initArea();
  }

  // TODO: delete
  // service needs
  public void toggleAll() {
    Field.getInstance().getCells().forEach(Cell::toggle);
  }

  public void random() {
    Field.getInstance().initCells();
    Field.getInstance().randomize();
    Graphics.getInstance().initArea();
  }

  public void blockButtons() {
    ControlBar.getInstance().blockButtons();
  }

  public void unblockButtons() {
    ControlBar.getInstance().unblockButtons();
  }

  public void blockStop() {
    ControlBar.getInstance().blockStop();
  }

  public void unblockStop() {
    ControlBar.getInstance().unblockStop();
  }
}
