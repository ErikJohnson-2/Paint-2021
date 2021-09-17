package pain.t.fx_2021;

//Imports
import java.io.File;
import java.net.UnknownHostException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 *
 * @author Erik
 */
public class PainTFX_2021 extends Application {
    
    //global variables, referenced by other classes
    //UX
    static ScrollPane sp;
    static ScrollPane windowSp;
    static VBox vbox;
    static HBox hbox;
    //Image variables
    static GraphicsContext gc;
    static Canvas canvas;
    //File variables
     static File outFile;
    static String fileDest;
    
    @Override
    public void start(Stage primaryStage) throws UnknownHostException {
        
        
        //throw an error if user tries to save before using the filefinder dialogue
        fileDest = "null";
        
        //initialize canvas and graphics context
        Double imageWidth = 300.0;
        Double imageHeight = 300.0;
        canvas = new Canvas(imageWidth, imageHeight);
        gc = canvas.getGraphicsContext2D();
        
        //initialize scrollpane
        sp = new ScrollPane();
        //make sure there is no previous data
        sp.getChildrenUnmodifiable().remove(canvas);
        sp.setContent(canvas);
        //dont let scollpane get larger than canvas
        sp.setMaxSize(imageWidth, imageHeight);
        sp.autosize();
      
        //initialize toolBar and menuBar
        ToolBarPain toolBar2 = new ToolBarPain();
        toolBar2.setOrientation(Orientation.VERTICAL);
        MenuPain mb = new MenuPain(primaryStage, gc, sp);

        //initialize hbox
        hbox = new HBox();
        //make hbox fit canvas
        hbox.setHgrow(sp, Priority.ALWAYS);
        hbox.setFillHeight(true);
        //sp holds canvas, arrange toolbar and canvas horizontally in hbox
        hbox.getChildren().addAll(sp, toolBar2);
        
        //initialize vbox
        vbox = new VBox();
        vbox.setPadding(new Insets(1, 5, 1, 1));
        //arrange menubar above canvas and toolbar in vbox
        vbox.getChildren().addAll(mb,hbox);
        //let vbox adjust to fit canvas
        vbox.setVgrow(hbox, Priority.ALWAYS);
        
    
        //conclude high level window initialization
        Scene scene = new Scene(vbox, 500, 500);
        primaryStage.setTitle("Pain(t)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    
    //method to intialize canvas, used by other classes when opening new images
    
    public static void canvasInit(Double imageW, Double imageH, Image img) {
        
        //remove prior data and initalize canvas
        sp.getChildrenUnmodifiable().remove(canvas);
        canvas = new Canvas(imageW, imageH);
        gc = canvas.getGraphicsContext2D();
        
        //place image data on graphics context
        gc.drawImage(img, 0, 0);

        //built in padding for testing
        sp.setMaxSize(imageW+20, imageH+20);
        sp.setContent(canvas);
    }
       
}