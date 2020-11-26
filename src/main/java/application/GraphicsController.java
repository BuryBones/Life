package application;

import javafx.application.Platform;

public class GraphicsController {

  private static GraphicsController instance = new GraphicsController();

  private GraphicsController() {

  }

  public static GraphicsController getInstance() {
    if (instance == null) {
      instance = new GraphicsController();
    }
    return instance;
  }

  public void demandRepaint() {
    Graphics.getInstance().triggerPaint();
  }

  public void demandButtonsBlock() {
    Platform.runLater(() -> {
      Controller.getInstance().unblockButtons();
      Controller.getInstance().blockStop();
    });
  }

  public void showInfoMessage(String message) {
    Platform.runLater(() -> Graphics.getInstance().showInfoMessage(message));
  }

  public void showErrorMessageAndExit(String message) {
    Platform.runLater(() -> Graphics.getInstance().showErrorMessageAndExit(message));
  }

}
