package application;

public class PaintTask implements Runnable {

  @Override
  public void run() {
    Graphics.getInstance().paint();
  }
}
