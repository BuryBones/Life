package application;

import application.Controller.SetupController;
import application.View.Graphics;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    SetupController.getInstance().setupConfigurations();
    Graphics.getInstance().start(stage);
  }
}
