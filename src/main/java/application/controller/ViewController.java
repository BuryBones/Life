package application.controller;

import application.view.ButtonService;
import application.view.ControlBar;
import application.view.Graphics;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ViewController {

  private Graphics graphics;
  private ControlBar controlBar;

  private static ViewController instance = new ViewController();

  private final ButtonService startAction = () -> {
    ModelController.getInstance().start();
  };

  private final ButtonService stopAction = () -> {
    ModelController.getInstance().stop();
  };

  private final ButtonService clearAction = () -> {
    ModelController.getInstance().clear();
    ViewController.getInstance().demandRepaint();
  };

  private final ButtonService randomAction = () -> {
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

  public void unblockButtons() {
    controlBar.unblockButtons();
  }

  public void blockStop() {
    controlBar.blockStop();
  }

}
