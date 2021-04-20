package application;

import application.controller.AlertsController;
import application.controller.ModelController;
import application.controller.ViewController;
import application.model.Logic;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

  private final Injector injector = Guice.createInjector(new BasicModule());

  @Inject
  private ModelController modelController = injector.getInstance(ModelController.class);

  @Inject
  private ViewController viewController = injector.getInstance(ViewController.class);

  @Inject
  private Logic logic = injector.getInstance(Logic.class);

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
    viewController.startGraphics(stage);
  }
}
