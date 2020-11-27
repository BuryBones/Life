package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    Main.setConfigs();
    Graphics.getInstance().start(stage);
  }
}
