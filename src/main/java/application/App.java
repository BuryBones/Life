package application;

import application.Controller.MainController;
import application.Controller.ViewController;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    MainController.getInstance().setupConfigurations();
    ViewController.getInstance().startGraphics(stage);
  }
}
