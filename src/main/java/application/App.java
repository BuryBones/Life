package application;

import application.controller.AlertsController;
import application.controller.ViewController;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    String setupMessage = Configurations.getCurrentConfigs().getSetupMessage();
    if (!setupMessage.isEmpty()) {
      if (Configurations.getCurrentConfigs().invalidArguments) {
        AlertsController.getInstance().getConfigAlert(setupMessage).pop();
      } else {
        AlertsController.getInstance().getInfoAlert(setupMessage).pop();
      }
    }
    ViewController.getInstance().startGraphics(stage);
  }
}
