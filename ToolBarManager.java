/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pain.t.fx_2021;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.TabPane;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


/**
 *
 * @author Erik
 */
public class ToolBarManager {
    //data for these three layout nodes is passed when toolBarManager is called.
    static TabPain selectedTab;
    static TabPane tabPane;
    static ToolBarPain toolBar;
    //doubles that hold the information of starting position for draw
    static Double lineFirstX;
    static Double lineFirstY;
    //doubles that hold the information for the current position for draw
    static Double currentX;
    static Double currentY;
    //doubles that hold the last position for draw
    static Double lastX;
    static Double lastY;
    //used for rounded rectangle
    static Double arcWidth;
    static Color backgroundColor = Color.WHITE;
    //default polygon sides
    static int vertices = 6;
    //current tool
    static String activeCanvasTool;
    //claries if an operation should change previewCanvas AND Canvas
    static boolean previewDraw = true;
    //doubles holding information for cutPaste location on canvas
    static Double cutPasteX1;
    static Double cutPasteX2;
    static Double cutPasteY1;
    static Double cutPasteY2;
    //image that is being cut and paste
    static WritableImage cutPasteImage;
    //function that removes any possibly active button tool and adds new tool passed with string x
    public static void toolBarManager(String x, TabPain selectedTabPass, TabPane tabPanePass, ToolBarPain toolBarPass) {
        
        tabPane = tabPanePass;
        selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
        toolBar = toolBarPass;
        arcWidth = 2.0;
        
        activeCanvasTool = x;
        //remove all tools
        //colorDropper
        try {
            selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_CLICKED, colorDropperHandler);
        } catch (Exception ex) {
        }
        //erase
         try {
            selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, eraseHandler1);
            selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, eraseHandler2);
        } catch (Exception ex) {
        }
         //General Draw
        try {
            selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, shapeAndLineReleaseHandler1);
            selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1 );
            selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
        } catch (Exception ex) {
        }
        //freeHand
        try {
            selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, freeHandHandler1);
            selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, freeHandHandler2);
        } catch (Exception ex) {
        }
        //cutPaste
         try {
             selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1 );
            selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, cutPasteReleaseHandler1);
           selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, cutPasteStartHandler2);
           selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED,cutPasteDragHandler2);
           selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, cutPasteReleaseHandler2);
        } catch (Exception ex) {
        }
        //textTool
         try {
            selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, textHandler1);
            selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, textHandler2);
            selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, textHandler3);
        } catch (Exception ex) {
        }
         
         
         

         //phew, now that this canvas has no tools, selected, lets add the one we want
         
        //eH for freehand tool
        if (x.equalsIgnoreCase("freeHand")) {
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, freeHandHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, freeHandHandler2);
        }

        //eH for straight line tool
        if (x.equalsIgnoreCase("straightLine")) {
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1 );
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, shapeAndLineReleaseHandler1);
        }
        //eH for color grabber tool
        if (x.equalsIgnoreCase("colorDropper")) {
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, colorDropperHandler);
        }   
        //eH for cutPaste tool
        if (x.equalsIgnoreCase("cutPaste")) {
           selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1 );
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, cutPasteReleaseHandler1);
        } 
        //eH for erasetool
        if (x.equalsIgnoreCase("erase")) {
        selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, eraseHandler1);
        selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, eraseHandler2);
        }
        
         //eH for texttool
        if (x.equalsIgnoreCase("text")) {
        selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, textHandler1);
        selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, textHandler2);
        selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, textHandler3);
        }
        //eH for circle tool
        if (x.equalsIgnoreCase("circle")) {
           selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1 );
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, shapeAndLineReleaseHandler1);
        }
        //eH for square tool
        if (x.equalsIgnoreCase("square")) {
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1 );
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, shapeAndLineReleaseHandler1);
        }
        //eH for rectangle tool
        if (x.equalsIgnoreCase("rectangle")) {
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1 );
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, shapeAndLineReleaseHandler1);
        }
        if (x.equalsIgnoreCase("rectangleRounded")) {
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1 );
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, shapeAndLineReleaseHandler1);
        }
        //eH for ellipse tool
        if (x.equalsIgnoreCase("ellipse")) {
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1 );
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, shapeAndLineReleaseHandler1);
        }
        if (x.equalsIgnoreCase("polygon")) {
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1 );
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, shapeAndLineReleaseHandler1);
        }
    }

    
    //Event Handlers for Tools
    
    static EventHandler<MouseEvent> freeHandHandler1 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            
            TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            selectedTab.setClosable(false);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);         
            WritableImage image = selectedTab.canvas.snapshot(params, null);
            //selectedTab.undoRedoData.pushChanges(image);
            selectedTab.undoRedoData.pushChanges(image);
            
            selectedTab.gc.setLineWidth(toolBar.slider.getValue());
            selectedTab.gc.setStroke(toolBar.colorPicker.getValue());
            selectedTab.gc.beginPath();
            selectedTab.gc.moveTo(e.getX(), e.getY());
            selectedTab.gc.stroke();
            selectedTab.previewContext.setLineWidth(toolBar.slider.getValue());
            selectedTab.previewContext.setStroke(toolBar.colorPicker.getValue());
            selectedTab.previewContext.beginPath();
            selectedTab.previewContext.moveTo(e.getX(), e.getY());
            selectedTab.previewContext.stroke();
        }
    };
   
 static EventHandler<MouseEvent> freeHandHandler2 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            
            TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            selectedTab.setClosable(false);
            selectedTab.gc.setStroke(toolBar.colorPicker.getValue());
            selectedTab.gc.lineTo(e.getX(), e.getY());
            selectedTab.gc.stroke();
            selectedTab.previewContext.setStroke(toolBar.colorPicker.getValue());
            selectedTab.previewContext.lineTo(e.getX(), e.getY());
            selectedTab.previewContext.stroke();
           
        }
    };

    //select a color from canvas pixel data
    static EventHandler<MouseEvent> colorDropperHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
             selectedTab.setClosable(false);
            //all of this just detects the position of the mouse on the canvas
            WritableImage wImage = new WritableImage(
                   (int) selectedTab.canvas.getWidth(),
                   (int) selectedTab.canvas.getHeight());
            selectedTab.canvas.snapshot(null, wImage);
            int x = new Double(e.getX()).intValue();
            int y = new Double(e.getY()).intValue();
            PixelReader r = wImage.getPixelReader();
            //get color from canvas position
            toolBar.currentColor = r.getColor(x, y);
            toolBar.colorPicker.setValue(toolBar.currentColor);
        }
    };
    
    //eH for erase start position
     static EventHandler<MouseEvent> eraseHandler1 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            selectedTab.setClosable(false);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);         
            WritableImage image = selectedTab.canvas.snapshot(params, null);
            selectedTab.undoRedoData.pushChanges(image);
            lineFirstX = e.getX();
            lineFirstY = e.getY();
            // Erase a 10-by-10 block around the starting mouse position.
            selectedTab.previewContext.clearRect(lineFirstX-5,lineFirstY-5,10,10);
            selectedTab.gc.clearRect(lineFirstX-5,lineFirstY-5,10,10);
            
        }
    };
     
     //eH for erase dragged
     static EventHandler<MouseEvent> eraseHandler2 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            selectedTab.setClosable(false);
            lineFirstX = e.getX();
            lineFirstY = e.getY();
            selectedTab.previewContext.clearRect(lineFirstX-5,lineFirstY-5,10,10);
            selectedTab.gc.clearRect(lineFirstX-5,lineFirstY-5,10,10);
        }
    };
  //eH for text start position
     static EventHandler<MouseEvent> textHandler1 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {  
            TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            selectedTab.setClosable(false);
            selectedTab.previewCanvas.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String a = event.getCharacter();
                selectedTab.previewContext.strokeText(a, e.getX(), e.getY());
            }
        });
        }
    };
     
     

     
     
     //eH for text start position
     static EventHandler<MouseEvent> textHandler2 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {  
            TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            selectedTab.setClosable(false);
            System.out.println(e.getX());
            selectedTab.previewCanvas.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String a = event.getCharacter();
                System.out.print(a);
                selectedTab.previewContext.strokeText(a, e.getX(), e.getY());
            }
        });
        }
    };
     //eH for text start position
     static EventHandler<MouseEvent> textHandler3 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {  
        }
    };
   
        //eH for recording start position
     static EventHandler<MouseEvent> shapeAndLineStartPosHandler1 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            selectedTab.setClosable(false);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);         
            WritableImage image = selectedTab.canvas.snapshot(params, null);
            selectedTab.undoRedoData.pushChanges(image);
           
           
            lineFirstX = e.getX();
            lineFirstY = e.getY();
            selectedTab.gc.setFill(toolBar.colorPicker.getValue());
            selectedTab.gc.setStroke(toolBar.colorPicker.getValue());
            selectedTab.gc.setLineWidth(toolBar.slider.getValue());
            selectedTab.previewContext.setFill(toolBar.colorPicker.getValue());
            selectedTab.previewContext.setStroke(toolBar.colorPicker.getValue());
            selectedTab.previewContext.setLineWidth(toolBar.slider.getValue());

            //doesnt need to be a switch but other tools might be migrated here
            switch (activeCanvasTool) {
                case "polygon":
                String userVerticesString = toolBar.polygonSidesInput();
            try
            {
                // the String to int conversion happens here
                vertices = Integer.parseInt(userVerticesString.trim());
                if (vertices < 3) {
                    vertices = 3;
                }
            }
                 catch (NumberFormatException nfe)
            {
                System.out.println("NumberFormatException: " + nfe.getMessage());
            }
             default:  // not called in other cases
            break;
            }
           
        }
    };
     
     //eH 
     static EventHandler<MouseEvent> shapeAndLineDragHandler1 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            selectedTab.setClosable(false);
            currentX = e.getX();
            currentY = e.getY();
            
            previewDraw = true;
            drawTool();
        
           lastX = currentX;
           lastY = currentY;
        }
    }; 
     
      static EventHandler<MouseEvent> shapeAndLineReleaseHandler1 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            
            TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            selectedTab.setClosable(false);
            currentX = e.getX();
            currentY = e.getY();
            
            previewDraw = false;
            drawTool();
        }
    };
      
        static EventHandler<MouseEvent> cutPasteReleaseHandler1 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            selectedTab.setClosable(false);
            currentX = e.getX();
            currentY = e.getY();
            cutPasteX2 =currentX;
            cutPasteY2 = currentY;
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);         
            WritableImage image = selectedTab.canvas.snapshot(params, null);
            selectedTab.copyCanvas();
            cutPasteImage = new WritableImage (image.getPixelReader(), cutPasteX1.intValue(), cutPasteY1.intValue(), (int) Math.abs(lineFirstX - currentX), (int) Math.abs(lineFirstY - currentY));
            selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1 );
            selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, cutPasteReleaseHandler1);
           selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, cutPasteStartHandler2);
           selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,cutPasteDragHandler2);
           selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, cutPasteReleaseHandler2);
        }
    };
        static EventHandler<MouseEvent> cutPasteStartHandler2 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            selectedTab.setClosable(false);
            currentX = e.getX();
            currentY = e.getY();
            //make erased area
            //draw on click point
            selectedTab.previewContext.drawImage(cutPasteImage, currentX, currentY);
      }
    };
        static EventHandler<MouseEvent> cutPasteDragHandler2 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            //keep erased area
            //pretend to draw on canvas
             TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            selectedTab.setClosable(false);
            currentX = e.getX();
            currentY = e.getY();
           
            //draw on click point
             selectedTab.previewContext.clearRect(0,0, selectedTab.previewCanvas.getWidth(),selectedTab.previewCanvas.getHeight());
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);         
            WritableImage image =  selectedTab.canvas.snapshot(params, null);
            selectedTab.previewContext.drawImage(image, 0, 0);
             //make erased area
            selectedTab.previewContext.clearRect(cutPasteX1, cutPasteY1, (int) Math.abs(lineFirstX -  cutPasteX2), (int) Math.abs(lineFirstY -  cutPasteY2)); 
            selectedTab.previewContext.drawImage(cutPasteImage, currentX, currentY);
      }
    };
       static EventHandler<MouseEvent> cutPasteReleaseHandler2 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            //draw image on canvas
             TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            selectedTab.setClosable(false);
            currentX = e.getX();
            currentY = e.getY();
            selectedTab.previewContext.clearRect(0,0, selectedTab.previewCanvas.getWidth(),selectedTab.previewCanvas.getHeight());
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);         
            WritableImage image =  selectedTab.canvas.snapshot(params, null);
            selectedTab.previewContext.drawImage(image, 0, 0);

            selectedTab.previewContext.clearRect(cutPasteX1, cutPasteY1, (int) Math.abs(lineFirstX -  cutPasteX2), (int) Math.abs(lineFirstY -  cutPasteY2)); 
            selectedTab.previewContext.drawImage(cutPasteImage, currentX, currentY);
            selectedTab.gc.clearRect(cutPasteX1, cutPasteY1, (int) Math.abs(lineFirstX -  cutPasteX2), (int) Math.abs(lineFirstY -  cutPasteY2)); 
            selectedTab.gc.drawImage(cutPasteImage, currentX, currentY);
            selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, cutPasteStartHandler2 );
            selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, cutPasteDragHandler2);
            selectedTab.previewCanvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, cutPasteReleaseHandler2);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1 );
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, cutPasteReleaseHandler1);
      }
    };
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
       
     public static void drawTool() {
         
         TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
         selectedTab.setClosable(false);
         
         selectedTab.previewContext.clearRect(0,0, selectedTab.previewCanvas.getWidth(),selectedTab.previewCanvas.getHeight());
           SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);         
        WritableImage image =  selectedTab.canvas.snapshot(params, null);
         selectedTab.previewContext.drawImage(image, 0, 0);
