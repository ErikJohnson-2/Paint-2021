
package pain.t.fx_2021;

import java.io.File;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

/**
 *
 * @author Erik
 */
public class TabPain extends Tab {
    

    static File outFile;
    static String fileDest;
    ScrollPane sp;
    GraphicsContext gc;
    GraphicsContext previewContext;
    Canvas canvas;
    Canvas previewCanvas;
    boolean modifiedSinceSaved;
    Scale zoomScale;
    int currentZoom = 1;
    UndoRedo undoRedoData;
    
    
    public TabPain (){
        //throw an error if user tries to save before using the filefinder dialogue
        fileDest = "null";
        undoRedoData = new UndoRedo();
        //initialize canvas and graphics context
        Double imageWidth = 300.0;
        Double imageHeight = 300.0;
        canvas = new Canvas(imageWidth, imageHeight);
        gc = canvas.getGraphicsContext2D();
        previewCanvas = new Canvas(imageWidth, imageHeight);
        previewContext = previewCanvas.getGraphicsContext2D();
        
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
    }
    
    //creates a new canvas out of an image for the current tabs scrollpane
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
     //behaves almost the same as CanvasInit but is not passed dimensions
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
       
       //resets previewCanvas to the canvas data
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
}