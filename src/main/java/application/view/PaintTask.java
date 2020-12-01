package application.view;

public class PaintTask implements Runnable {

  private final Canvas canvas;

  public PaintTask(Canvas canvas) {
    this.canvas = canvas;
  }

  @Override
  public void run() {
    canvas.paint();
  }
}
