import static org.junit.jupiter.api.Assertions.*;

import application.Cell;
import application.Configurations;
import application.Field;
import application.Main;
import java.util.List;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MainTest {

  @Test
  @DisplayName("Shape's fill property binds with cell's color")
  public void bindShapeFillToCellColorPropertyTest() {
    Field.getInstance().initCells();
    List<Cell> cellList = Field.getInstance().getCells();
    for (int i = 0; i < cellList.size(); i++) {
      Cell cell = cellList.get(i);
      Circle circle = new Circle();
      Main.bindShapeFillToCellColorProperty(circle,i);
      assertSame(circle.fillProperty().get(),cell.colorProperty().get());
      cell.toggle();
      assertSame(circle.fillProperty().get(),cell.colorProperty().get());
      cell.toggle();
      assertSame(circle.fillProperty().get(),cell.colorProperty().get());
    }
  }

  @Test
  @DisplayName("Init area test")
  public void initAreaTest() {
    Field.getInstance().initCells();
    Main.initPane();
    Main.initArea();
    assertEquals(Configurations.width * Configurations.height,
        Main.canvas.getChildren().size());
  }

}
