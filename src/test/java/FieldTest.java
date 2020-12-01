import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import application.Configurations;
import application.model.Cell;
import application.model.Field;
import application.model.Logic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FieldTest {

  static Field field;

  @BeforeEach
  public void init() {
    new Configurations();
    Logic logic = new Logic();
    field = logic.initField();
  }

  @Test
  @DisplayName("Initialized cells are not nulls and not alive")
  public void initCellsTest() {
    for (Cell cell : field.getCells()) {
      assertNotNull(cell);
      assertFalse(cell.isAlive());
    }
  }

  @ParameterizedTest
  @DisplayName("Computes neighbour constraints")
  @MethodSource("defineConstrainsTestParams")
  public void defineConstrainsTest(int index, int size, boolean[] expected) {
    boolean[] actual = field.defineConstraints(index,size);
    assertArrayEquals(expected,actual);
  }

  @Test
  @DisplayName("Gets top neighbour")
  public void getTopNeighbourTest() {
int cellIndex = Configurations.get().getWidth() + 1;
    assertEquals(field.getCells().get(cellIndex - Configurations.get().getWidth()),
        field.getTopNeighbour(cellIndex));
  }

  @Test
  @DisplayName("Gets bottom neighbour")
  public void getBottomNeighbourTest() {
    int cellIndex = 0;
    assertEquals(field.getCells().get(cellIndex + Configurations.get().getWidth()),
        field.getBottomNeighbour(cellIndex));
  }

  @Test
  @DisplayName("Gets left neighbour")
  public void getLeftNeighbourTest() {
    int cellIndex = 1;
    assertEquals(field.getCells().get(cellIndex - 1),
        field.getLeftNeighbour(cellIndex));
  }

  @Test
  @DisplayName("Gets right neighbour")
  public void getRightNeighbourTest() {
    int cellIndex = 0;
    assertEquals(field.getCells().get(cellIndex + 1),
        field.getRightNeighbour(cellIndex));
  }

  @Test
  @DisplayName("Gets top left neighbour")
  public void getTopLeftNeighbourTest() {
    int cellIndex = Configurations.get().getWidth() * Configurations.get().getHeight();
    assertEquals(field.getCells().get(cellIndex - Configurations.get().getWidth() - 1),
        field.getTopLeftNeighbour(cellIndex));
  }

  @Test
  @DisplayName("Gets top right neighbour")
  public void getTopRightNeighbourTest() {
    int cellIndex = Configurations.get().getWidth() * Configurations.get().getHeight() - Configurations.get().getWidth();
    assertEquals(field.getCells().get(cellIndex - Configurations.get().getWidth() + 1),
        field.getTopRightNeighbour(cellIndex));
  }

  @Test
  @DisplayName("Gets bottom left neighbour")
  public void getBottomLeftNeighbourTest() {
    int cellIndex = Configurations.get().getWidth() - 1;
    assertEquals(field.getCells().get(cellIndex + Configurations.get().getWidth() - 1),
        field.getBottomLeftNeighbour(cellIndex));
  }

  @Test
  @DisplayName("Gets bottom right neighbour")
  public void getBottomRightNeighbourTest() {
    int cellIndex = 0;
    assertEquals(field.getCells().get(cellIndex + Configurations.get().getWidth() + 1),
        field.getBottomRightNeighbour(cellIndex));
  }

  @ParameterizedTest
  @DisplayName("Gets cell's neighbours")
  @MethodSource("getNeighboursTestParams")
  public void getNeighboursTest(int focusIndex, int expectedAmount, int[] expectedIndexes) {
    List<Cell> cells = field.getCells();
    Cell[] actual = new Cell[expectedIndexes.length];
    for (int i = 0; i < actual.length; i++) {
      actual[i] = cells.get(expectedIndexes[i]);
    }
    // check if getNeighbours() returns correct number of neighbours
    assertEquals(actual.length,expectedAmount);
    // check if getNeighbours() contains all expected neighbours
    assertTrue(cells.get(focusIndex).getNeighbours().containsAll(Arrays.asList(actual)));
  }

  @Test
  @DisplayName("Counts a number of alive neighbours")
  public void countAliveNeighboursTest() {
    List<Cell> cells = field.getCells();
    Cell focusCell = cells.get(cells.size() / 2);
    Random random = new Random();
    int expected = 0;
    for (Cell neighbour: focusCell.getNeighbours()) {
      if (random.nextFloat() <= 0.5f) {
        neighbour.revive();
        expected++;
      }
    }
    assertEquals(expected,field.countAliveNeighbours(focusCell));
  }

  @Test
  @DisplayName("Counts a number of alive cells")
  public void getAliveCellsTest() {
    int counter = 0;
    Random random = new Random();
    for (Cell cell: field.getCells()) {
      if (random.nextFloat() <= Configurations.get().getRandomCells()) {
        cell.revive();
        counter++;
      }
    }
    // check if amount of alive cells is equal to expected
    assertEquals(counter,field.getAliveCells().size());
    // check if list of all cells contains every cell from alive cells' list
    for (Cell cell: field.getCells()) {
      if (cell.isAlive()) {
        assertTrue(field.getCells().contains(cell));
      }
    }
  }

  @Test
  @DisplayName("Counts a number of dead cells")
  public void getDeadCellsTest() {
    int counter = 0;
    Random random = new Random();
    for (Cell cell: field.getCells()) {
      if (random.nextFloat() <= Configurations.currentConfigs.getRandomCells()) {
        cell.revive();
      } else {
        counter++;
      }
    }
    // check if amount of dead cells is equal to expected
    assertEquals(counter,field.getDeadCells().size());
    // check if list of all cells contains every cell from dead cells' list
    for (Cell cell: field.getCells()) {
      if (!cell.isAlive()) {
        assertTrue(field.getCells().contains(cell));
      }
    }
  }

  @Test
  @DisplayName("Generates a list of cells to kill")
  public void prepareDeathListTest() {
    field.randomize();
    ArrayList<Cell> expected = new ArrayList<>();
    for (Cell cell: field.getCells()) {
      if (cell.isAlive()) {
        int neighboursAlive = field.countAliveNeighbours(cell);
        if (neighboursAlive > Configurations.get().getOvercrowd()
            || neighboursAlive < Configurations.get().getForlorn()) {
          expected.add(cell);
        }
      }
    }
    assertEquals(field.prepareDeathList().size(), expected.size());
    assertTrue(field.prepareDeathList().containsAll(expected));
  }

  @Test
  @DisplayName("Generates a list of cells to revive")
  public void prepareNewbornList() {
    field.randomize();
    ArrayList<Cell> expected = new ArrayList<>();
    for (Cell cell: field.getCells()) {
      if (!cell.isAlive()) {
        int neighboursAlive = field.countAliveNeighbours(cell);
        if (neighboursAlive == Configurations.get().getBreed()) {
          expected.add(cell);
        }
      }
    }
    assertEquals(field.prepareNewbornList().size(), expected.size());
    assertTrue(field.prepareNewbornList().containsAll(expected));
  }

  @Test
  @DisplayName("Counts number of alive cells")
  public void numberOfCellsAliveTest() {
    field.randomize();
    int expected = 0;
    for (Cell cell: field.getCells()) {
      if (cell.isAlive()) {
        expected++;
      }
    }
    assertEquals(expected,field.numberOfCellsAlive());
  }

  public static Stream<? extends Arguments> defineConstrainsTestParams() {
    int size = Configurations.get().getWidth() * Configurations.get().getHeight();
    int topLeftIndex = 0;
    int topRightIndex = Configurations.get().getWidth() - 1;
    int topIndex = 1;
    int bottomLeftIndex = Configurations.get().getWidth() * (Configurations.get().getHeight() - 1);
    int bottomRightIndex = size - 1;
    int bottomIndex = size - 2;
    int leftIndex = Configurations.get().getWidth();
    int rightIndex = Configurations.get().getWidth() * 2 - 1;
    int middleIndex = (int)(size / 2);
    return Stream.of(
        Arguments.of(topLeftIndex, size,
            new boolean[]{false,true,false,true}),  // top-left position
        Arguments.of(topIndex, size,
            new boolean[]{false,true,true,true}),   // top position
        Arguments.of(topRightIndex, size,
            new boolean[]{false,true,true,false}),  // top-right position
        Arguments.of(middleIndex, size,
            new boolean[]{true,true,true,true}),  // middle position
        Arguments.of(leftIndex, size,
            new boolean[]{true,true,false,true}), // left position
        Arguments.of(rightIndex, size,
            new boolean[]{true,true,true,false}), // right position
        Arguments.of(bottomLeftIndex, size,
            new boolean[]{true,false,false,true}),  // bottom-left position
        Arguments.of(bottomIndex, size,
            new boolean[]{true,false,true,true}),  // bottom position
        Arguments.of(bottomRightIndex, size,
            new boolean[]{true,false,true,false})   // bottom-right position
    );
  }

  public static Stream<? extends Arguments> getNeighboursTestParams() {
    int topLeftIndex = 0;
    int topRightIndex = Configurations.get().getWidth() - 1;
    int topIndex = 1;
    int bottomLeftIndex = Configurations.get().getWidth() * (Configurations.get().getHeight() - 1);
    int bottomRightIndex = Configurations.get().getWidth() * Configurations.get().getHeight() - 1;
    int bottomIndex = Configurations.get().getWidth() * Configurations.get().getHeight() - 2;
    int leftIndex = Configurations.get().getWidth();
    int rightIndex = Configurations.get().getWidth() * 2 - 1;
    int middleIndex = (int)(Configurations.get().getWidth() * Configurations.get().getHeight() / 2);
    return Stream.of(
      /*
      1 - focus cell index
      2 - neighbours number
      3 - neighbours expected indexes
       */
        Arguments.of(topLeftIndex, 3,
            new int[] {
                topLeftIndex + 1,
                topLeftIndex + Configurations.get().getWidth(),
                topLeftIndex + Configurations.get().getWidth() + 1
            }),
        Arguments.of(topRightIndex, 3,
            new int[] {
                topRightIndex - 1,
                topRightIndex + Configurations.get().getWidth(),
                topRightIndex + Configurations.get().getWidth() - 1
            }),
        Arguments.of(topIndex, 5,
            new int[] {
                topIndex - 1,
                topIndex + 1,
                topIndex + Configurations.get().getWidth(),
                topIndex + Configurations.get().getWidth() + 1,
                topIndex + Configurations.get().getWidth() - 1
            }),
        Arguments.of(bottomLeftIndex, 3,
            new int[] {
                bottomLeftIndex - Configurations.get().getWidth(),
                bottomLeftIndex - Configurations.get().getWidth() + 1,
                bottomLeftIndex + 1
            }),
        Arguments.of(bottomRightIndex, 3,
            new int[] {
                bottomRightIndex - Configurations.get().getWidth(),
                bottomRightIndex - Configurations.get().getWidth() - 1,
                bottomRightIndex - 1
            }),
        Arguments.of(bottomIndex, 5,
            new int[] {
                bottomIndex - 1,
                bottomIndex + 1,
                bottomIndex - Configurations.get().getWidth() - 1,
                bottomIndex - Configurations.get().getWidth(),
                bottomIndex - Configurations.get().getWidth() + 1
            }),
        Arguments.of(leftIndex, 5,
            new int[] {
                leftIndex - Configurations.get().getWidth(),
                leftIndex - Configurations.get().getWidth() + 1,
                leftIndex + Configurations.get().getWidth(),
                leftIndex + Configurations.get().getWidth() + 1,
                leftIndex + 1
            }),
        Arguments.of(rightIndex, 5,
            new int[] {
                rightIndex - Configurations.get().getWidth(),
                rightIndex - Configurations.get().getWidth() - 1,
                rightIndex + Configurations.get().getWidth(),
                rightIndex + Configurations.get().getWidth() - 1,
                rightIndex - 1
            }),
        Arguments.of(middleIndex, 8,
            new int[] {
                middleIndex - 1,
                middleIndex + 1,
                middleIndex - Configurations.get().getWidth(),
                middleIndex - Configurations.get().getWidth() + 1,
                middleIndex - Configurations.get().getWidth() - 1,
                middleIndex + Configurations.get().getWidth(),
                middleIndex + Configurations.get().getWidth() + 1,
                middleIndex + Configurations.get().getWidth() - 1,
            })
    );
  }
}
