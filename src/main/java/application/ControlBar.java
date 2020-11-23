package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControlBar extends HBox {

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
    super(2);
    init();
  }

  private void init() {
    setAlignment(Pos.CENTER);

    start = new Button("Start");
    start.setPrefSize(75,30);
    start.setOnAction(e -> Controller.getInstance().start());
    start.setPadding(new Insets(0,1,0,1));

    stop = new Button("Stop");
    stop.setPrefSize(75,30);
    stop.setOnAction(e -> Controller.getInstance().stop());
    stop.setPadding(new Insets(0,1,0,1));

    clear = new Button("Clear");
    clear.setPrefSize(75,30);
    clear.setOnAction(e -> Controller.getInstance().clear());
    clear.setPadding(new Insets(0,1,0,1));

    random = new Button("Random");
    random.setPrefSize(75,30);
    random.setOnAction(e -> Controller.getInstance().random());
    random.setPadding(new Insets(0,1,0,1));

    getChildren().addAll(start,stop,clear,random);
  }

  public void blockButtons() {
    start.setText("Running...");

    start.setDisable(true);
    clear.setDisable(true);
    random.setDisable(true);
  }

  public void unblockButtons() {
    start.setText("Start");

    start.setDisable(false);
    clear.setDisable(false);
    random.setDisable(false);
  }

}
