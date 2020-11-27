package application.View;

public class PaintTask implements Runnable {

  private Canvas canvas;

  public PaintTask(Canvas canvas) {
    this.canvas = canvas;
  }

  @Override
  public void run() {
    canvas.paint();
  }
}