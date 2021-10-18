/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pain.t.fx_2021;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
* <h1>Methods for saving to and opening from a file</h1>
* Runner object in PainTFX_2021.java can be called 
* to implement methods based on opening and saving files
* 
* @author  Erik Johnson
* @version 1.5
* @since   2021-10-11
*/
public class OpenAndSaveMethods {
    
    Stage stage;
    
    
/**
* Constructor that records data about the stage for dialog
     * @param primaryStage Stage from PainTFX_2021
*/ 
    public OpenAndSaveMethods(Stage primaryStage) {
        
        stage = primaryStage;
    }
    
    
/**
* Event Handler method for PainTFX_2021
* Detects whether user input Open File or Save File
* Open File uses file selector to put image file in current tab
* Save File checks for the tab's default path and throws an error if missing
* Calls LoggingThread#logSave()
* Calls TabPain#canvasInit()
* @param event KeyEvent is data passed from keyboard
*/
    public void keyboardOpenFileAndSave(KeyEvent event) {
                KeyCodeCombination f;
                f = new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN);
                KeyCodeCombination s;
                s = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
                if (f.match(event)) {
                //user selects a file with chosen types
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Image File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
                FileInputStream imageStream;
                //try to use file the user selects, functionality seems suspect/superfluous
                //this keeps memory of fileDest and outFile for future save functions
                //passes image data and image to canvas init which adds it to UX
                try {
                    File file = fileChooser.showOpenDialog(stage);
                    String fileDest = file.getPath();  
                    TabPain selectedTab = (TabPain) PainTFX_2021.tabPane.getSelectionModel().getSelectedItem();
                    selectedTab.fileDest = file.getPath();
                     selectedTab.outFile = file;  
                    imageStream = new FileInputStream(fileDest);
                    Image image = new Image(imageStream);
                    double imageWidth = image.getWidth();
                    double imageHeight = image.getHeight();
                    selectedTab.canvasInit(imageWidth, imageHeight, image);
                   
                } catch (FileNotFoundException ex) {
                   //NEED TO FIX THIS
                    Logger.getLogger(PainTFX_2021.class.getName()).log(Level.SEVERE, null, f);
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
                if (s.match(event)) {
                    
                        //save
                        TabPain selectedTab = (TabPain) PainTFX_2021.tabPane.getSelectionModel().getSelectedItem();
                        Canvas canvas = selectedTab.canvas;
                        File outFile = selectedTab.outFile;
                        //System.out.print(outFile.toString());
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
                            PainTFX_2021.logThread.logSave(true);
                            
                        } catch (Exception ex) {
                            //error message dialog box
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Save Alert");
                            alert.setHeaderText("Save was Unsuccessful");
                            String save = "Try using save as first, no file name or directory has been set";
                            alert.setContentText(save);
                            alert.show();
                            PainTFX_2021.logThread.logSave(false);
                        }
                }    
            }
    
    
/**
* Event Handler method for Open File MenuItem in MenuPain
* Uses file selector to put image file in current tab
* Calls TabPain#canvasInit()
     * @param e ActionEvent is used only for logging purposes
*/
    
     public void fileChooseOpen(ActionEvent e) {
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
                    File file = fileChooser.showOpenDialog(stage);
                    String fileDest = file.getPath();
                    
                    TabPain selectedTab = (TabPain)  PainTFX_2021.tabPane.getSelectionModel().getSelectedItem();
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
    
     
    /**
* Event Handler method for Open File New Tab MenuItem in MenuPain
* Uses file selector to put image file in current tab
* Calls TabPain#canvasInit()
     * @param e ActionEvent is used only for logging purposes
*/
     
     public void fileChooseOpenNewTab(ActionEvent e) {
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
                    File file = fileChooser.showOpenDialog(stage);
                    String fileDest = file.getPath();
                    
                    TabPain selectedTab = (TabPain) PainTFX_2021.tabPane.getSelectionModel().getSelectedItem();
                    selectedTab.fileDest = file.getPath();
                    selectedTab.outFile = file;
                    imageStream = new FileInputStream(fileDest);
                    Image image = new Image(imageStream);
                    double imageWidth = image.getWidth();
                    double imageHeight = image.getHeight();
                    TabPain tab = new TabPain();
                    tab.canvasInit(imageWidth, imageHeight, image);
                    PainTFX_2021.tabPane.getTabs().add(tab);
                   
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
     
     
     
        /**
* Event Handler method for Open Public Domain Image MenuItem in MenuPain
* Checks user.dir for MA.JPG
* Loads image into current tab
* Calls TabPain#canvasInit()
     * @param e ActionEvent is used only for logging purposes
*/
     
     public void filePubDomOpen(ActionEvent e) {
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
                //this is not extendable!!
                location = "/I:/cs250/Pain(t)FX_2021/MA.jpg";   
               try {
                    FileInputStream imageStream = new FileInputStream(location);
                    Image image = new Image(imageStream);
                    double imageWidth = image.getWidth();
                    double imageHeight = image.getHeight();
                    TabPain tab = new TabPain();
                    tab.canvasInit(imageWidth, imageHeight, image);
                    PainTFX_2021.tabPane.getTabs().add(tab);
                   
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
     
     
     
     
     
  /**
* Event Handler method for Save MenuItem in MenuPain
* Saves current canvas data to the file location in current tab
* Throws dialog if no file location data is recorded
* Calls LoggingThread#logSave()
* Calls TabPain#canvasInit()
     * @param e ActionEevent is just used for logging
*/
     
     public void saveGeneric(ActionEvent e) {
                TabPain selectedTab = (TabPain) PainTFX_2021.tabPane.getSelectionModel().getSelectedItem();
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
                      selectedTab.modifiedSinceSaved = false;
                      PainTFX_2021.logThread.logSave(true);
                } catch (Exception ex) {
                    //error message dialog box
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Save Alert");
                alert.setHeaderText("Save was Unsuccessful");
                String s = "Try using save as first, no file name or directory has been set";
                alert.setContentText(s);
                alert.show();
                PainTFX_2021.logThread.logSave(false);
                }
            }
     
      /**
* Event Handler method for Save As MenuItem in MenuPain
* Saves current canvas data to the file location selected by user
* Calls LoggingThread#logSave()
* Calls TabPain#canvasInit()
     * @param e ActionEevent is just used for logging
*/
    public void saveDestChosen(ActionEvent e) {
                TabPain selectedTab = (TabPain)  PainTFX_2021.tabPane.getSelectionModel().getSelectedItem();
                Canvas canvas = selectedTab.canvas;
                // user can type file name and selct path
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Image");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
                File outFile = fileChooser.showSaveDialog(stage);
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
                   
                        try {
                            ImageIO.write(SwingFXUtils.fromFXImage(wImage,
                                    null), extension, outFile);
                            selectedTab.setClosable(true);
                            selectedTab.modifiedSinceSaved = false;
                             PainTFX_2021.logThread.logSave(true);
                        } catch (IOException ex) {
                             //System.out.println(ex.getMessage());
                             PainTFX_2021.logThread.logSave(false);
                        } 
                }
            }
    
    
}
