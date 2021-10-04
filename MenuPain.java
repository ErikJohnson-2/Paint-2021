package pain.t.fx_2021;

//imports
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import javafx.stage.Stage;
import static pain.t.fx_2021.PainTFX_2021.tabPane;

public class MenuPain extends MenuBar{
    
    //THIS CLASS CONTAINS CODE FOR CREATING A MENU AND EVENT HANDLERS FOR ALL MENU ITEMS
    //public static final String APP_ICON = "http://icons.iconarchive.com/icons/deleket/soft-scraps/128/Zoom-icon.png";
  public static final String ZOOM_RESET_ICON = "http://icons.iconarchive.com/icons/deleket/soft-scraps/24/Zoom-icon.png";
  public static final String ZOOM_OUT_ICON = "http://icons.iconarchive.com/icons/deleket/soft-scraps/24/Zoom-Out-icon.png";
  public static final String ZOOM_IN_ICON = "http://icons.iconarchive.com/icons/deleket/soft-scraps/24/Zoom-In-icon.png";
 // public static final String CLOSE_ICON = "http://icons.iconarchive.com/icons/deleket/soft-scraps/24/Button-Close-icon.png";
    
  
  
  
  
    public MenuPain(Stage primaryStage) {
    
    //initialize Menus for Menubar
    Menu file = new Menu("File");
    Menu image = new Menu("Image");
    Menu exit = new Menu("Close Pain(t)");
    Menu help = new Menu("Help");
    Menu undoRedo = new Menu("Undo/Redo");
   
    
    // intialize menuitems for Menus
    MenuItem open1 = new MenuItem("Open File In Current Tab");
    MenuItem open2 = new MenuItem("Open File In New Tab");
    MenuItem open3 = new MenuItem("Open Blank Canvas In New Tab");
    MenuItem open4 = new MenuItem("Open Public Domain Painting In New Tab");
    MenuItem save1 = new MenuItem("Save");
    MenuItem save2 = new MenuItem("Save As");
    
    MenuItem redoMenuItem = new MenuItem("Redo");
    MenuItem undoMenuItem = new MenuItem("Undo");
    
    MenuItem resize1 = new MenuItem("Resize Image");
    MenuItem zoomInMenuItem = new MenuItem("Zoom _In");
    MenuItem zoomOutMenuItem = new MenuItem("Zoom _Out");
    MenuItem zoomResetMenuItem = new MenuItem("Zoom _Reset");
    
    MenuItem exit1 = new MenuItem("Are you sure?");
    
    MenuItem help1 = new MenuItem("Help");
    MenuItem about1 = new MenuItem("About");
    MenuItem support1 = new MenuItem("Online Support");
    MenuItem release1 = new MenuItem("Release Notes");
       
    // add menu items to file menu
    file.getItems().add(open1);
    file.getItems().add(open2);
    file.getItems().add(open3);
     file.getItems().add(open4);
    file.getItems().add(save1);
    file.getItems().add(save2);
    
    // menu items to undoRedo
    undoRedo.getItems().add(redoMenuItem);
    undoRedo.getItems().add(undoMenuItem);
    
    //add menu item to image menu
    image.getItems().add(resize1);
    image.getItems().add(zoomInMenuItem);
    image.getItems().add(zoomOutMenuItem);
    image.getItems().add(zoomResetMenuItem);
    
    //add menu items to help menu
    help.getItems().add(help1);
    help.getItems().add(support1);
    help.getItems().add(about1);
    help.getItems().add(release1);
    
    //add menu items to exit menu
    exit.getItems().add(exit1);
    
    // add menus to this menubar object
    this.getMenus().add(file);
    this.getMenus().add(image);
    this.getMenus().add(undoRedo);
    this.getMenus().add(help);
    this.getMenus().add(exit);
    
    //EVENT HANDLERS BELOW
    
    
    //eH for Undo, push selected tab's canvas to redo and pop from undo
    undoMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
          TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
          if (selectedTab.undoRedoData.undoStack.isEmpty() != true){
                SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);         
            WritableImage image = selectedTab.canvas.snapshot(params, null);
              selectedTab.undoRedoData.pushRedo(image);
               selectedTab.redoUndoCanvasInit(selectedTab.undoRedoData.popUndo());
               System.out.print("undo");
          } else {
              System.out.print("undo_failed");
          }
      }
    });
     //eH for redo, push selected tab's canvas to undo and pop from redo
     redoMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
          TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
          if (selectedTab.undoRedoData.redoStack.isEmpty() != true) {
              SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);         
            WritableImage image = selectedTab.canvas.snapshot(params, null);
              selectedTab.undoRedoData.pushUndo(image);
              selectedTab.redoUndoCanvasInit(selectedTab.undoRedoData.popRedo());
              System.out.print("redo");
          } else {
              System.out.print("redo_failed");
          }
      }
    });
    
    
    //key shortcut for zoomReset
    zoomResetMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.ESCAPE));
    //icon for zoomReset
    zoomResetMenuItem.setGraphic(new ImageView(new Image(ZOOM_RESET_ICON)));
     //eH for zoomReset, grabs selectedTab and sets a scale on previewCanvcas
    zoomResetMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
          TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
          selectedTab.previewCanvas.setScaleX(1);
          selectedTab.previewCanvas.setScaleY(1);
        //group.setScaleX(1);
        //group.setScaleY(1);
      }
    });
    
     //key shortcut for zoomIn
    zoomInMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.I));
    //icon for zoomIn
    zoomInMenuItem.setGraphic(new ImageView(new Image(ZOOM_IN_ICON)));
     //eH for zoomIn, grabs selectedTab and sets a scale on previewCanvcas
    zoomInMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
          TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
          selectedTab.previewCanvas.setScaleX(selectedTab.previewCanvas.getScaleX() * 1.5);
          selectedTab.previewCanvas.setScaleY(selectedTab.previewCanvas.getScaleY() * 1.5);
        //group.setScaleX(group.getScaleX() * 1.5);
        //group.setScaleY(group.getScaleY() * 1.5);
      }
    });
   
    //key shortcut for zoomOut
    zoomOutMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O));
    //icon for zoomOut
    zoomOutMenuItem.setGraphic(new ImageView(new Image(ZOOM_OUT_ICON)));
    
    
    //eH for zoomOut, grabs selectedTab and sets a scale on previewCanvcas
    zoomOutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
           TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
          selectedTab.previewCanvas.setScaleX(selectedTab.previewCanvas.getScaleX() * 1 / 1.5);
          selectedTab.previewCanvas.setScaleY(selectedTab.previewCanvas.getScaleY() * 1 / 1.5);
          //group.setScaleX(group.getScaleX() * 1 / 1.5);
          //group.setScaleY(group.getScaleY() * 1 / 1.5);
      }
    });
    //test
    
    
    
    
    
    
    
 
   //eventHandler for close menuItem
        exit1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                //this boolean variable is true until the following
                //code fings a tab in tabPane that has been set to notClosable
                //this could have an indicator?
                boolean tabsClosable = true;

                for (Tab var : tabPane.getTabs()) { 
                    System.out.print("h");
                    var.setClosable(true);
                    if (var.isClosable() == true) {
                    } else {
                        tabsClosable = false;
                    }
                }
                
                //if changes were logged in tabs then make user confirm in popup
                if (tabsClosable == true)  
                {
                    //popup dialog that asks user to select cancel or close
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Data Loss Alert!");
                    alert.setHeaderText("Looks like you are attempting to close without saving!" +
                            "\n" + "Are you sure you want to do that?");
                    alert.setContentText("Choose your option.");

                    ButtonType buttonTypeOne = new ButtonType("Eh Just Close");
                    ButtonType buttonTypeTwo = new ButtonType("Ugh. Close");
                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

                    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeOne){
                    // ... user chose "Eh Just Close"
                    Platform.exit();
                    } else if (result.get() == buttonTypeTwo) {
                     // user chose "Ugh. Close"
                     Platform.exit();
                    } else {
                     // user chose "Cancel" or closed the dialog
                    }
                //the tabs logged no changes
                } else {
                    Platform.exit();
                }  
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
                    imageStream = new FileInputStream(fileDest);
                    Image image = new Image(imageStream);
                    double imageWidth = image.getWidth();
                    double imageHeight = image.getHeight();
                    selectedTab.canvasInit(imageWidth, imageHeight, image);
                   
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
                    imageStream = new FileInputStream(fileDest);
                    Image image = new Image(imageStream);
                    double imageWidth = image.getWidth();
                    double imageHeight = image.getHeight();
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
        
        //eH for open4 "Open Public Domain Painting In New Tab"
        open4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //user selects a file with chosen types
                
                String location = "File Didn't Load";
              try {
                  location = new File(
                          System.getProperty("user.dir") + File.separator + "MA.jpg"
                  ).toURI().toURL().toExternalForm();
              } catch (MalformedURLException ex) {
                  Logger.getLogger(MenuPain.class.getName()).log(Level.SEVERE, null, ex);
              }
                
                System.out.print("that worked");
                
                location = "/I:/cs250/Pain(t)FX_2021/MA.jpg";
                
               
  try {
                    FileInputStream imageStream = new FileInputStream(location);
                    Image image = new Image(imageStream);
                    double imageWidth = image.getWidth();
                    double imageHeight = image.getHeight();
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
        
        
        
        
        
        //eH for file/saveAs, creates new scene and adds menubar, sets fileDest,
        //takes snapshot of canvas and saves it to fileDest
        //closes by changing scene and moving menubar
        save2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
                Canvas canvas = selectedTab.canvas;
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
                    //get file data from user type
                    String fileDest = outFile.getName();
                    String extension = fileDest.substring(1 + fileDest.lastIndexOf(".")).toLowerCase();
                    //try to save file in memory, print message if failed
                    // ####### THIS NEEDS A BETTER ERROR MESSAGE PLEASE
                        try {
                            ImageIO.write(SwingFXUtils.fromFXImage(wImage,
                                    null), extension, outFile);
                            selectedTab.setClosable(true);
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
                File outFile = selectedTab.outFile;
                
                try {
                    WritableImage wImage = new WritableImage(
                            (int) canvas.getWidth(),
                            (int) canvas.getHeight());
                    canvas.snapshot(null, wImage);
                    System.out.print(outFile.toString());
                    //default saves as png, could modify this in future to take advantage of extensions?
                    ImageIO.write(SwingFXUtils.fromFXImage(wImage, null),
                            "png", outFile);
                     
                     selectedTab.setClosable(true);
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
        
        
        //eH for image/resize, will prompt user for length and width of new image in two seperate popups
        resize1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //popup a dialog that the user can input data to
                TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
                double inputWidth = 0;
                double inputHeight = 0;
                // create a text input dialog
                TextInputDialog td = new TextInputDialog();
               
                td.setTitle("Resize Image");
                td.getDialogPane().setContentText("New Height:");
                // show the text input dialog
                td.showAndWait();
                TextField inputHeightField = td.getEditor();
                inputHeight = Double.valueOf(inputHeightField.getText());
                System.out.print(inputHeight);
                //reset text input dialog for width
                td.getDialogPane().setContentText("New Width:");
                td.showAndWait();
                TextField inputWidthField = td.getEditor();
                inputWidth = Double.valueOf(inputWidthField.getText());
                System.out.print(inputWidth);
                
                WritableImage wImage = new WritableImage(
                            (int) selectedTab.canvas.getWidth(),
                            (int) selectedTab.canvas.getHeight());

                selectedTab.canvas.snapshot(null, wImage);             
                selectedTab.setClosable(false);       
              
              //remove prior data and initalize canvas, is not using built in TabPain code
              //should get own function in tabPain
              //problem is gc.draw image passing extra paramters here
              //selectedTab.canvasInit(selectedTab.canvas.getWidth(), selectedTab.canvas.getHeight(), wImage);
              
              //code below condensed to canvasInit
                selectedTab.sp.getChildrenUnmodifiable().remove(selectedTab.canvas);
                selectedTab.canvas = new Canvas(inputWidth, inputHeight);
                selectedTab.gc = selectedTab.canvas.getGraphicsContext2D();
                //place image data on graphics context
                selectedTab.gc.drawImage(wImage, 0, 0, inputWidth, inputHeight);
                 //built in padding for testing
                 SnapshotParameters params = new SnapshotParameters();
                  params.setFill(Color.TRANSPARENT);         
                WritableImage image = selectedTab.canvas.snapshot(params, null);
                Double imageW = selectedTab.canvas.getWidth();
                Double imageH = selectedTab.canvas.getHeight();
                selectedTab.previewCanvas =  new Canvas(imageW, imageH);
                selectedTab.previewContext = selectedTab.previewCanvas.getGraphicsContext2D();
                selectedTab.previewContext.drawImage(image, 0, 0);
                selectedTab.sp.setContent(selectedTab.previewCanvas);
                 
                 
                 
               // selectedTab.sp.setMaxSize(inputWidth+20, inputHeight+20);
                //selectedTab.sp.setContent(selectedTab.canvas);
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
        support1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Online Docs and Support");
                alert.setHeaderText("Information Alert");
                String s = "Youtube Demo: https://youtu.be/EN9vIy3C-lI \n "
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
        
        
        //eH for Release Notes dialog
        //passes string with releasenotes name, should this be a CONSTANT ?
        release1.setOnAction(new EventHandler<ActionEvent>() {
          @Override
            public void handle(ActionEvent e) {
             String location = "File Didn't Load";
              try {
                  location = new File(
                          System.getProperty("user.dir") + File.separator + "release_notes_Paint.txt"
                  ).toURI().toURL().toExternalForm();
              } catch (MalformedURLException ex) {
                  Logger.getLogger(MenuPain.class.getName()).log(Level.SEVERE, null, ex);
              }
        System.out.println(location);
        WebView webView = new WebView();
        webView.getEngine().load(location);
        Scene scene = new Scene(webView);
        Stage releaseNotes = new Stage();
        releaseNotes.setScene(scene);
        releaseNotes.show();
            }});
}
}
