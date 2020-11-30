package application.Controller;

import application.View.ControlBar;
import application.View.Graphics;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ViewController {

  private static ViewController instance = new ViewController();

  private ViewController() {

  }

  public static ViewController getInstance() {
    if (instance == null) {
      instance = new ViewController();
    }
    return instance;
  }

  public void startGraphics(Stage stage) {
    Graphics.getInstance().start(stage);
  }

  public void demandRepaint() {
    Graphics.getInstance().triggerPaint();
  }

  public void demandButtonsBlock() {
    Platform.runLater(() -> {
      unblockButtons();
      blockStop();
    });
  }

  public void unblockButtons() {
    ControlBar.getInstance().unblockButtons();
  }

  public void blockStop() {
    ControlBar.getInstance().blockStop();
  }

}
