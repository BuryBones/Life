package application.view;

import application.Configurations;
import application.controller.ModelController;
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

  private ModelController modelController;

  public Canvas(ModelController modelController) {
    this.modelController = modelController;
    init();
    paint();
  }

  private void init() {
    int canvasWidth = Configurations.get().getWidth()
        * Configurations.get().getCellSize() + 4;
    int canvasHeight = Configurations.get().getHeight()
        * Configurations.get().getCellSize() + 4;
    prefHeightProperty().setValue(canvasHeight);
    prefWidthProperty().setValue(canvasWidth);
    maxHeightProperty().setValue(canvasHeight);
    maxWidthProperty().setValue(canvasWidth);
    minHeightProperty().setValue(canvasHeight);
    minWidthProperty().setValue(canvasWidth);

    setBackground(new Background(
        new BackgroundFill(Configurations.get().getBackground(), null, null)));
    setBorder(new Border(
        new BorderStroke(Configurations.get().getBorder(),
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            new BorderWidths(2.0))));
  }

  public void paint() {
    // clear children list
    ObservableList<Node> canvasChildren = getChildren();
    canvasChildren.removeAll(canvasChildren);

    for (int i = 0; i < Configurations.get().getWidth() * Configurations.get().getHeight(); i++) {
      Circle circle = new Circle(Configurations.get().getCellSize() / 2.0f);
      circle.setFill(modelController.getCellColorProperty(i));
      int x = ((i % Configurations.get().getWidth()) * Configurations.get().getCellSize()) + (int) circle.getRadius();
      int y = ((i / Configurations.get().getWidth()) * Configurations.get().getCellSize()) + (int) circle.getRadius();
      int currentCellIndex = i;
      circle.setOnMouseClicked(mouseEvent -> {
        modelController.toggleCellByIndex(currentCellIndex);
        paint();
      });
      circle.setCenterX(2 + x);
      circle.setCenterY(2 + y);
      canvasChildren.add(circle);
    }
  }

}
