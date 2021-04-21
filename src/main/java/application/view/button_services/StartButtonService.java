package application.view.button_services;

public class StartButtonService extends AbstractButtonService {

  @Override
  public void apply() {
    modelController.start();
  }
}
