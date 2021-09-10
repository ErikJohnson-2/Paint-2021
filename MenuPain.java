/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pain.t.fx_2021;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Erik
 */



public class MenuPain extends MenuBar{
    
    
    public MenuPain(Stage primaryStage, ImageView iv1) {
        
    Menu m = new Menu("Menu");
    Menu exit = new Menu("Close Pain(t)");
    
    // create menuitems
    MenuItem m1 = new MenuItem("Open File");
    MenuItem exit1 = new MenuItem("Are you sure?");
       
    // add menu items to menu
    m.getItems().add(m1);
    exit.getItems().add(exit1);
    
    // add menu to menubar
    this.getMenus().add(m);
    this.getMenus().add(exit);
    
    
    
    
   //eventHandler for close menuItem
        exit1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Platform.exit();
            }
        });
        
    //eventHandler for fileOpen menuItem    
        m1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
                FileInputStream imageStream;
                try {
                    File file = fileChooser.showOpenDialog(primaryStage);
                    String fileDest = file.getPath();
                    imageStream = new FileInputStream(fileDest);
                    Image image = new Image(imageStream);
                    iv1.setImage(image);
                    
                    
                } catch (FileNotFoundException ex) {
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


}
}
