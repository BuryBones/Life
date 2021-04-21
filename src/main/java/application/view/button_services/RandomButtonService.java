package application.view.button_services;

public class RandomButtonService extends AbstractButtonService {

  @Override
  public void apply() {
    modelController.random();
    graphics.triggerPaint();
  }
}
