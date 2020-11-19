package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Field {

  private static Field instance = new Field();

  public static Field getInstance() {
    if (instance == null) {
      instance = new Field();
    }
    return instance;
  }

  Cell[][] area = new Cell[Configurations.height][Configurations.width];

  private Field() {
  }

  public void initCells() {
    // TEST IDEA: check if no elements of array are nulls, and every element is not alive

    for (int outer = 0; outer < area.length; outer++) {
      for (int inner = 0; inner < area[outer].length; inner++) {
        Cell focus = new Cell(inner, outer, getNeighbours(inner,outer));
        area[outer][inner] = focus;
      }
    }
  }

  public List<Cell> getCells() {
    return Arrays.stream(area).flatMap(Arrays::stream).collect(Collectors.toList());
  }

  public void randomize() {
    Random random = new Random();
    for (Cell[] outer: area) {
      for (Cell cell : outer) {
        if (random.nextFloat() <= Configurations.RANDOM_CELLS) {
          cell.revive();
        } else {
          cell.kill();
        }
      }
    }
  }

  private ArrayList<Cell> getNeighbours(int x, int y) {

    ArrayList<Cell> result = new ArrayList<>();

    // PATHETIC.
    boolean notTop = y > 0;
    boolean notBottom = y < Configurations.height - 1;
    boolean notLeft = x > 0;
    boolean notRight = x < Configurations.width - 1;

    if (notTop) result.add(area[y - 1][x]);
    if (notBottom) result.add(area[y + 1][x]);
    if (notLeft) result.add(area[y][x - 1]);
    if (notRight) result.add(area[y][x + 1]);

    if (notTop && notLeft) result.add(area[y - 1][x - 1]);
    if (notTop && notRight) result.add(area[y - 1][x + 1]);

    if (notBottom && notLeft) result.add(area[y + 1][x - 1]);
    if (notBottom && notRight) result.add(area[y + 1][x + 1]);

    return result;
  }

  private int countAliveNeighbours(Cell cell) {
    return (int) getNeighbours(cell.getX(),cell.getY()).stream().filter(Cell::isIsAliveProp).count();
  }

  private List<Cell> getAliveCells() {
    return Arrays.stream(area).flatMap(Arrays::stream).filter(Cell::isIsAliveProp).collect(Collectors.toList());
  }

  private List<Cell> getDeadCells() {
    return Arrays.stream(area).flatMap(Arrays::stream).filter(c -> !c.isIsAliveProp()).collect(Collectors.toList());
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

}
