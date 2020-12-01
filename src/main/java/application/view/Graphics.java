package application.view;

import application.Configurations;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Graphics {

  private static Graphics instance = new Graphics();

  private Graphics() {}

  public static Graphics getInstance() {
    if (instance == null) {
      instance = new Graphics();
    }
    return instance;
  }

  private PaintTask canvasRepaint;

  public void start(Stage stage) {
    VBox root = new VBox(2);
    Canvas canvas = new Canvas();
    canvasRepaint = new PaintTask(canvas);
    ControlBar controlBar = ControlBar.getInstance();
    root.getChildren().addAll(canvas, controlBar);
    root.setAlignment(Pos.TOP_CENTER);
    root.setBackground(new Background(new BackgroundFill(
        Configurations.getCurrentConfigs().getBackground(), null, null)));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle(Configurations.getCurrentConfigs().getApplicationName());
    stage.setResizable(false);
    stage.show();
  }

  public void triggerPaint() {
    Platform.runLater(new Thread(canvasRepaint));
  }

}
