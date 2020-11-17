import java.util.List;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {

  static Field field;

  @Override
  public void start(Stage stage) throws Exception {
    BorderPane root = new BorderPane();
    Pane canvas = new Pane();
    canvas.setBackground(new Background(new BackgroundFill(Paint.valueOf("#4a4a4a"),null,null)));
    int canvasWidth = Configurations.width*Configurations.pxs;
    int canvasHeight = Configurations.height*Configurations.pxs+50;
    ObservableList<Node> children = canvas.getChildren();

    List<Cell> cells = field.getCellsAsList();
    for (int i = 0; i < cells.size(); i++) {
      Circle circle = new Circle(Configurations.pxs/2);
      int x = (i%Configurations.width)*Configurations.pxs;
      int y = (i/Configurations.width)*Configurations.pxs;
      circle.fillProperty().bind(cells.get(i).color);
      circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
          Circle clicked = (Circle)mouseEvent.getSource();
          int x = (int) clicked.getCenterX()/Configurations.pxs;
          int y = (int) clicked.getCenterY()/Configurations.pxs*Configurations.width;
          cells.get(x+y).toggle();
        }
      });
      circle.setCenterX(x+circle.getRadius());
      circle.setCenterY(y+circle.getRadius());
//      System.out.println("X " + x + " <> Y " + y);
      children.add(circle);
    }
    ButtonBar buttonBar = new ButtonBar();
    buttonBar.setBackground(new Background(new BackgroundFill(Paint.valueOf("#4a4a4a"),null,null)));
    Button start = new Button("Start");
    Button stop = new Button("Stop");
    Button clear = new Button("Clear");
    Button random = new Button("Random");
//    Background buttonBackground = new Background(new BackgroundFill(Paint.valueOf("#84d100"),null,null));
//    start.setBackground(buttonBackground);
//    stop.setBackground(buttonBackground);
//    clear.setBackground(buttonBackground);
//    random.setBackground(buttonBackground);
    buttonBar.getButtons().addAll(start,stop,clear,random);
    buttonBar.setTranslateX(-70);
    buttonBar.setTranslateY(-10);

    root.setTop(canvas);
    root.setBottom(buttonBar);
    root.setBackground(new Background(new BackgroundFill(Paint.valueOf("#4a4a4a"),null,null)));

    Scene scene = new Scene(root,canvasWidth,canvasHeight);
    stage.setScene(scene);
    stage.setTitle("Bacteria colony");
    stage.setResizable(false);
    stage.show();
  }


  public static void main(String[] args) {
    System.out.println("Launching...");
    try {
      Configurations.setConfigurations(parseArguments(args));
    } catch (InvalidArgumentsException e) {
      // TODO: show message to user, say that default configs would be applied
    }
    field = new Field();
    start();
    launch(args);
  }

  public static void start() {
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        System.out.println("Started!");
        while (true) {
          List<Cell> toKill = field.prepareDeathList();
          List<Cell> toRevive = field.prepareNewbornList();
          toKill.forEach(Cell::kill);
          toRevive.forEach(Cell::revive);
          try {
            Thread.sleep(Configurations.PERIOD);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    };
    Thread thread = new Thread(runnable);
    thread.setDaemon(true);
    thread.start();
  }
  private static int[] parseArguments(String[] args) throws InvalidArgumentsException {
    int expectedArguments = 3;  // magic constant
    int[] result = new int[expectedArguments];
    if (args.length != expectedArguments) {
      throw new InvalidArgumentsException("Found " + args.length + " arguments, expected " + expectedArguments);
    } else {
      for (int i = 0; i < args.length;i ++) {
        try {
          int argument = Integer.parseInt(args[i]);
          if (argument < 0) {
            throw new InvalidArgumentsException("Argument must not be less than zero!");
          }
          result[i] = argument;
        } catch (NumberFormatException nfe) {
          throw new InvalidArgumentsException("Numbers expected!",nfe);
        }
      }
    }
    return result;
  }
}