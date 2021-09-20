package pain.t.fx_2021;

//Imports
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;


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
    static TabPane tabPane;
    
    @Override
    public void start(Stage primaryStage) throws UnknownHostException {
        
        
        //throw an error if user tries to save before using the filefinder dialogue
        //fileDest = "null";
        
        //initialize canvas and graphics context
       // Double imageWidth = 300.0;
       // Double imageHeight = 300.0;
        //canvas = new Canvas(imageWidth, imageHeight);
        //gc = canvas.getGraphicsContext2D();
        
        //initialize scrollpane
        //sp = new ScrollPane();
        //make sure there is no previous data
       // sp.getChildrenUnmodifiable().remove(canvas);
        //sp.setContent(canvas);
        //dont let scollpane get larger than canvas
        //sp.setMaxSize(imageWidth, imageHeight);
        //sp.autosize();
        
        
        tabPane = new TabPane();
         //Tab tab = new Tab();
        TabPain tab = new TabPain();
        tabPane.getTabs().add(tab);
        
       // tab.setContent(sp);
        
        
        //initialize toolBar and menuBar
        ToolBarPain toolBar2 = new ToolBarPain();
        toolBar2.setOrientation(Orientation.VERTICAL);
        //MenuPain mb = new MenuPain(primaryStage, gc, sp);
        MenuPain mb = new MenuPain(primaryStage);
        
        //initialize hbox
        hbox = new HBox();
        //make hbox fit canvas
        hbox.setHgrow(tabPane, Priority.ALWAYS);
        //hbox.setHgrow(sp, Priority.ALWAYS);
        hbox.setFillHeight(true);
        //sp holds canvas, arrange toolbar and canvas horizontally in hbox
        hbox.getChildren().addAll(tabPane, toolBar2);
        
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
        
        //keyboard command for scene
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCodeCombination e;
                e = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
                if (e.match(event)) {
                    
                        //save
                        TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
                        Canvas canvas = selectedTab.canvas;
                        File outFile = selectedTab.outFile;
                        //Canvas canvas = PainTFX_2021.canvas;
                        //File outFile = PainTFX_2021.outFile;
                
                        try {
                            WritableImage wImage = new WritableImage(
                            (int) canvas.getWidth(),
                            (int) canvas.getHeight());

                            canvas.snapshot(null, wImage);
                            System.out.print(outFile.toString());
                            //default saves as png, could modify this in future to take advantage of extensions?
                             ImageIO.write(SwingFXUtils.fromFXImage(wImage, null),
                            "png", outFile);
                        } catch (Exception ex) {
                            //error message dialog box
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Save Alert");
                            alert.setHeaderText("Save was Unsuccessful");
                             String s = "Try using save as first, no file name or directory has been set";
                            alert.setContentText(s);
                            alert.show();
                   
                        }

                }
            }
        });
        
        
        
        //keyboard command for scene
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCodeCombination e;
                e = new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN);
                if (e.match(event)) {
                    
                       //user selects a file with chosen types
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Image File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
                FileInputStream imageStream;
                
                //try to use file the user selects, functionality seems suspect/superfluous
                //this keeps memory of fileDest and outFile for future save functions
                //passes image data and image to canvas inti which adds it to UX
                try {
                    File file = fileChooser.showOpenDialog(primaryStage);
                    String fileDest = file.getPath();
                    
                    TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
                    selectedTab.fileDest = file.getPath();
                     selectedTab.outFile = file;
                    
                   // PainTFX_2021.fileDest = file.getPath();
                    //PainTFX_2021.outFile = file;
                    
                    imageStream = new FileInputStream(fileDest);
                    Image image = new Image(imageStream);
                    //iv1.setImage(image);
                    
                    double imageWidth = image.getWidth();
                    double imageHeight = image.getHeight();
                    
                    selectedTab.canvasInit(imageWidth, imageHeight, image);
                  //PainTFX_2021.canvasInit(imageWidth, imageHeight, image);
                   
                } catch (FileNotFoundException ex) {
                    
                   //NEED TO FIX THIS
                    Logger.getLogger(PainTFX_2021.class.getName()).log(Level.SEVERE, null, e);
                    ButtonType exitButtonType = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.getDialogPane().getButtonTypes().add(exitButtonType);
                    boolean disabled = false; // computed based on content of text fields, for example
                    alert.getDialogPane().lookupButton(exitButtonType).setDisable(disabled);
                    alert.setTitle("Error");
                    alert.setHeaderText("File Error");
                    alert.setContentText("File not Found");
                    
                }

                }
            }
        });
        
        
        
        
        
    
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    
    //basic scene function for key presses
        
    //method to intialize canvas, used by other classes when opening new images
    /*
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
    
    */
    
    
    
       
}