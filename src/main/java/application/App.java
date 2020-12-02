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
    String setupMessage = Configurations.get().getSetupMessage();
    if (!setupMessage.isEmpty()) {
      if (Configurations.get().isInvalidArguments()) {
        if (AlertsController.getInstance().getConfigAlert(setupMessage).popAndAskForExit()) {
          Main.exit();
        }
      } else {
        AlertsController.getInstance().getInfoAlert(setupMessage).pop();
      }
    }
    ModelController modelController = new ModelController();
    ViewController viewController = new ViewController(modelController);
    Logic logic = new Logic(viewController);
    Field field = logic.initField();
    modelController.setLogic(logic);
    modelController.setField(field);
    viewController.startGraphics(stage);
  }
}
