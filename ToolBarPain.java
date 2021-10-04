package pain.t.fx_2021;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.paint.Color;
import static pain.t.fx_2021.PainTFX_2021.tabPane;


/**
 *
 * @author Erik
 */
public class ToolBarPain extends ToolBar {
    
    //global variables for painting functionality
    static Slider slider;
    static ColorPicker colorPicker;
    //static Double lineFirstX;
    //static Double lineFirstY;
    static Color currentColor;
    static TextField userVertices;
    static TextField userText;

  
     public ToolBarPain() {
    
         
       // create labels
        Label sliderLabel = new Label("Select the Draw Width");
        Label shapesLabel = new Label("Select A Shape to Draw");
        //Set basic properties of slider like amount of ticks and tick increment
        slider = new Slider();
        lengthsliderInit();
        //Set default color and create listener for color picking
        colorPicker = new ColorPicker();
        colorPickerInit();
        
        //set default colorpicker data
        currentColor = Color.BLUE;
        
       
        
        //create buttons
        Button freeHandButton = new Button("Free Hand Line");
        Button  straightLineButton = new Button("Straight Line");
        Button colorDropperButton = new Button("Color Dropper");
        Button eraseButton = new Button("Erase");
        Button textButton = new Button("Text");
        Button cutPasteButton = new Button("Cut and Paste");
        Button circleButton = new Button("Circle");
        Button squareButton = new Button("Square");
        Button rectangleButton = new Button("Rectangle");
        Button rectangleRoundedButton = new Button("Rounded Rectangle");
        Button ellipseButton = new Button("Ellipse");
        Button polygonButton = new Button("Polygon");
        
        //TextField for user input on polygon length
        userVertices = new TextField("Enter integer greater than 2 for # of polygon sides");
        
        //add buttons, slider, colorpicker to this toolbar object
        this.getItems().addAll(sliderLabel,slider, colorPicker, freeHandButton, 
               straightLineButton, colorDropperButton, eraseButton, textButton, cutPasteButton, shapesLabel, circleButton, squareButton, rectangleButton, 
               rectangleRoundedButton, ellipseButton, polygonButton, userVertices);
       
       //default line draw data
       //lineFirstX = 0.0;
       //lineFirstY = 0.0;
       //defualt active tool
       TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
       ToolBarManager.toolBarManager("freeHand", selectedTab, tabPane, this);
       ToolBarPain toolBarCopy = this;
        
        //event handlers for clicking on toolBar buttons
        freeHandButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ToolBarManager.toolBarManager("freeHand", selectedTab, tabPane, toolBarCopy);
            }
        });
        straightLineButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ToolBarManager.toolBarManager("straightLine", selectedTab, tabPane, toolBarCopy);
            }
        });
        colorDropperButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ToolBarManager.toolBarManager("colorDropper", selectedTab, tabPane, toolBarCopy);
            }
        });
        eraseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ToolBarManager.toolBarManager("erase", selectedTab, tabPane, toolBarCopy);
            }
        });
         textButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ToolBarManager.toolBarManager("text", selectedTab, tabPane, toolBarCopy);
            }
        });
        cutPasteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ToolBarManager.toolBarManager("cutPaste", selectedTab, tabPane, toolBarCopy);
            }
        });
        circleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ToolBarManager.toolBarManager("circle", selectedTab, tabPane, toolBarCopy);
            }
        });
        squareButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ToolBarManager.toolBarManager("square", selectedTab, tabPane, toolBarCopy);
            }
        });
        rectangleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ToolBarManager.toolBarManager("rectangle", selectedTab, tabPane, toolBarCopy);
            }
        });
        rectangleRoundedButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ToolBarManager.toolBarManager("rectangleRounded", selectedTab, tabPane, toolBarCopy);
            }
        });
        ellipseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ToolBarManager.toolBarManager("ellipse", selectedTab, tabPane, toolBarCopy);
            }
        });
        polygonButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ToolBarManager.toolBarManager("polygon", selectedTab, tabPane, toolBarCopy);
            }
        });
        
        

        
        
        
     }

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
                TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
                currentColor = colorPicker.getValue();
                selectedTab.gc.setFill(currentColor);
            }
        });
    }
//get and return text from polygon textfield
    public String polygonSidesInput(){
        String userInput = userVertices.getText();
        return userInput;
}
}
