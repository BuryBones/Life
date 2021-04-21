package application.view.button_services;

public class StopButtonService extends AbstractButtonService {

  @Override
  public void apply() {
    modelController.stop();
  }
}
