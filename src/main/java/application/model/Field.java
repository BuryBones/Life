package application.model;

import application.Configurations;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Field {

  private ArrayList<Cell> areaList;

  public Field() {
    initCells();
  }

  public void initCells() {
    areaList = new ArrayList<>(Configurations.get().getHeight()
        * Configurations.get().getWidth());
    for (int i = 0; i < Configurations.get().getHeight()
        * Configurations.get().getWidth(); i++) {
      areaList.add(new Cell());
    }
    setAllNeighbours();
  }

  public void setAllNeighbours() {
    areaList.forEach(cell -> cell.setNeighbours(getNeighbours(areaList.indexOf(cell))));
  }

  public List<Cell> getCells() {
    return areaList;
  }

  public void randomize() {

    Random random = new Random();
    areaList.forEach(cell -> {
      if (random.nextFloat() <= Configurations.get().getRandomCells()) {
        cell.revive();
      } else {
        cell.kill();
      }
    });
  }

  public ArrayList<Cell> getNeighbours(int index) {

    ArrayList<Cell> result = new ArrayList<>();

    boolean[] constraints = defineConstraints(index,areaList.size());
    boolean notTop = constraints[0];
    boolean notBottom = constraints[1];
    boolean notLeft = constraints[2];
    boolean notRight = constraints[3];

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

  public boolean[] defineConstraints(int index, int size) {
    boolean[] result = new boolean[4];
    result[0] = index >= Configurations.get().getWidth();             // notTop
    result[1] = index < size - Configurations.get().getWidth();       // notBottom
    result[2] = index % Configurations.get().getWidth() != 0;         // notLeft
    result[3] = (index + 1) % Configurations.get().getWidth() != 0;   // notRight
    return result;
  }

  public Cell getTopNeighbour(int index) {
    return areaList.get(index - Configurations.get().getWidth());
  }

  public Cell getBottomNeighbour(int index) {
    return areaList.get(index + Configurations.get().getWidth());
  }

  public Cell getLeftNeighbour(int index) {
    return areaList.get(index - 1);
  }

  public Cell getRightNeighbour(int index) {
    return areaList.get(index + 1);
  }

  public Cell getTopLeftNeighbour(int index) {
    return areaList.get(index - Configurations.get().getWidth() - 1);
  }

  public Cell getTopRightNeighbour(int index) {
    return areaList.get(index - Configurations.get().getWidth() + 1);
  }

  public Cell getBottomLeftNeighbour(int index) {
    return areaList.get(index + Configurations.get().getWidth() - 1);
  }

  public Cell getBottomRightNeighbour(int index) {
    return areaList.get(index + Configurations.get().getWidth() + 1);
  }

  public int countAliveNeighbours(Cell cell) {
    return (int) cell.getNeighbours()
        .stream()
        .filter(Cell::isAlive)
        .count();
  }

  public List<Cell> getAliveCells() {
    return areaList.stream()
        .filter(Cell::isAlive)
        .collect(Collectors.toList());
  }

  public List<Cell> getDeadCells() {
    return areaList.stream()
        .filter(c -> !c.isAlive())
        .collect(Collectors.toList());
  }

  public List<Cell> prepareDeathList() {
    return getAliveCells().stream()
        .filter(cell -> countAliveNeighbours(cell) > Configurations.get().getOvercrowd()
            || countAliveNeighbours(cell) < Configurations.get().getForlorn())
        .collect(Collectors.toList());
  }

  public List<Cell> prepareNewbornList() {
    return getDeadCells().stream()
        .filter(cell -> countAliveNeighbours(cell) == Configurations.get().getBreed())
        .collect(Collectors.toList());
  }

  public int numberOfCellsAlive() {
    return getAliveCells().size();
  }

}
