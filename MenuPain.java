package pain.t.fx_2021;

//imports
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import static pain.t.fx_2021.PainTFX_2021.tabPane;

public class MenuPain extends MenuBar{
    
    
    public MenuPain(Stage primaryStage) {
    
    //initialize Menus for Menubar
    Menu file = new Menu("File");
    Menu exit = new Menu("Close Pain(t)");
    Menu help = new Menu("Help");
    
    // intialize menuitems for Menus
    MenuItem open1 = new MenuItem("Open File In Current Tab");
    MenuItem open2 = new MenuItem("Open File In New Tab");
    MenuItem open3 = new MenuItem("Open Blank Canvas In New Tab");
    MenuItem save1 = new MenuItem("Save");
    MenuItem save2 = new MenuItem("Save As");
    MenuItem exit1 = new MenuItem("Are you sure?");
    MenuItem help1 = new MenuItem("Help");
    MenuItem about1 = new MenuItem("About");
    MenuItem support1 = new MenuItem("Online Support");
       
    // add menu items to file menu
    file.getItems().add(open1);
    file.getItems().add(open2);
    file.getItems().add(open3);
    file.getItems().add(save1);
    file.getItems().add(save2);
    
    //add menu items to help menu
    help.getItems().add(help1);
    help.getItems().add(support1);
    help.getItems().add(about1);
    
    //add menu items to exit menu
    exit.getItems().add(exit1);
    
    // add menus to this menubar object
    this.getMenus().add(file);
    this.getMenus().add(help);
    this.getMenus().add(exit);
   
   //eventHandler for close menuItem
        exit1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Platform.exit();
            }
        });
        
    //eventHandler for fileOpen menuItem    
        open1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
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
                    //PainTFX_2021.fileDest = file.getPath();
                   // PainTFX_2021.outFile = file;
                    
                    imageStream = new FileInputStream(fileDest);
                    Image image = new Image(imageStream);
                    //iv1.setImage(image);
                    
                    double imageWidth = image.getWidth();
                    double imageHeight = image.getHeight();
                    
                    
                    selectedTab.canvasInit(imageWidth, imageHeight, image);
                  //PainTFX_2021.canvasInit(imageWidth, imageHeight, image);
                   
                } catch (FileNotFoundException ex) {
                    //NEW ERROR CODE NEEDED
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
        });
        
         //eventHandler for fileOpen in new tab menuItem    
        open2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
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
                    //PainTFX_2021.fileDest = file.getPath();
                    //PainTFX_2021.outFile = file;
                    
                    imageStream = new FileInputStream(fileDest);
                    Image image = new Image(imageStream);
                    //iv1.setImage(image);
                    
                    double imageWidth = image.getWidth();
                    double imageHeight = image.getHeight();
                    
                  //PainTFX_2021.canvasInit(imageWidth, imageHeight, image);
                  TabPain tab = new TabPain();
                  
                  tab.canvasInit(imageWidth, imageHeight, image);
                  tabPane.getTabs().add(tab);
                   
                } catch (FileNotFoundException ex) {
                    //new error
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
        });
        
        //eH for open3 "Open Blank Canvas In New Tab");
        open3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                  TabPain tab = new TabPain();
                  tabPane.getTabs().add(tab);
                   
               
            }
        });
        
        
        
        
        //eH for file/saveAs, creates new scene and adds menubar, sets fileDest,
        //takes snapshot of canvas and saves it to fileDest
        //closes by changing scene and moving menubar
        save2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
                Canvas canvas = selectedTab.canvas;
                
                //Canvas canvas = PainTFX_2021.canvas;
                // user can type file name and selct path
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Image");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
                File outFile = fileChooser.showSaveDialog(primaryStage);
                //intialize new Writable image and transer canvas data
                WritableImage wImage = new WritableImage(
                        (int) canvas.getWidth(),
                        (int) canvas.getHeight());
                canvas.snapshot(null, wImage);
                // Write wImage to file system as an image
                if (outFile != null) {
                    //remember valid files for future save functionality
                    selectedTab.outFile = outFile;
                    selectedTab.fileDest = outFile.getName();
                    //PainTFX_2021.outFile = outFile;
                    //PainTFX_2021.fileDest = outFile.getName();
                    //get file data from user type
                    String fileDest = outFile.getName();
                    String extension = fileDest.substring(1 + fileDest.lastIndexOf(".")).toLowerCase();
                    //try to save file in memory, print message if failed
                    // ####### THIS NEEDS A BETTER ERROR MESSAGE PLEASE
                        try {
                            ImageIO.write(SwingFXUtils.fromFXImage(wImage,
                                    null), extension, outFile);
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        } 
                }
            }
        });
        
        
        
        //eH for file/save, uses same method as file/saveAs with fileDest already set by open or saveAs
        //should have an error message that states if a file is unsaved
        save1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
                Canvas canvas = selectedTab.canvas;
                //Canvas canvas = PainTFX_2021.canvas;
                File outFile = selectedTab.outFile;
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
            //}
            }
        });

        //##CUSTOMIZE THESE FURTHER?
        //eH for About dialog
        about1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("About");
                alert.setHeaderText("Information Alert");
                String s = "Product Version: Pain(t)FX 2.0.0\n"
                        + "Java JDK 8\n"
                        + "Compiled on Netbeans IDE 12.0\n"
                        + "\n";
                alert.setContentText(s);
                alert.show();
            }
        });
        //eH for OnlineDocs&Support dialog
        //####Release notes?
        support1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Online Docs and Support");
                alert.setHeaderText("Information Alert");
                String s = "Youtube Demo: https://youtu.be/lQRhxPnVNko \n "
                        + "Github Code: https://github.com/ErikJohnson-2/Paint-2021 \n"
                        + "Oracle Documentation: https://docs.oracle.com/ \n"
                        + "Email: erik.johnson2@valpo.edu \n";
                alert.setContentText(s);
                alert.show();
            }
        });
        //eH for UsingPaint dialog
        help1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Using Paint(t)");
                alert.setHeaderText("Information Alert");
                String s = "Open and Save images in File Menu. Modify image with tools in toolbar. "
                        + "Change the line length with the slider and the line color with the Color Chooser."
                        + "";
                alert.setContentText(s);
                alert.show();
            }
        });
        

}
}
