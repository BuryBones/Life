package application.view;

import application.Configurations;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControlBar extends HBox {

  private Button start;
  private Button stop;
  private Button clear;
  private Button random;
  private final EventHandler<ActionEvent> startAction;
  private final EventHandler<ActionEvent> stopAction;
  private final EventHandler<ActionEvent> clearAction;
  private final EventHandler<ActionEvent> randomAction;

  public ControlBar(
      EventHandler<ActionEvent> forStart,
      EventHandler<ActionEvent> forStop,
      EventHandler<ActionEvent>forClear,
      EventHandler<ActionEvent>forRandom
      ) {
    super(2);
    startAction = forStart;
    stopAction = forStop;
    clearAction = forClear;
    randomAction = forRandom;
    init();
  }

  private void init() {
    setAlignment(Pos.CENTER);

    start = new Button(Configurations.get().getStartButtonText());
    start.setPrefSize(75,30);
    start.setOnAction(startAction);
    start.setPadding(new Insets(0,1,0,1));

    stop = new Button(Configurations.get().getStopButtonText());
    stop.setPrefSize(75,30);
    stop.setOnAction(stopAction);
    stop.setPadding(new Insets(0,1,0,1));
    stop.setDisable(true);  // blocked on launch

    clear = new Button(Configurations.get().getClearButtonText());
    clear.setPrefSize(75,30);
    clear.setOnAction(clearAction);
    clear.setPadding(new Insets(0,1,0,1));

    random = new Button(Configurations.get().getRandomButtonText());
    random.setPrefSize(75,30);
    random.setOnAction(randomAction);
    random.setPadding(new Insets(0,1,0,1));

    getChildren().addAll(start,stop,clear,random);
  }

  public void blockButtons() {
    start.setText(Configurations.get().getStartButtonRunningText());

    start.setDisable(true);
    clear.setDisable(true);
    random.setDisable(true);
  }

  public void unblockButtons() {
    start.setText(Configurations.get().getStartButtonText());

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
