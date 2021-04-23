package application.model;

import java.util.concurrent.Phaser;

public class LoopPhaser extends Phaser {

  private final Logic logic;

  public LoopPhaser(Logic logic) {
    this.logic = logic;
  }

  @Override
  protected boolean onAdvance(int phase, int registeredParties) {
    logic.barrierAction();
    return false;
  }
}
