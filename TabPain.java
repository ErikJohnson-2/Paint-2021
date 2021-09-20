/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pain.t.fx_2021;

import java.io.File;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;

/**
 *
 * @author Erik
 */
public class TabPain extends Tab {
    
    //static GraphicsContext gc;
    //Canvas canvas;
    static File outFile;
    static String fileDest;
    static ScrollPane sp;
    GraphicsContext gc;
    Canvas canvas;
    
    
    public TabPain (){
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
        
        this.setContent(sp);
    }
    
     public void canvasInit(Double imageW, Double imageH, Image img) {
        
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
