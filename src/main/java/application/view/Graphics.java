package application.view;

import application.Configurations;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Graphics {

  private PaintTask canvasRepaint;
  private ControlBar controlBar;
  private Canvas canvas;

  @Inject
  public void setCanvas(Canvas canvas) {
    this.canvas = canvas;
  }

  @Inject
  public void setControlBar(ControlBar controlBar) {
    this.controlBar = controlBar;
  }

  public void start(Stage stage) {
    VBox root = new VBox(2);
    canvasRepaint = new PaintTask(canvas);
    root.getChildren().addAll(canvas, controlBar);
    root.setAlignment(Pos.TOP_CENTER);
    root.setBackground(new Background(new BackgroundFill(
        Configurations.get().getBackground(), null, null)));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle(Configurations.get().getApplicationName());
    stage.setResizable(false);
    stage.show();
    canvas.paint();
  }

  public void triggerPaint() {
    Platform.runLater(new Thread(canvasRepaint));
  }

  public void unblockButtons() {
    controlBar.unblockButtons();
  }

  public void blockStop() {
    controlBar.blockStop();
  }
}
