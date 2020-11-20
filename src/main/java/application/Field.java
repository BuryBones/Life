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

  private final Cell[][] area = new Cell[Configurations.height][Configurations.width];

  private Field() {
  }

  public void initCells() {
    for (int outer = 0; outer < area.length; outer++) {
      for (int inner = 0; inner < area[outer].length; inner++) {
        Cell focus = new Cell(inner, outer);
        area[outer][inner] = focus;
      }
    }
    setAllNeighbours();
  }

  private void setAllNeighbours() {
    for (int outer = 0; outer < area.length; outer++) {
      for (int inner = 0; inner < area[outer].length; inner++) {
        area[outer][inner].setNeighbours(getNeighbours(inner,outer));
      }
    }
  }

  public List<Cell> getCells() {
    return Arrays.stream(area)
        .flatMap(Arrays::stream)
        .collect(Collectors.toList());
  }

  // TODO: refactor
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

    boolean notTop = y > 0;
    boolean notBottom = y < Configurations.height - 1;
    boolean notLeft = x > 0;
    boolean notRight = x < Configurations.width - 1;

    // TODO: move to methods
    if (notTop) {
      result.add(area[y - 1][x]);
    }

    if (notBottom) {
      result.add(area[y + 1][x]);
    }

    if (notLeft) {
      result.add(area[y][x - 1]);
    }

    if (notRight) {
      result.add(area[y][x + 1]);
    }

    if (notTop && notLeft) {
      result.add(area[y - 1][x - 1]);
    }

    if (notTop && notRight) {
      result.add(area[y - 1][x + 1]);
    }

    if (notBottom && notLeft) {
      result.add(area[y + 1][x - 1]);
    }

    if (notBottom && notRight) {
      result.add(area[y + 1][x + 1]);
    }
    return result;
  }

  private int countAliveNeighbours(Cell cell) {
    return (int) cell.getNeighbours()
        .stream()
        .filter(Cell::isAlive)
        .count();
  }

  private List<Cell> getAliveCells() {
    return Arrays.stream(area)
        .flatMap(Arrays::stream)
        .filter(Cell::isAlive)
        .collect(Collectors.toList());
  }

  private List<Cell> getDeadCells() {
    return Arrays.stream(area)
        .flatMap(Arrays::stream)
        .filter(c -> !c.isAlive())
        .collect(Collectors.toList());
  }

  public List<Cell> prepareDeathList() {
    return getAliveCells().stream()
        .filter(cell -> countAliveNeighbours(cell) > Configurations.OVERCROWD ||
                        countAliveNeighbours(cell) < Configurations.FORLORN)
        .collect(Collectors.toList());
  }

  public List<Cell> prepareNewbornList() {
    return getDeadCells().stream()
        .filter(cell -> countAliveNeighbours(cell) == Configurations.BREED)
        .collect(Collectors.toList());
  }

}
