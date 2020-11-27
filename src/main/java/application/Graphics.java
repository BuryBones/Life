package application;

import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
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

  private final PaintTask canvasRepaint = new PaintTask();
  private Pane canvas;

  public void start(Stage stage) {
    VBox root = new VBox(2);
    initPane();
    paint();
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

  public void initPane() {
    int canvasWidth = Configurations.width * Configurations.CELL_SIZE + 4;
    int canvasHeight = Configurations.height * Configurations.CELL_SIZE + 4;
    canvas = new Pane();
    canvas.prefHeightProperty().setValue(canvasHeight);
    canvas.prefWidthProperty().setValue(canvasWidth);
    canvas.maxHeightProperty().setValue(canvasHeight);
    canvas.maxWidthProperty().setValue(canvasWidth);
    canvas.minHeightProperty().setValue(canvasHeight);
    canvas.minWidthProperty().setValue(canvasWidth);

    canvas.setBackground(new Background(
        new BackgroundFill(Configurations.BACKGROUND, null, null)));
    canvas.setBorder(new Border(
        new BorderStroke(Configurations.BORDER,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            new BorderWidths(2.0))));
  }

  public void triggerPaint() {
    Platform.runLater(new Thread(canvasRepaint));
  }

  public void paint() {
    ObservableList<Node> canvasChildren = canvas.getChildren();
    canvasChildren.removeAll(canvasChildren);

    List<Cell> cells = Field.getInstance().getCells();
    for (int i = 0; i < cells.size(); i++) {
      Circle circle = new Circle(Configurations.CELL_SIZE / 2.0f);
      circle.setFill(cells.get(i).colorProperty().get());
      int x = ((i % Configurations.width) * Configurations.CELL_SIZE) + (int) circle.getRadius();
      int y = ((i / Configurations.width) * Configurations.CELL_SIZE) + (int) circle.getRadius();
      int currentCellIndex = i;
      circle.setOnMouseClicked(mouseEvent -> {
        cells.get(currentCellIndex).toggle();
        paint();
      });
      circle.setCenterX(2 + x);
      circle.setCenterY(2 + y);
      canvasChildren.add(circle);
    }
  }

  public void showConfigWarning(String message) {
    Alert alert = new Alert(
        AlertType.WARNING,
        message + "\n" + Configurations.argumentsWarning,
        ButtonType.OK, ButtonType.CLOSE);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isEmpty() || result.get() == ButtonType.CLOSE) {
      Main.exit();
    } else if (result.get() == ButtonType.OK) {
      System.out.println("Default settings");
    }
  }

  public void showErrorMessageAndExit(String message) {
    Alert alert = new Alert(
        AlertType.ERROR,
        "Error message: " + message + Configurations.ERROR_EXIT_STRING,
        ButtonType.CLOSE);
    alert.showAndWait();
    Main.exit();
  }

  public void showInfoMessage(String message) {
    Alert alert = new Alert(
        AlertType.INFORMATION,
        message,
        ButtonType.OK);
    alert.showAndWait();
  }

  public Pane getCanvas() {
    return canvas;
  }
}
