package application;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;

public class ControlBar extends ButtonBar {

  private static ControlBar instance = new ControlBar();

  public static ControlBar getInstance() {
    if (instance == null) {
      instance = new ControlBar();
    }
    return instance;
  }

  private Button start;
  private Button stop;
  private Button clear;
  private Button random;

  private ControlBar() {
    init();
  }

  private void init() {
    start = new Button("Start");
    start.setOnAction(e -> Controller.getInstance().start());
    stop = new Button("Stop");
    stop.setOnAction(e -> Controller.getInstance().stop());
    clear = new Button("Clear");
    clear.setOnAction(e -> Controller.getInstance().clear());
    random = new Button("Random");
    random.setOnAction(e -> Controller.getInstance().random());

    getButtons().addAll(start,stop,clear,random);
  }

  public void blockButtons() {
    start.setText("Running...");

    start.setDisable(true);
    clear.setDisable(true);
    random.setDisable(true);
  }

  public void unBlockButtons() {
    start.setText("Start");

    start.setDisable(false);
    clear.setDisable(false);
    random.setDisable(false);
  }

}
