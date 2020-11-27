package application;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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

//  private Pane canvas;
  private final PaintTask canvasRepaint = new PaintTask();

  public void start(Stage stage) {
    VBox root = new VBox(2);
//    initPane();
//    paint();
    Canvas canvas = new Canvas();
    HBox controlBar = ControlBar.getInstance();
    root.getChildren().addAll(canvas, controlBar);
    root.setAlignment(Pos.TOP_CENTER);
    root.setBackground(new Background(new BackgroundFill(Configurations.BACKGROUND, null, null)));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle(Configurations.APPLICATION_NAME);
    stage.setResizable(false);
    stage.show();
  }



  public void triggerPaint() {
    Platform.runLater(new Thread(canvasRepaint));
  }

//  public void paint() {
//    ObservableList<Node> canvasChildren = canvas.getChildren();
//    canvasChildren.removeAll(canvasChildren);
//
//    List<Cell> cells = Field.getInstance().getCells();
//    for (int i = 0; i < cells.size(); i++) {
//      Circle circle = new Circle(Configurations.CELL_SIZE / 2.0f);
//      circle.setFill(cells.get(i).colorProperty().get());
//      int x = ((i % Configurations.width) * Configurations.CELL_SIZE) + (int) circle.getRadius();
//      int y = ((i / Configurations.width) * Configurations.CELL_SIZE) + (int) circle.getRadius();
//      int currentCellIndex = i;
//      circle.setOnMouseClicked(mouseEvent -> {
//        cells.get(currentCellIndex).toggle();
//        paint();
//      });
//      circle.setCenterX(2 + x);
//      circle.setCenterY(2 + y);
//      canvasChildren.add(circle);
//    }
//  }





  public Pane getCanvas() {
    return canvas;
  }
}
