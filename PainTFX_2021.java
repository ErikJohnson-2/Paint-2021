package pain.t.fx_2021;

//Imports
import java.io.File;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Timer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;


/**
* <h1>Main class with layout and thread reference variables</h1>
* The PaintFX program implements a GUI with
* methods that allow the user to modify or
* create images.
*
* @author  Erik Johnson
* @version 1.4
* @since   2021-10-07
*/
public class PainTFX_2021 extends Application {
    
    //THIS CLASS CONTAINS STAGE AND TOPLEVEL LAYOUT + KEYBOARD SHORTCUTS
    
    //global variables, referenced by other classes
    
    //UX
    //static ScrollPane sp;
    //static ZoomableScrollPane sp;
    static ScrollPane windowSp;
    static VBox vbox;
    static HBox hbox;
    static HBox menuWithTimer;
    static TabPane tabPane;
    
    
    //Image variables
    static GraphicsContext gc;
    static Canvas canvas;
    
    //Logging variables
    //static Logger logger;
    Timer timer;
    static Boolean timerEnd = false;
    //File variables deprecated
    //static File outFile;
    //static String fileDest;
    static LoggingThread logThread;
 
    static OpenAndSaveMethods openSaveRunner;
    
    static boolean timerShown = true;
    
    static Integer STARTTIME = 15;
    private static Timeline timeline;
    private static Label timerLabel = new Label();
    private static Integer timeSeconds;
    
    /**
* Initializes PaintFX
* Method of the Application Class
* <p>
* Formats the logger and the GUI. Opens GUI for user.
* Adds high level eventHandlers for key shortcuts and window close.
*
* @param  primaryStage  This is created by default
     * @throws java.net.UnknownHostException

*/
    @Override
    public void start(Stage primaryStage) throws UnknownHostException {
        
        //Intialize instance of LoggingThread
        logThread = new LoggingThread();
        logThread.run();
        
        openSaveRunner = new OpenAndSaveMethods(primaryStage);
        //intialize tabPane and add its first tab
        tabPane = new TabPane();
        TabPain tab = new TabPain();
        tabPane.getTabs().add(tab);
        
        //initialize toolBar and menuBar
        ToolBarPain toolBar2 = new ToolBarPain();
        toolBar2.setOrientation(Orientation.VERTICAL);
        MenuPain mb = new MenuPain(primaryStage);
        
        //initialize hbox
        hbox = new HBox();
        //make hbox fit tabPane
        hbox.setHgrow(tabPane, Priority.ALWAYS);
        //hbox.setHgrow(sp, Priority.ALWAYS);
        hbox.setFillHeight(true);
        //sp (in tab) holds canvas, arrange toolbar and tabPane horizontally in hbox
        hbox.getChildren().addAll(tabPane, toolBar2);
        
        //timer icon
        
        String location = "File Didn't Load";
              try {
                  location = new File(
                          System.getProperty("user.dir") + File.separator + "Icons" + File.separator + "timer.png"
                  ).toURI().toURL().toExternalForm();
              } catch (MalformedURLException ex) {
                  //Logger.getLogger(MenuPain.class.getName()).log(Level.SEVERE, null, ex);
                  //better log please
              }
        Image img = new Image(location);
        ImageView viewTimer = new ImageView(img);
        viewTimer.setFitHeight(20);
        viewTimer.setPreserveRatio(true); 
        
        //initialize menuWithTimer
        menuWithTimer = new HBox();
        menuWithTimer.getChildren().addAll(mb,viewTimer, timerLabel);
        menuWithTimer.setHgrow(mb, Priority.ALWAYS);
        
        //initialize vbox
        vbox = new VBox();
        vbox.setPadding(new Insets(1, 5, 1, 1));
        //arrange menubar above canvas and toolbar in vbox
        vbox.getChildren().addAll(menuWithTimer,hbox);
        //let vbox adjust to fit canvas
        vbox.setVgrow(hbox, Priority.ALWAYS);
        
    
        //conclude high level window initialization
        Scene scene = new Scene(vbox, 500, 700);
        primaryStage.setTitle("Pain(t)");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        primaryStage.getScene().getWindow().addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, eventHandler);
         
         
       tabPane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> ov, Tab t, Tab t1) -> {
           System.out.println("Tab Selection changed");
           ToolBarPain.checkTabChanges();
       });
       
        //keyboard command for alt/f and cntl s 
        scene.setOnKeyPressed((KeyEvent event) -> {
            openSaveRunner.keyboardOpenFileAndSave(event);
        });
        
        

    }

    /**
     * Launches application, can be used with arguments from command line
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
    
 /**
* Event Handler for closing the application
* Checks for any living timer threads and kills them
*/
     static EventHandler<WindowEvent> eventHandler = new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
            System.out.println("Window close request ...");
        for (Tab var : tabPane.getTabs() ) { 
                         TabPain thisTab = (TabPain) var;
                         thisTab.timerEnd = true;
                         thisTab.timer.cancel();
                    }
        Platform.exit();
        }
             
        };
     
    /**
     * Run timer animation once
     * @param timerLength Integer with duration of timer
     */
    public static void timerStart(Integer timerLength){
        
        STARTTIME = timerLength;
        if (timeline != null) {
            timeline.stop();
        }
        
        //translate milliseconds to second for user display
        timeSeconds = STARTTIME / 1000;
 
        // update timerLabel
        timerLabel.setText(timeSeconds.toString());
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
                    timeSeconds--;
                    // update timerLabel
                    timerLabel.setText(
                            timeSeconds.toString());
                    if (timeSeconds <= 0) {
                        timeline.stop();
                    }
        } // KeyFrame event handler
        ));
        timeline.playFromStart();
     }
     
    /**
     * Hide timer animation from user, does not change any timer data
     * Only tries to remove timerLabel from parent layout the first time it is called
     */
    public static void timerHide(){
         if (timerShown != false) {
          menuWithTimer.getChildren().remove(timerLabel);
         }
         timerShown = false;
     }

    /**
     * Show timer animation to user, does not change any timer data
     * Only tries to add timerLabel to parent layout the first time it is called
     */
    public static void timerShow(){
         if (timerShown != true) {
          menuWithTimer.getChildren().add(timerLabel);
     }
     timerShown = true;
        
}
}