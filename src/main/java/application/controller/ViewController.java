package application.controller;

import application.view.ControlBar;
import application.view.Graphics;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class ViewController {

  private Graphics graphics;
  private ControlBar controlBar;

  private static ViewController instance = new ViewController();

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

  private ViewController() {

  }

  public static ViewController getInstance() {
    if (instance == null) {
      instance = new ViewController();
    }
    return instance;
  }

  public void startGraphics(Stage stage) {
    controlBar = new ControlBar(startAction,stopAction,clearAction,randomAction);
    graphics = new Graphics();
    graphics.start(stage,controlBar);
  }

  public void demandRepaint() {
    graphics.triggerPaint();
  }

  public void demandButtonsBlock() {
    Platform.runLater(() -> {
      unblockButtons();
      blockStop();
    });
  }

  public void blockButtons() {
    controlBar.blockButtons();
  }

  public void unblockButtons() {
    controlBar.unblockButtons();
  }

  public void blockStop() {
    controlBar.blockStop();
  }

  public void unblockStop() {
    controlBar.unblockStop();
  }

}
