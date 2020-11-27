package application;

import javafx.application.Platform;

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

  public void demandRepaint() {
    Graphics.getInstance().triggerPaint();
  }

  public void demandButtonsBlock() {
    Platform.runLater(() -> {
      ModelController.getInstance().unblockButtons();
      ModelController.getInstance().blockStop();
    });
  }

  public void showInfoMessage(String message) {
    Platform.runLater(() -> InfoAlert.showInfoMessage(message));
  }

  public void showErrorMessageAndExit(String message) {
    Platform.runLater(() -> ErrorAlert.showErrorMessageAndExit(message));
  }

  public void blockButtons() {
    ControlBar.getInstance().blockButtons();
  }

  public void unblockButtons() {
    ControlBar.getInstance().unblockButtons();
  }

  public void blockStop() {
    ControlBar.getInstance().blockStop();
  }

  public void unblockStop() {
    ControlBar.getInstance().unblockStop();
  }

}
