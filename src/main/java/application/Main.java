package application;

import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {

  private static Pane canvas;
  private static String[] arguments;

  @Override
  public void start(Stage stage) {
    setConfigs(arguments);
    VBox root = new VBox(5);
    initPane();
    initArea();
    HBox controlBar = ControlBar.getInstance();
    root.getChildren().addAll(canvas, controlBar);
    root.setBackground(new Background(new BackgroundFill(Configurations.BACKGROUND, null, null)));
    int canvasWidth = Configurations.width * Configurations.CELL_SIZE;
    int canvasHeight = Configurations.height * Configurations.CELL_SIZE + 50;
    Scene scene = new Scene(root, canvasWidth, canvasHeight);
    stage.setScene(scene);
    stage.setTitle("Bacteria colony");
    stage.setResizable(false);
    stage.show();
  }

  private static void setConfigs(String[] args) {
    try {
      Configurations.setConfigurations(args);
    } catch (InvalidArgumentsException e) {
      showConfigWarning(e.getMessage());
    }
    Logic.init();
  }

  public static void initPane() {
    canvas = new Pane();
    canvas.setBackground(new Background(new BackgroundFill(Configurations.BACKGROUND, null, null)));
  }

  public static void initArea() {
    ObservableList<Node> canvasChildren = canvas.getChildren();

    List<Cell> cells = Field.getInstance().getCells();
    for (int i = 0; i < cells.size(); i++) {
      Circle circle = new Circle(Configurations.CELL_SIZE / 2);
      int x = ((i % Configurations.width) * Configurations.CELL_SIZE) + (int) circle.getRadius();
      int y = ((i / Configurations.width) * Configurations.CELL_SIZE) + (int) circle.getRadius();
      circle.fillProperty().bind(cells.get(i).colorProperty());
      int currentCellIndex = i;
      circle.setOnMouseClicked(mouseEvent -> {
        cells.get(currentCellIndex).toggle();
      });
      circle.setCenterX(x);
      circle.setCenterY(y);
      canvasChildren.add(circle);
    }
  }

  public static void showConfigWarning(String message) {
    Alert alert = new Alert(
        AlertType.WARNING,
        message + "\n" + Configurations.argumentsWarning,
        ButtonType.OK, ButtonType.CLOSE);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isEmpty() || result.get() == ButtonType.CLOSE) {
      exit();
    } else if (result.get() == ButtonType.OK) {
      System.out.println("Default settings");
    }
  }

  public static void main(String[] args) {
    arguments = args;
    launch();
  }

  private static void exit() {
    System.exit(0);
  }

}