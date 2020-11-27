package application.View;

import application.Configurations;
import application.Controller.ModelController;
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

    start = new Button(Configurations.START_BUTTON_TEXT);
    start.setPrefSize(75,30);
    start.setOnAction(e -> ModelController.getInstance().start());
    start.setPadding(new Insets(0,1,0,1));

    stop = new Button(Configurations.STOP_BUTTON_TEXT);
    stop.setPrefSize(75,30);
    stop.setOnAction(e -> ModelController.getInstance().stop());
    stop.setPadding(new Insets(0,1,0,1));
    stop.setDisable(true);  // blocked on launch

    clear = new Button(Configurations.CLEAR_BUTTON_TEXT);
    clear.setPrefSize(75,30);
    clear.setOnAction(e -> ModelController.getInstance().clear());
    clear.setPadding(new Insets(0,1,0,1));

    random = new Button(Configurations.RANDOM_BUTTON_TEXT);
    random.setPrefSize(75,30);
    random.setOnAction(e -> ModelController.getInstance().random());
    random.setPadding(new Insets(0,1,0,1));

    getChildren().addAll(start,stop,clear,random);
  }

  public void blockButtons() {
    start.setText(Configurations.START_BUTTON_RUNNING_TEXT);

    start.setDisable(true);
    clear.setDisable(true);
    random.setDisable(true);
  }

  public void unblockButtons() {
    start.setText(Configurations.START_BUTTON_TEXT);

    start.setDisable(false);
    clear.setDisable(false);
    random.setDisable(false);
  }

  public void blockStop() {
    stop.setDisable(true);
  }

  public void unblockStop() {
    stop.setDisable(false);
  }
}
