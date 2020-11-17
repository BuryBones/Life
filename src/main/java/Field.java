import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Field {

  Cell[][] field = new Cell[Configurations.height][Configurations.width];

  public Field() {
    initCells();
    randomize();
  }

  public List<Cell> getCellsAsList() {
    return Arrays.stream(field).flatMap(Arrays::stream).collect(Collectors.toList());
  }

  private void initCells() {
    // TEST IDEA: check if no elements of array are nulls, and every element is not alive

    for (int outer = 0; outer < field.length; outer++) {
      for (int inner = 0; inner < field[outer].length; inner++) {
        field[outer][inner] = new Cell(inner, outer, getNeighbours(inner,outer));
      }
    }
  }
  private void randomize() {
    Random random = new Random();
    float probability = 0.2f;
    int counter = 0;
    for (Cell[] outer: field) {
      for (Cell cell : outer) {
        if (random.nextFloat() <= probability) {
          cell.revive();
          counter++;
        }
      }
    }
    System.out.println(counter + " cells are alive.");
  }

  public ArrayList<Cell> getNeighbours(int x, int y) {

    ArrayList<Cell> result = new ArrayList<>();

//    int x = cell.getX();
//    int y = cell.getY();

    // PATHETIC.
    boolean notTop = y > 0;
    boolean notBottom = y < Configurations.height - 1;
    boolean notLeft = x > 0;
    boolean notRight = x < Configurations.width - 1;

    if (notTop) result.add(field[y - 1][x]);
    if (notBottom) result.add(field[y + 1][x]);
    if (notLeft) result.add(field[y][x - 1]);
    if (notRight) result.add(field[y][x + 1]);

    if (notTop && notLeft) result.add(field[y - 1][x - 1]);
    if (notTop && notRight) result.add(field[y - 1][x + 1]);

    if (notBottom && notLeft) result.add(field[y + 1][x - 1]);
    if (notBottom && notRight) result.add(field[y + 1][x + 1]);

    return result;
  }

//    boolean isAlive(int x, int y) {
//      return field[y][x].isAlive();
//  }

  public int countAliveNeighbours(Cell cell) {
    int result = (int) getNeighbours(cell.getX(),cell.getY()).stream().filter(Cell::isIsAliveProp).count();
//    System.out.printf("X: %d, Y: %d, alive neighs = %d %n", cell.getX(),cell.getY(),result);
    return result;
//    return (int) getNeighbours(cell.getX(),cell.getY()).stream().filter(Cell::isAlive).count();
  }
  public List<Cell> getAliveCells() {
    return Arrays.stream(field).flatMap(Arrays::stream).filter(Cell::isIsAliveProp).collect(Collectors.toList());
  }
  public List<Cell> getDeadCells() {
    return Arrays.stream(field).flatMap(Arrays::stream).filter(c -> !c.isIsAliveProp()).collect(Collectors.toList());
  }
  public void lifeCycle() {
    for (Cell cell: getDeadCells()) {
      if (countAliveNeighbours(cell) > 2 ) {
        cell.revive();
//        System.out.printf("Revived x: %d y: %d %n", cell.getX(),cell.getY());
      }
    }
  }
  public void deathCycle() {
    for (Cell cell: getAliveCells()) {
      int aliveNeighbours = countAliveNeighbours(cell);
      if (aliveNeighbours < 2 || aliveNeighbours > 4) {
        cell.kill();
//        System.out.printf("Killed x: %d y: %d %n", cell.getX(),cell.getY());
      }
    }
//    getAliveCells().stream()
//        .filter(cell -> countAliveNeighbours(cell) > 4 || countAliveNeighbours(cell) < 2)
//        .forEach(Cell::kill);
  }
  public List<Cell> prepareDeathList() {
    return getAliveCells().stream()
        .filter(cell -> countAliveNeighbours(cell) > 4 || countAliveNeighbours(cell) < 2)
        .collect(Collectors.toList());
  }
  public List<Cell> prepareNewbornList() {
    return getDeadCells().stream()
        .filter(cell -> countAliveNeighbours(cell) == 3)
        .collect(Collectors.toList());
  }
  public void printArray() {
    for (Cell[] outer: field) {
      System.out.println();
      for (Cell c: outer) {
        System.out.print(c);
      }
    }
    System.out.println("\n---------------------------------------------");
  }
}
