import static org.junit.jupiter.api.Assertions.*;

import application.Cell;
import application.Configurations;
import application.Field;
import application.Graphics;
import application.Main;
import java.util.List;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GraphicsTest {

  @Test
  @DisplayName("Paint method puts width * height cells to canvas")
  public void paintTest() {
    Field.getInstance().initCells();
    Graphics.getInstance().initPane();
    Graphics.getInstance().paint();
    assertEquals(Configurations.width * Configurations.height,
        Graphics.getInstance().getCanvas().getChildren().size());
  }

}
