
package pain.t.fx_2021;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javax.imageio.ImageIO;


/**
* <h1>Extends Tab to handle a modifiable canvas and auto-save timer</h1>
* Extension of Tab with variables for canvas data,
* file data, zoom data, undo data, and timer data.
* Methods deal with saving/updating canvas and timer.
*
* @author  Erik Johnson
* @version 1.4
* @since   2021-10-07
* @see ScrollPane
*/
public class TabPain extends Tab {
    

    static File outFile;
    static String fileDest;
    ScrollPane sp;
    GraphicsContext gc;
    GraphicsContext previewContext;
    Canvas canvas;
    Canvas previewCanvas;
    boolean modifiedSinceSaved = false;
    Scale zoomScale;
    int currentZoom = 1;
    UndoRedo undoRedoData;
    Timer timer;
    Boolean timerEnd = false;
    double timerLoop;
    File file; 
    
    
/**
* Constructor for Tab takes no arguments. 
* Initializes blank canvas, ScrollPane, and timer.
* Calls birthTimer.
*
*/
    
    public TabPain () {
        //throw an error if user tries to save before using the filefinder dialogue
        fileDest = "null";
        
        this.setOnClosed(new EventHandler() {
            public void handle(Event t) {
                timerEnd = true;
                timer.cancel();
            }
        });
        
        undoRedoData = new UndoRedo();
        //initialize canvas and graphics context
        Double imageWidth = 300.0;
        Double imageHeight = 300.0;
        canvas = new Canvas(imageWidth, imageHeight);
        gc = canvas.getGraphicsContext2D();
        previewCanvas = new Canvas(imageWidth, imageHeight);
        previewContext = previewCanvas.getGraphicsContext2D();  
        
        try {
            String data = "";
            data = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ File.separator+ "AutoSaves" + File.separator + "Timer.txt")));
            double inputTimer = Double.valueOf(data);
            timerLoop = inputTimer * 1000;
            if (timerLoop < 1000 || timerLoop > 60000) {
                timerLoop = 5000;
            }
            
        } catch (IOException ex) {
            PainTFX_2021.logThread.logGeneral("Timer file failed to be read"); 
            timerLoop = (int) 5000;
            if (timerLoop < 1000 || timerLoop > 60000) {
                timerLoop = 5000;
            }
        }
        
        //initialize scrollpane
        sp = new ScrollPane();
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        //make sure there is no previous data
        sp.getChildrenUnmodifiable().remove(canvas);
        sp.setContent(previewCanvas);
        //dont let scollpane get larger than canvas
        sp.setMaxSize(imageWidth, imageHeight);
        sp.autosize();
        this.setContent(sp);
        SimpleDateFormat format = new SimpleDateFormat("M-d_HHmmss"); 
        file = new File(System.getProperty("user.dir")+ File.separator+ "AutoSaves" + File.separator + "tempfile" + format.format(Calendar.getInstance().getTime())+".png");
        birthTimer();
        
        
    }
    
/**
* Creates a new canvas out of an image to display in tab
     * @param imageW Double with width of passed image
     * @param imageH Double with height of passed image
     * @param img Image to be copied to this Tab
*/
     public void canvasInit(Double imageW, Double imageH, Image img) {
        
        //remove prior data and initalize canvas
        sp.getChildrenUnmodifiable().remove(canvas);
        canvas = new Canvas(imageW, imageH);
        gc = canvas.getGraphicsContext2D();
        //place image data on graphics context
        gc.drawImage(img, 0, 0);
        //built in padding for testing
        sp.setMaxSize(imageW+20, imageH+20);
        previewCanvas = canvas;
        previewContext = previewCanvas.getGraphicsContext2D();
        sp.setContent(previewCanvas);
        copyCanvas();
    }    

/**
* Creates a new canvas out of an image to display in tab
* Is not passed dimensions
* @param img Image to be copied to this Tab
* @see MenuPain
*/
       public void redoUndoCanvasInit(Image img) {
        
        //remove prior data and initalize canvas
        sp.getChildrenUnmodifiable().remove(canvas);
        canvas = new Canvas(img.getWidth(), img.getHeight());
        gc = canvas.getGraphicsContext2D();
        
        //place image data on graphics context
        gc.drawImage(img, 0, 0);

        //built in padding for testing
        sp.setMaxSize(img.getWidth()+20, img.getHeight()+20);
        previewCanvas = canvas;
        previewContext = previewCanvas.getGraphicsContext2D();
        sp.setContent(previewCanvas);
        copyCanvas();
    }    
       
/**
* Resets previewCanvas to Canvas data
* Has to reset display because it is a new canvas
*/
      public void copyCanvas() {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);         
        WritableImage image = canvas.snapshot(params, null);
        Double imageW = previewCanvas.getWidth();
        Double imageH = previewCanvas.getHeight();
        previewCanvas =  new Canvas(imageW, imageH);
        previewContext = previewCanvas.getGraphicsContext2D();
        previewContext.drawImage(image, 0, 0);
        sp.setContent(previewCanvas);

    }


/**
* Saves Canvas to a Temporary File
* Currently saves to Pain(t)FX_2021
* Auto-save instanced to each Tab
* Called by BirthTimer
* 
     * @return 
*/
public boolean autoSaveCanvas() {
    SnapshotParameters params = new SnapshotParameters();
    params.setFill(Color.TRANSPARENT);   
    WritableImage wImage = canvas.snapshot(params, null);
    try {
    ImageIO.write(SwingFXUtils.fromFXImage(wImage, null), "png", file);
    return true;
    } catch (Exception e){
        PainTFX_2021.logThread.logGeneral("AutoSave failed"); 
        return false;
    }
}
/**
* Begins autoSave timer thread 
* Do not call this function without killing previous timer.
* Is killed by exit functionality
* @see autoSaveCanvas
* 
*/
public void birthTimer (){
    
    timer = new Timer();
        
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
             //save
             Platform.runLater(() -> {
                
            //##change timer text to saving...?
                if (timerEnd == true) {
                timer.cancel();
                } else {
                        if (autoSaveCanvas() == true) {
                        setClosable(true);
                        //cludge to make logRecord that it went to autosave
                        String saveDest = fileDest;
                        fileDest = file.toString();
                        PainTFX_2021.logThread.logSave(true);
                        fileDest = saveDest;  
                        } else {
                              PainTFX_2021.logThread.logSave(false);
                        }
            }  
                PainTFX_2021.timerStart((int) timerLoop);
                });
            }
                     
        }, 0, (int) timerLoop);   
    
}

}