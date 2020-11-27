package application;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class Canvas extends Pane {

  public Canvas() {
    init();
    paint();
  }

  private void init() {
    int canvasWidth = Configurations.width * Configurations.CELL_SIZE + 4;
    int canvasHeight = Configurations.height * Configurations.CELL_SIZE + 4;
    prefHeightProperty().setValue(canvasHeight);
    prefWidthProperty().setValue(canvasWidth);
    maxHeightProperty().setValue(canvasHeight);
    maxWidthProperty().setValue(canvasWidth);
    minHeightProperty().setValue(canvasHeight);
    minWidthProperty().setValue(canvasWidth);

    setBackground(new Background(
        new BackgroundFill(Configurations.BACKGROUND, null, null)));
    setBorder(new Border(
        new BorderStroke(Configurations.BORDER,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            new BorderWidths(2.0))));
  }
  public void paint() {
    ObservableList<Node> canvasChildren = getChildren();
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

}
