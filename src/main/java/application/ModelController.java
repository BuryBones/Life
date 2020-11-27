package application;

public class ModelController {

  private static ModelController instance;

  public static ModelController getInstance() {
    if (instance == null) {
      instance = new ModelController();
    }
    return instance;
  }

  private ModelController() {}

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
    ViewController.getInstance().demandRepaint();
  }

  public void random() {
    Field.getInstance().initCells();
    Field.getInstance().randomize();
    ViewController.getInstance().demandRepaint();
  }

  public void blockButtons() {
    ViewController.getInstance().blockButtons();
  }

  public void unblockButtons() {
    ViewController.getInstance().unblockButtons();
  }

  public void blockStop() {
    ViewController.getInstance().blockStop();
  }

  public void unblockStop() {
    ViewController.getInstance().unblockStop();
  }
}
