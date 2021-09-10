/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pain.t.fx_2021;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Erik
 */
public class PainTFX_2021 extends Application {
    
    //global variables
    Image image;
    String fileDest;
    Double imageWidth;
    Double imageHeight;
    ScrollPane sp;
    ScrollPane windowSp;
    Scene scene;
    VBox vbox;
    ImageView iv1;
    MenuPain mb;
    
    
    @Override
    public void start(Stage primaryStage) {
        
        
        
        vbox = new VBox();
        windowSp = new ScrollPane();
        windowSp.setPrefSize(500, 500);
        sp = new ScrollPane();
        imageWidth = 400.0;
        imageHeight = 400.0;
        iv1 = new ImageView();
        mb = new MenuPain(primaryStage, iv1);
        sp.setContent(iv1); 
        vbox.setPadding(new Insets(10, 20, 10, 20));
        sp.setPadding(new Insets(10, 20, 10, 20));
        windowSp.setPadding(new Insets(1, 1, 1, 1)); 
        vbox.getChildren().addAll(mb,sp);
        vbox.prefWidthProperty().bind(primaryStage.widthProperty().subtract(50));
        windowSp.setContent(vbox);
        scene = new Scene(windowSp, 500, 500);
        primaryStage.setTitle("Pain(t)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}