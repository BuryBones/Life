package application.controller;

import application.view.ControlBar;
import application.view.Graphics;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ViewController {

  private final Graphics graphics;
  private final ControlBar controlBar;

  @Inject
  public ViewController(Graphics graphics, ControlBar controlBar) {
    this.graphics = graphics;
    this.controlBar = controlBar;
  }

  public void startGraphics(Stage stage) {
    graphics.start(stage);
  }

  public void demandRepaint() {
    graphics.triggerPaint();
  }

  public void demandButtonsUnblock() {
    Platform.runLater(() -> {
      unblockButtons();
      blockStop();
    });
  }

  public void unblockButtons() {
    controlBar.unblockButtons();
  }

  public void blockStop() {
    controlBar.blockStop();
  }
}
