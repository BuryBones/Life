package application.view.button_services;

public class ClearButtonService extends AbstractButtonService {

  @Override
  public void apply() {
    modelController.clear();
    graphics.triggerPaint();
  }
}