//selectedTab.previewCanvas = selectedTab.canvas;
         //selectedTab.previewContext = selectedTab.canvas.getGraphicsContext2D(); 
         
         
        switch (activeCanvasTool) {
        case "polygon":
            drawPolygon();
            break;
        case "square":
            drawSquare();
            break;
        case "rectangle":
            drawRectangle();
            break;
        case "cutPaste":
            drawCutPasteRectangle();
            break;
        case "rectangleRounded":
           drawRectangleRounded();
            break;
        case "circle":
           drawCircle();
            break;
        case "straightLine":
           drawStraightLine();
            break;
        case "ellipse":
           drawEllipse();
            break;
        default:  // not called in other cases
            break;
        }
}
     
    public static void drawCircle(){
         TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            selectedTab.setClosable(false);
           
            double x =currentX;
            double y = currentY;
            //Draw circle
            
             if (previewDraw == true) {
                if (Math.abs(lineFirstX - x) > Math.abs(lineFirstY - y)) { //the user dragged more horizontally than vertically
                    selectedTab.previewContext.fillOval(lineFirstX - Math.abs(lineFirstX - x), lineFirstY - Math.abs(lineFirstX - x),
                            2 * Math.abs(lineFirstX - x), 2 * Math.abs(lineFirstX - x));
                    selectedTab.previewContext.strokeOval(lineFirstX - Math.abs(lineFirstX - x), lineFirstY - Math.abs(lineFirstX - x),
                            2 * Math.abs(lineFirstX - x), 2 * Math.abs(lineFirstX - x));
                } else { //the user dragged more vertically than horizontally
                    selectedTab.previewContext.fillOval(lineFirstX - Math.abs(lineFirstY - y), lineFirstY - Math.abs(lineFirstY - y),
                            2 * Math.abs(lineFirstY - y), 2 * Math.abs(lineFirstY - y));
                    selectedTab.previewContext.strokeOval(lineFirstX - Math.abs(lineFirstY - y), lineFirstY - Math.abs(lineFirstY - y),
                            2 * Math.abs(lineFirstY - y), 2 * Math.abs(lineFirstY - y));
                }
             } else {
                        if (Math.abs(lineFirstX - x) > Math.abs(lineFirstY - y)) { //the user dragged more horizontally than vertically
                    selectedTab.previewContext.fillOval(lineFirstX - Math.abs(lineFirstX - x), lineFirstY - Math.abs(lineFirstX - x),
                            2 * Math.abs(lineFirstX - x), 2 * Math.abs(lineFirstX - x));
                    selectedTab.gc.fillOval(lineFirstX - Math.abs(lineFirstX - x), lineFirstY - Math.abs(lineFirstX - x),
                            2 * Math.abs(lineFirstX - x), 2 * Math.abs(lineFirstX - x));
                    selectedTab.previewContext.strokeOval(lineFirstX - Math.abs(lineFirstX - x), lineFirstY - Math.abs(lineFirstX - x),
                            2 * Math.abs(lineFirstX - x), 2 * Math.abs(lineFirstX - x));
                    selectedTab.gc.strokeOval(lineFirstX - Math.abs(lineFirstX - x), lineFirstY - Math.abs(lineFirstX - x),
                            2 * Math.abs(lineFirstX - x), 2 * Math.abs(lineFirstX - x));
                     selectedTab.copyCanvas();
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, shapeAndLineReleaseHandler1);
                } else { //the user dragged more vertically than horizontally
                    selectedTab.previewContext.fillOval(lineFirstX - Math.abs(lineFirstY - y), lineFirstY - Math.abs(lineFirstY - y),
                            2 * Math.abs(lineFirstY - y), 2 * Math.abs(lineFirstY - y));
                    selectedTab.gc.fillOval(lineFirstX - Math.abs(lineFirstY - y), lineFirstY - Math.abs(lineFirstY - y),
                            2 * Math.abs(lineFirstY - y), 2 * Math.abs(lineFirstY - y));
                    selectedTab.previewContext.strokeOval(lineFirstX - Math.abs(lineFirstY - y), lineFirstY - Math.abs(lineFirstY - y),
                            2 * Math.abs(lineFirstY - y), 2 * Math.abs(lineFirstY - y));
                    selectedTab.gc.strokeOval(lineFirstX - Math.abs(lineFirstY - y), lineFirstY - Math.abs(lineFirstY - y),
                            2 * Math.abs(lineFirstY - y), 2 * Math.abs(lineFirstY - y));
                     selectedTab.copyCanvas();
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, shapeAndLineReleaseHandler1);
                        }
    } }
     public static void drawEllipse(){
        TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            selectedTab.setClosable(false);
            double upperLeftX;
            double upperLeftY;
            double width;
            double height;
            
            //determine where the upper left bound is set
            if (lineFirstX > currentX){
                upperLeftX = currentX;
            } else {
                upperLeftX = lineFirstX;
            }
            if (lineFirstY > currentY){
                upperLeftY = currentY;
            } else {
                upperLeftY = lineFirstY;
            }
            
            width = Math.abs(lineFirstX - currentX);
            height = Math.abs(lineFirstY - currentY);
            
            if (previewDraw == true) {
            selectedTab.previewContext.strokeOval(upperLeftX,upperLeftY,width, height);
            selectedTab.previewContext.fillOval(upperLeftX,upperLeftY,width, height);
        } else {
             selectedTab.gc.strokeOval(upperLeftX,upperLeftY,width, height);
            selectedTab.gc.fillOval(upperLeftX,upperLeftY,width, height);
             selectedTab.previewContext.strokeOval(upperLeftX,upperLeftY,width, height);
            selectedTab.previewContext.fillOval(upperLeftX,upperLeftY,width, height);
            selectedTab.copyCanvas();
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, shapeAndLineReleaseHandler1);
        }
    } 
     
     public static void drawSquare(){
        double x =currentX;
            double y = currentY;
            TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            selectedTab.setClosable(false);

            if (x < lineFirstX) { //user last x went left
                    if (lineFirstX - x > Math.abs(y - lineFirstY)) { //user dragged horizontally > vertically
                        double temp = lineFirstX; 
                        lineFirstX = x;
                        x = temp;
                    } else { //if the user dragged more vertically than horizontally
                        lineFirstX = lineFirstX - Math.abs(y - lineFirstY); //force x to be as equal distance from start as y
                    }
                }
                if (y < lineFirstY) { //user last y went up
                    if (lineFirstY - y > Math.abs(x - lineFirstX)) { //user dragged vertically > horizontally
                        double temp = lineFirstY; 
                        lineFirstY = y;
                        y = temp;
                    } else { //user dragged horizontally > vertically
                        lineFirstY = lineFirstY - Math.abs(x - lineFirstX); //force y to be as equal distance from start as x
                    }
                }
                //Draw square
               if (previewDraw == true) {
                if (Math.abs(lineFirstX - x) > Math.abs(lineFirstY - y)) { //user dragged horizontally > vertically
                        selectedTab.previewContext.fillRect(lineFirstX, lineFirstY, Math.abs(x - lineFirstX), Math.abs(x - lineFirstX)); //width = side length
                        selectedTab.previewContext.strokeRect(lineFirstX, lineFirstY, Math.abs(x - lineFirstX), Math.abs(x - lineFirstX));
                    } else { //user dragged vertically > horizontally
                        selectedTab.previewContext.fillRect(lineFirstX, lineFirstY, Math.abs(y - lineFirstY), Math.abs(y - lineFirstY)); //height = side length
                        selectedTab.previewContext.strokeRect(lineFirstX, lineFirstY, Math.abs(y - lineFirstY), Math.abs(y - lineFirstY));
                    }  
               } else {
                        if (Math.abs(lineFirstX - x) > Math.abs(lineFirstY - y)) { //user dragged horizontally > vertically
                        selectedTab.gc.fillRect(lineFirstX, lineFirstY, Math.abs(x - lineFirstX), Math.abs(x - lineFirstX)); //width = side length
                        selectedTab.gc.strokeRect(lineFirstX, lineFirstY, Math.abs(x - lineFirstX), Math.abs(x - lineFirstX));
                        selectedTab.previewContext.fillRect(lineFirstX, lineFirstY, Math.abs(x - lineFirstX), Math.abs(x - lineFirstX)); //width = side length
                        selectedTab.previewContext.strokeRect(lineFirstX, lineFirstY, Math.abs(x - lineFirstX), Math.abs(x - lineFirstX));
                    selectedTab.copyCanvas();
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, shapeAndLineReleaseHandler1);
    
                        } else { //user dragged vertically > horizontally
                        selectedTab.gc.fillRect(lineFirstX, lineFirstY, Math.abs(y - lineFirstY), Math.abs(y - lineFirstY)); //height = side length
                        selectedTab.gc.strokeRect(lineFirstX, lineFirstY, Math.abs(y - lineFirstY), Math.abs(y - lineFirstY));
                        selectedTab.previewContext.fillRect(lineFirstX, lineFirstY, Math.abs(y - lineFirstY), Math.abs(y - lineFirstY)); //height = side length
                        selectedTab.previewContext.strokeRect(lineFirstX, lineFirstY, Math.abs(y - lineFirstY), Math.abs(y - lineFirstY));
                    selectedTab.copyCanvas();
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, shapeAndLineReleaseHandler1);
    }  
                        }  
                        }
           
        public static void drawCutPasteRectangle(){
        TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            selectedTab.setClosable(false);
            
            //values passed to gc drawoval functions
            double upperLeftX;
            double upperLeftY;
            double width;
            double height;

            
            
            //determine where the upper left bound is set
            if (lineFirstX > currentX){
                upperLeftX = currentX;
                cutPasteX1 = upperLeftX;
                cutPasteX2 = lineFirstX;
            } else {
                upperLeftX = lineFirstX;
                cutPasteX1 = upperLeftX;
                cutPasteX2 = currentX;
            }
            
            if (lineFirstY > currentY){
                upperLeftY = currentY;
                cutPasteY1 = upperLeftY;
                cutPasteY2 = lineFirstY;
            } else {
                upperLeftY = lineFirstY;
                cutPasteY1 = upperLeftY;
                cutPasteY2 = currentY;
            }
            
            width = Math.abs(lineFirstX - currentX);
            height = Math.abs(lineFirstY - currentY);
            
            
            if (previewDraw == true) {
             selectedTab.previewContext.strokeRect(upperLeftX,upperLeftY,width, height);

        } else {
            selectedTab.gc.strokeRect(upperLeftX,upperLeftY,width, height);
            selectedTab.gc.fillRect(upperLeftX,upperLeftY,width, height);
            selectedTab.previewContext.strokeRect(upperLeftX,upperLeftY,width, height);
            selectedTab.previewContext.fillRect(upperLeftX,upperLeftY,width, height);
            selectedTab.copyCanvas();
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, shapeAndLineReleaseHandler1);
    }  
    }

     
    public static void drawRectangle(){
        TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            selectedTab.setClosable(false);
            
            //values passed to gc drawoval functions
            double upperLeftX;
            double upperLeftY;
            double width;
            double height;
            
            //determine where the upper left bound is set
            if (lineFirstX > currentX){
                upperLeftX = currentX;
            } else {
                upperLeftX = lineFirstX;
            }
            
            if (lineFirstY > currentY){
                upperLeftY = currentY;
            } else {
                upperLeftY = lineFirstY;
            }
            
            width = Math.abs(lineFirstX - currentX);
            height = Math.abs(lineFirstY - currentY);
            
            
            if (previewDraw == true) {
             selectedTab.previewContext.strokeRect(upperLeftX,upperLeftY,width, height);
            selectedTab.previewContext.fillRect(upperLeftX,upperLeftY,width, height);
        } else {
            selectedTab.gc.strokeRect(upperLeftX,upperLeftY,width, height);
            selectedTab.gc.fillRect(upperLeftX,upperLeftY,width, height);
            selectedTab.previewContext.strokeRect(upperLeftX,upperLeftY,width, height);
            selectedTab.previewContext.fillRect(upperLeftX,upperLeftY,width, height);
            selectedTab.copyCanvas();
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, shapeAndLineReleaseHandler1);
    }  
    }
   
    public static void drawRectangleRounded(){
        TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            selectedTab.setClosable(false);
            //values passed to gc drawoval functions
            double upperLeftX;
            double upperLeftY;
            double width;
            double height;
            
            //determine where the upper left bound is set
            if (lineFirstX > currentX){
                upperLeftX = currentX;
            } else {
                upperLeftX = lineFirstX;
            }
            
            if (lineFirstY > currentY){
                upperLeftY = currentY;
            } else {
                upperLeftY = lineFirstY;
            }
            
            width = Math.abs(lineFirstX - currentX);
            height = Math.abs(lineFirstY - currentY);
            
            
            if (previewDraw == true) {
             selectedTab.previewContext.strokeRoundRect(upperLeftX,upperLeftY,width, height,arcWidth * 5, arcWidth * 5);
            selectedTab.previewContext.fillRoundRect(upperLeftX,upperLeftY,width, height, arcWidth * 5, arcWidth * 5);
        } else {
             selectedTab.gc.strokeRoundRect(upperLeftX,upperLeftY,width, height,arcWidth * 5, arcWidth * 5);
            selectedTab.gc.fillRoundRect(upperLeftX,upperLeftY,width, height, arcWidth * 5, arcWidth * 5);
            selectedTab.previewContext.strokeRoundRect(upperLeftX,upperLeftY,width, height,arcWidth * 5, arcWidth * 5);
            selectedTab.previewContext.fillRoundRect(upperLeftX,upperLeftY,width, height, arcWidth * 5, arcWidth * 5);
            selectedTab.copyCanvas();
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, shapeAndLineReleaseHandler1);
            
            
            
            }    
    }  
            
            
            
            
    public static void drawPolygon(){
        TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            selectedTab.setClosable(false);

             ArrayList<Double> list = new ArrayList<>(); //list for points
        double theta; //angle in radians between the current point and the positive x axis
        double x = currentX;
        double y = currentY;
        
            theta = Math.atan((lineFirstY - y) / (lineFirstX - x)); //theta is the inverse tangent of y component of the radius and the x component
            if (x < lineFirstX) { //if the user dragged left, adjust theta appropriately
                theta -= Math.PI;
            }
        
        double radius = Math.sqrt((lineFirstX - x) * (lineFirstX - x) + (lineFirstY - y) * (lineFirstY - y));
        for (int i = 0; i < vertices; i++) {
            list.add(radius * Math.cos(theta) + lineFirstX);//add x point
            list.add(radius * Math.sin(theta) + lineFirstY);//add y point
            theta += (2 * Math.PI) / vertices; //increase theta
        }
        
        
                //get x and y valuse of coordinates for polygon
                double[] xValues = new double[vertices];
                for (int i = 0; i < vertices; i++) {
                    xValues[i] = list.get(i * 2);
                }
                double[] yValues = new double[vertices];
                for (int i = 0; i < vertices; i++) {
                    yValues[i] = list.get(i * 2 + 1);
                }
                //draw polygon

                if (previewDraw == true) {
            selectedTab.previewContext.fillPolygon(xValues, yValues, vertices);
            selectedTab.previewContext.strokePolygon(xValues, yValues, vertices);
        } else {
            selectedTab.gc.fillPolygon(xValues, yValues, vertices);
            selectedTab.gc.strokePolygon(xValues, yValues, vertices);
            selectedTab.previewContext.fillPolygon(xValues, yValues, vertices);
            selectedTab.previewContext.strokePolygon(xValues, yValues, vertices);
            selectedTab.copyCanvas();
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, shapeAndLineReleaseHandler1);
        }
    }  
    public static void drawStraightLine(){
        TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
        if (previewDraw == true) {
            selectedTab.previewContext.strokeLine(lineFirstX, lineFirstY, currentX, currentY);
        } else {
            selectedTab.gc.strokeLine(lineFirstX, lineFirstY, currentX, currentY);
            selectedTab.previewContext.strokeLine(lineFirstX, lineFirstY, currentX, currentY);
            selectedTab.copyCanvas();
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, shapeAndLineStartPosHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, shapeAndLineDragHandler1);
            selectedTab.previewCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, shapeAndLineReleaseHandler1);
        }
           
    }  
     
   
}
