import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

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
    new Configurations(4,4);
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

  @Test
  @DisplayName("getNeighbours() invokes defineConstraints()")
  public void getNeighboursInvokesDefineConstraintsTest() {
    Field spy = spy(Field.class);

    spy.getNeighbours(0);
    verify(spy).getBottomNeighbour(0);
    verify(spy).getRightNeighbour(0);
    verify(spy).getBottomRightNeighbour(0);

    spy.getNeighbours(12);
    verify(spy).getTopNeighbour(12);
    verify(spy).getTopRightNeighbour(12);
    verify(spy).getRightNeighbour(12);

    spy.getNeighbours(3);
    verify(spy).getLeftNeighbour(3);
    verify(spy).getBottomNeighbour(3);
    verify(spy).getBottomLeftNeighbour(3);

    spy.getNeighbours(15);
    verify(spy).getTopNeighbour(15);
    verify(spy).getTopLeftNeighbour(15);
    verify(spy).getLeftNeighbour(15);

    spy.getNeighbours(4);
    verify(spy).getTopNeighbour(4);
    verify(spy).getTopRightNeighbour(4);
    verify(spy).getRightNeighbour(4);
    verify(spy).getBottomRightNeighbour(4);
    verify(spy).getBottomNeighbour(4);

    spy.getNeighbours(1);
    verify(spy).getLeftNeighbour(1);
    verify(spy).getBottomLeftNeighbour(1);
    verify(spy).getBottomNeighbour(1);
    verify(spy).getRightNeighbour(1);
    verify(spy).getBottomRightNeighbour(1);

    spy.getNeighbours(7);
    verify(spy).getTopNeighbour(7);
    verify(spy).getTopLeftNeighbour(7);
    verify(spy).getLeftNeighbour(7);
    verify(spy).getBottomNeighbour(7);
    verify(spy).getBottomLeftNeighbour(7);

    spy.getNeighbours(13);
    verify(spy).getTopNeighbour(13);
    verify(spy).getTopLeftNeighbour(13);
    verify(spy).getTopRightNeighbour(13);
    verify(spy).getLeftNeighbour(13);
    verify(spy).getRightNeighbour(13);

    spy.getNeighbours(6);
    verify(spy).getTopNeighbour(6);
    verify(spy).getTopLeftNeighbour(6);
    verify(spy).getTopRightNeighbour(6);
    verify(spy).getLeftNeighbour(6);
    verify(spy).getRightNeighbour(6);
    verify(spy).getBottomNeighbour(6);
    verify(spy).getBottomLeftNeighbour(6);
    verify(spy).getBottomRightNeighbour(6);
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
      if (random.nextFloat() <= Configurations.get().getRandomCells()) {
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
    return Stream.of(
        Arguments.of(0, 16,
            new boolean[]{false,true,false,true}),  // top-left position
        Arguments.of(1, 16,
            new boolean[]{false,true,true,true}),   // top position
        Arguments.of(3, 16,
            new boolean[]{false,true,true,false}),  // top-right position
        Arguments.of(5, 16,
            new boolean[]{true,true,true,true}),  // middle position
        Arguments.of(8, 16,
            new boolean[]{true,true,false,true}), // left position
        Arguments.of(11, 16,
            new boolean[]{true,true,true,false}), // right position
        Arguments.of(12, 16,
            new boolean[]{true,false,false,true}),  // bottom-left position
        Arguments.of(14, 16,
            new boolean[]{true,false,true,true}),  // bottom position
        Arguments.of(15, 16,
            new boolean[]{true,false,true,false})   // bottom-right position
    );
  }

  public static Stream<? extends Arguments> getNeighboursTestParams() {
    return Stream.of(
      /*
      1 - focus cell index
      2 - neighbours number
      3 - neighbours expected indexes
       */
        Arguments.of(0, 3,
            new int[] {1,4,5}),
        Arguments.of(3, 3,
            new int[] {2,6,7}),
        Arguments.of(1, 5,
            new int[] {0,4,5,6,2}),
        Arguments.of(12, 3,
            new int[] {8,9,13}),
        Arguments.of(15, 3,
            new int[] {14,10,11}),
        Arguments.of(14, 5,
            new int[] {13,9,10,11,15}),
        Arguments.of(4, 5,
            new int[] {0,1,5,9,8}),
        Arguments.of(11, 5,
            new int[] {7,6,10,14,15}),
        Arguments.of(10, 8,
            new int[] {5,6,7,9,11,13,14,15})
    );
  }
}
