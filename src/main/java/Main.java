import java.util.List;
import java.util.concurrent.CyclicBarrier;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {

  static CounterTask counterTask = new CounterTask();
  public static CyclicBarrier barrier = new CyclicBarrier(2,counterTask);

  static Field field;
  static Thread controlThread;

  static LifeTask lifeTask;
  static DeathTask deathTask;
  static Thread lifeThread;
  static Thread deathThread;

  static Pane canvas;

  @Override
  public void start(Stage stage) throws Exception {
    VBox root = new VBox();
    canvas = new Pane();
    canvas.setBackground(new Background(new BackgroundFill(Configurations.BACKGROUND,null,null)));
    int canvasWidth = Configurations.width*Configurations.CELL_SIZE;
    int canvasHeight = Configurations.height*Configurations.CELL_SIZE +50;

    initArea();

    ButtonBar buttonBar = new ButtonBar();
    Button start = new Button("Start");
    start.setOnAction(e -> Controller.getInstance().start());
    Button stop = new Button("Stop");
    stop.setOnAction(e -> Controller.getInstance().stop());
    Button clear = new Button("Clear");
    clear.setOnAction(e -> Controller.getInstance().clear());
    Button random = new Button("Random");
    random.setOnAction(e -> Controller.getInstance().random());
    buttonBar.getButtons().addAll(start,stop,clear,random);

    root.getChildren().addAll(canvas,buttonBar);
    root.setBackground(new Background(new BackgroundFill(Paint.valueOf("#4a4a4a"),null,null)));

    Scene scene = new Scene(root,canvasWidth,canvasHeight);
    stage.setScene(scene);
    stage.setTitle("Bacteria colony");
    stage.setResizable(false);
    stage.show();
  }

  public static void initArea() {
    ObservableList<Node> children = canvas.getChildren();

    List<Cell> cells = field.getCellsAsList();
    for (int i = 0; i < cells.size(); i++) {
      Circle circle = new Circle(Configurations.CELL_SIZE /2);
      int x = (i%Configurations.width)*Configurations.CELL_SIZE;
      int y = (i/Configurations.width)*Configurations.CELL_SIZE;
      circle.fillProperty().bind(cells.get(i).color);
      int currentCellIndex = i;
      circle.setOnMouseClicked(mouseEvent -> {
        cells.get(currentCellIndex).toggle();
      });
      circle.setCenterX(x+circle.getRadius());
      circle.setCenterY(y+circle.getRadius());
      children.add(circle);
    }
  }

  public static void main(String[] args) {
    try {
      Configurations.setConfigurations(parseArguments(args));
    } catch (InvalidArgumentsException e) {
      // TODO: move to JAVAFX thread
      System.out.println("EXCEPTION!");
//      Alert alert = new Alert(AlertType.WARNING,Configurations.argumentsWarning, ButtonType.OK,ButtonType.CLOSE);
//      alert.show();
    }
    field = new Field();
    launch(args);
  }

  public static void runLogic() {
    // TODO: do something with all this statics
    counterTask.resetCounter();

    // TODO: why do I need control thread at all?!?

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        System.out.println("Started!");
        lifeTask = new LifeTask(field);
        deathTask = new DeathTask(field);
        lifeThread = new Thread(lifeTask);
        deathThread = new Thread(deathTask);

        lifeThread.start();
        deathThread.start();
      }
    };
    controlThread = new Thread(runnable);
    controlThread.setDaemon(true);
    controlThread.start();
  }
  public static void stopThreads() {
    controlThread.interrupt();

    lifeTask.stop();
    deathTask.stop();
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