package pain.t.fx_2021;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


/**
 *
 * @author Erik
 */
public class ToolBarPain extends ToolBar {
    
    //global variables for painting functionality
    static Slider slider;
    static ColorPicker colorPicker;
    static Double lineFirstX;
    static Double lineFirstY;
    static Color currentColor;
    static Label colorLabel;
       
  
     public ToolBarPain() {
    
         
         
         
        //Set basic properties of slider like amount of ticks and tick increment
        slider = new Slider();
        lengthsliderInit();
        //Set default color and create listener for color picking
        colorPicker = new ColorPicker();
        colorPickerInit();
        
        //set default colorpicker data
        currentColor = Color.BLUE;
        colorLabel = new Label("You are painting with" + currentColor.toString());
        
        //create buttons
        Button freeHandButton = new Button("Free Hand Line");
        Button  straightLineButton = new Button("Straight Line");
        Button colorDropperButton = new Button("Color Dropper");
        
        //add buttons, slider, colorpicker to this toolbar object
        this.getItems().addAll(slider, colorPicker, freeHandButton, 
               straightLineButton, colorDropperButton);
        
       //default line draw data
       lineFirstX = 0.0;
       lineFirstY = 0.0;
       //defualt active tool
       toolbarManager("freeHand");
        
        //event handlers for clicking on toolBar buttons
        freeHandButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                toolbarManager("freeHand");
            }
        });
        straightLineButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                toolbarManager("straightLine");
            }
        });
        colorDropperButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                toolbarManager("colorDropper");
            }
        });
    
    
     }
    
     //function that removes any possibly active button tool and adds new tool passed with string x
    static void toolbarManager(String x) {

        String activeCanvasTool = x;
        //remove all tools
        try {
            PainTFX_2021.canvas.removeEventHandler(MouseEvent.MOUSE_CLICKED, colorDropperHandler);
        } catch (Exception ex) {
        }
        try {
            PainTFX_2021.canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, straightLineHandler1);
        } catch (Exception ex) {
        }
        try {
            PainTFX_2021.canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, straightLineHandler2);
        } catch (Exception ex) {
        }
        try {
            PainTFX_2021.canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, freeHandHandler);
        } catch (Exception ex) {
        }

        //eH for freehand tool
        if (x.equalsIgnoreCase("freeHand")) {
            PainTFX_2021.canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, freeHandHandler);
        }

        //eH for straight line tool
        if (x.equalsIgnoreCase("straightLine")) {
            PainTFX_2021.canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, straightLineHandler1);
            PainTFX_2021.canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, straightLineHandler2);
        }

        //eH for color grabber tool
        if (x.equalsIgnoreCase("colorDropper")) {
            PainTFX_2021.canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, colorDropperHandler);
        }
        
    }
    
    
    //Event Handlers for Tools
    
 static EventHandler<MouseEvent> freeHandHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            PainTFX_2021.gc.setFill(colorPicker.getValue());
            PainTFX_2021.gc.fillRect(e.getX(), e.getY(), slider.getValue(), slider.getValue());
        }
    };
 
 //straight line has start and stop functionality
    static EventHandler<MouseEvent> straightLineHandler1 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            lineFirstX = e.getX();
            lineFirstY = e.getY();
        }
    };
    static EventHandler<MouseEvent> straightLineHandler2 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            PainTFX_2021.gc.setFill(colorPicker.getValue());
            PainTFX_2021.gc.setLineWidth(slider.getValue());
            PainTFX_2021.gc.setStroke(currentColor);
            PainTFX_2021.gc.strokeLine(lineFirstX, lineFirstY, e.getX(), e.getY());
        }
    };
    
    //select a color from canvas pixel data
    static EventHandler<MouseEvent> colorDropperHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            //all of this just detects the position of the mouse on the canvas
            WritableImage wImage = new WritableImage(
                    (int) PainTFX_2021.canvas.getWidth(),
                    (int) PainTFX_2021.canvas.getHeight());
            PainTFX_2021.canvas.snapshot(null, wImage);
            int x = new Double(e.getX()).intValue();
            int y = new Double(e.getY()).intValue();
            PixelReader r = wImage.getPixelReader();
            //get color from canvas position
            currentColor = r.getColor(x, y);
            colorLabel.setText("You are painting with" + currentColor.toString());
            colorPicker.setValue(currentColor);
        }
    };

//initialize slider data/formating
public void lengthsliderInit() {
    
        slider = new Slider(1, 10, 2);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(0.25f);
        slider.setBlockIncrement(0.1f);
    }
//intialize colorPicker data/formatting
    public static void colorPickerInit() {
        colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLUE);
        //special event handler for clicking on the colorPicker, this is not a button
        colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                currentColor = colorPicker.getValue();
                PainTFX_2021.gc.setFill(currentColor);
                colorLabel.setText("You are painting with" + currentColor.toString());

            }
        });
    }

}
