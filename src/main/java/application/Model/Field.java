package application.Model;

import application.Configurations;
import java.util.ArrayList;
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

  private ArrayList<Cell> areaList;

  private Field() {
  }

  public void initCells() {
    areaList = new ArrayList<>(Configurations.height * Configurations.width);
    for (int i = 0; i < Configurations.height * Configurations.width; i++) {
      areaList.add(new Cell());
    }
    setAllNeighbours();
  }

  private void setAllNeighbours() {
    areaList.forEach(cell -> cell.setNeighbours(getNeighbours(areaList.indexOf(cell))));
  }

  public List<Cell> getCells() {
    return areaList;
  }

  public void randomize() {

    Random random = new Random();
    areaList.forEach(cell -> {
      if (random.nextFloat() <= Configurations.RANDOM_CELLS) {
        cell.revive();
      } else {
        cell.kill();
      }
    });
  }

  private ArrayList<Cell> getNeighbours(int index) {

    ArrayList<Cell> result = new ArrayList<>();
    int size = areaList.size();

    boolean notTop = index >= Configurations.width;
    boolean notBottom = index < size - Configurations.width;
    boolean notLeft = index % Configurations.width != 0;
    boolean notRight = (index + 1) % Configurations.width != 0;

    if (notTop) {
      result.add(getTopNeighbour(index));
    }

    if (notBottom) {
      result.add(getBottomNeighbour(index));
    }

    if (notLeft) {
      result.add(getLeftNeighbour(index));
    }

    if (notRight) {
      result.add(getRightNeighbour(index));
    }

    if (notTop && notLeft) {
      result.add(getTopLeftNeighbour(index));
    }

    if (notTop && notRight) {
      result.add(getTopRightNeighbour(index));
    }

    if (notBottom && notLeft) {
      result.add(getBottomLeftNeighbour(index));
    }

    if (notBottom && notRight) {
      result.add(getBottomRightNeighbour(index));
    }
    return result;
  }

  private Cell getTopNeighbour(int index) {
    return areaList.get(index - Configurations.width);
  }

  private Cell getBottomNeighbour(int index) {
    return areaList.get(index + Configurations.width);
  }

  private Cell getLeftNeighbour(int index) {
    return areaList.get(index - 1);
  }

  private Cell getRightNeighbour(int index) {
    return areaList.get(index + 1);
  }

  private Cell getTopLeftNeighbour(int index) {
    return areaList.get(index - Configurations.width - 1);
  }

  private Cell getTopRightNeighbour(int index) {
    return areaList.get(index - Configurations.width + 1);
  }

  private Cell getBottomLeftNeighbour(int index) {
    return areaList.get(index + Configurations.width - 1);
  }

  private Cell getBottomRightNeighbour(int index) {
    return areaList.get(index + Configurations.width + 1);
  }

  private int countAliveNeighbours(Cell cell) {
    return (int) cell.getNeighbours()
        .stream()
        .filter(Cell::isAlive)
        .count();
  }

  private List<Cell> getAliveCells() {
    return areaList.stream()
        .filter(Cell::isAlive)
        .collect(Collectors.toList());
  }

  private List<Cell> getDeadCells() {
    return areaList.stream()
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

  public int numberOfCellsAlive() {
    return getAliveCells().size();
  }

}