package application.controller;

import application.view.Graphics;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ViewController {

  private Graphics graphics;

  @Inject
  public void setGraphics(Graphics graphics) {
    this.graphics = graphics;
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
    graphics.unblockButtons();
  }

  public void blockStop() {
    graphics.blockStop();
  }
}
