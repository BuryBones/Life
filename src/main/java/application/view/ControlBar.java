package application.view;

import application.Configurations;
import application.controller.ModelController;
import application.controller.ViewController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
  private final EventHandler<ActionEvent> startAction = event -> {
    blockButtons();
    unblockStop();
    ModelController.getInstance().start();
  };
  private final EventHandler<ActionEvent> stopAction = event -> {
    blockStop();
    unblockButtons();
    ModelController.getInstance().stop();
  };
  private final EventHandler<ActionEvent> clearAction = event -> {
    ModelController.getInstance().clear();
    ViewController.getInstance().demandRepaint();
  };
  private final EventHandler<ActionEvent> randomAction = event -> {
    ModelController.getInstance().random();
    ViewController.getInstance().demandRepaint();
  };


  private ControlBar() {
    super(2);
    init();
  }

  private void init() {
    setAlignment(Pos.CENTER);

    start = new Button(Configurations.getCurrentConfigs().getStartButtonText());
    start.setPrefSize(75,30);
    start.setOnAction(startAction);
    start.setPadding(new Insets(0,1,0,1));

    stop = new Button(Configurations.getCurrentConfigs().getStopButtonText());
    stop.setPrefSize(75,30);
    stop.setOnAction(stopAction);
    stop.setPadding(new Insets(0,1,0,1));
    stop.setDisable(true);  // blocked on launch

    clear = new Button(Configurations.getCurrentConfigs().getClearButtonText());
    clear.setPrefSize(75,30);
    clear.setOnAction(clearAction);
    clear.setPadding(new Insets(0,1,0,1));

    random = new Button(Configurations.getCurrentConfigs().getRandomButtonText());
    random.setPrefSize(75,30);
    random.setOnAction(randomAction);
    random.setPadding(new Insets(0,1,0,1));

    getChildren().addAll(start,stop,clear,random);
  }

  public void blockButtons() {
    start.setText(Configurations.getCurrentConfigs().getStartButtonRunningText());

    start.setDisable(true);
    clear.setDisable(true);
    random.setDisable(true);
  }

  public void unblockButtons() {
    start.setText(Configurations.getCurrentConfigs().getStartButtonText());

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
