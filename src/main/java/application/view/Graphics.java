package application.view;

import application.Configurations;
import application.controller.ModelController;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Graphics {

  private PaintTask canvasRepaint;

  public void start(Stage stage, ControlBar controlBar, ModelController modelController) {
    VBox root = new VBox(2);
    Canvas canvas = new Canvas(modelController);
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
  }

  public void triggerPaint() {
    Platform.runLater(new Thread(canvasRepaint));
  }

}
