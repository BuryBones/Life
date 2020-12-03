package application.controller;

import application.view.ButtonService;
import application.view.ControlBar;
import application.view.Graphics;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ViewController {

  private Graphics graphics;
  private ControlBar controlBar;
  private ModelController modelController;

  private final ButtonService startAction = () -> {
    modelController.start();
  };

  private final ButtonService stopAction = () -> {
    modelController.stop();
  };

  private final ButtonService clearAction = () -> {
    modelController.clear();
    demandRepaint();
  };

  private final ButtonService randomAction = () -> {
    modelController.random();
    demandRepaint();
  };

  public ViewController(ModelController modelController) {
    this.modelController = modelController;
  }

  public void startGraphics(Stage stage) {
    controlBar = new ControlBar(startAction,stopAction,clearAction,randomAction);
    graphics = new Graphics();
    graphics.start(stage,controlBar,modelController);
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
