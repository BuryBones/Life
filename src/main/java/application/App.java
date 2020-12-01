package application;

import application.controller.AlertsController;
import application.controller.ModelController;
import application.controller.ViewController;
import application.model.Field;
import application.model.Logic;
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
    Logic logic = new Logic();
    Field field = logic.initField();
    ModelController.getInstance().setLogic(logic);
    ModelController.getInstance().setField(field);
    ViewController.getInstance().startGraphics(stage);
  }
}
