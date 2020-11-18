import java.util.List;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {

  static Field field;
  static Thread controlThread;
  static Thread lifeThread;
  static Thread deathThread;

  @Override
  public void start(Stage stage) throws Exception {
//    BorderPane root = new BorderPane();
    VBox root = new VBox();
    Pane canvas = new Pane();
    canvas.setBackground(new Background(new BackgroundFill(Configurations.BACKGROUND,null,null)));
    int canvasWidth = Configurations.width*Configurations.CELL_SIZE;
    int canvasHeight = Configurations.height*Configurations.CELL_SIZE +50;
    ObservableList<Node> children = canvas.getChildren();

    List<Cell> cells = field.getCellsAsList();
    for (int i = 0; i < cells.size(); i++) {
      Circle circle = new Circle(Configurations.CELL_SIZE /2);
      int x = (i%Configurations.width)*Configurations.CELL_SIZE;
      int y = (i/Configurations.width)*Configurations.CELL_SIZE;
      circle.fillProperty().bind(cells.get(i).color);
      circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
          // TODO: use 'i'
          Circle clicked = (Circle)mouseEvent.getSource();
          int x = (int) clicked.getCenterX()/Configurations.CELL_SIZE;
          int y = (int) clicked.getCenterY()/Configurations.CELL_SIZE * Configurations.width;
          cells.get(x+y).toggle();
        }
      });
      circle.setCenterX(x+circle.getRadius());
      circle.setCenterY(y+circle.getRadius());
//      System.out.println("X " + x + " <> Y " + y);
      children.add(circle);
    }
    ButtonBar buttonBar = new ButtonBar();
    Button start = new Button("Start");
    start.setOnAction(e -> Controller.getInstance().start());
    Button stop = new Button("Stop");
    stop.setOnAction(e -> Controller.getInstance().stop());
    Button clear = new Button("Clear");
    clear.setOnAction(e -> Controller.getInstance().clear());
    Button random = new Button("Random");
    random.setOnAction(e -> Controller.getInstance().random());
//    Background buttonBackground = new Background(new BackgroundFill(Paint.valueOf("#84d100"),null,null));
//    start.setBackground(buttonBackground);
//    stop.setBackground(buttonBackground);
//    clear.setBackground(buttonBackground);
//    random.setBackground(buttonBackground);
    buttonBar.getButtons().addAll(start,stop,clear,random);

    root.getChildren().addAll(canvas,buttonBar);
//    root.setBottom(buttonBar);
    root.setBackground(new Background(new BackgroundFill(Paint.valueOf("#4a4a4a"),null,null)));

    Scene scene = new Scene(root,canvasWidth,canvasHeight);
    stage.setScene(scene);
    stage.setTitle("Bacteria colony");
    stage.setResizable(false);
    stage.show();
  }

  public static void main(String[] args) {
    try {
      Configurations.setConfigurations(parseArguments(args));
    } catch (InvalidArgumentsException e) {
      // TODO: show message to user, say that default configs would be applied
    }
    field = new Field();
    launch(args);
  }

  public static void runLogic() {
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        System.out.println("Started!");
        LifeRunnable lifeRunnable = new LifeRunnable(field);
        DeathRunnable deathRunnable = new DeathRunnable(field);
        lifeThread = new Thread(lifeRunnable);
        deathThread = new Thread(deathRunnable);
//        int counter = 0;
        try {
          Thread.sleep(Configurations.PERIOD);
//          while (!Configurations.timeLimit || counter < Configurations.steps) {
//            List<Cell> toKill = field.prepareDeathList();
//            List<Cell> toRevive = field.prepareNewbornList();
//            toKill.forEach(Cell::kill);
//            toRevive.forEach(Cell::revive);
            lifeThread.start();
            deathThread.start();
//            counter++;
//            Thread.sleep(Configurations.PERIOD);
//          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };
    controlThread = new Thread(runnable);
    controlThread.setDaemon(true);
    controlThread.start();
  }
  public static void stopThreads() {
    controlThread.interrupt();
    lifeThread.interrupt();
    deathThread.interrupt();
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