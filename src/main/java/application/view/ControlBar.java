package application.view;

import application.Configurations;
import application.view.button_services.ButtonService;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControlBar extends HBox {

  private Button start;
  private Button stop;
  private Button clear;
  private Button random;

  @Inject
  public ControlBar(
      @Named("StartButtonAction") ButtonService forStart,
      @Named("StopButtonAction") ButtonService forStop,
      @Named("ClearButtonAction") ButtonService forClear,
      @Named("RandomButtonAction") ButtonService forRandom
  ) {
    super(2);
    init();
    setStartAction(forStart);
    setStopAction(forStop);
    setClearAction(forClear);
    setRandomAction(forRandom);
  }

  private void init() {
    setAlignment(Pos.CENTER);

    start = new Button(Configurations.get().getStartButtonText());
    start.setPrefSize(75, 30);
    start.setPadding(new Insets(0, 1, 0, 1));

    stop = new Button(Configurations.get().getStopButtonText());
    stop.setPrefSize(75, 30);
    stop.setPadding(new Insets(0, 1, 0, 1));
    stop.setDisable(true);  // blocked on launch

    clear = new Button(Configurations.get().getClearButtonText());
    clear.setPrefSize(75, 30);
    clear.setPadding(new Insets(0, 1, 0, 1));

    random = new Button(Configurations.get().getRandomButtonText());
    random.setPrefSize(75, 30);
    random.setPadding(new Insets(0, 1, 0, 1));

    getChildren().addAll(start, stop, clear, random);
  }

  public void setStartAction(ButtonService action) {
    start.setOnAction(e -> {
      blockButtons();
      unblockStop();
      action.apply();
    });
  }

  public void setStopAction(ButtonService action) {
    stop.setOnAction(e -> {
      blockStop();
      unblockButtons();
      action.apply();
    });
  }

  public void setClearAction(ButtonService action) {
    clear.setOnAction(e -> {
      action.apply();
    });
  }

  public void setRandomAction(ButtonService action) {
    random.setOnAction(e -> {
      action.apply();
    });
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
