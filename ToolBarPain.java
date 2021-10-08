package pain.t.fx_2021;

import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        //Button freeHandButton = new Button("Free Hand Line");
        //Button  straightLineButton = new Button("Straight Line");
        Button freeHandButton = new Button();
        Button  straightLineButton = new Button();
        //Button colorDropperButton = new Button("Color Dropper");
        Button colorDropperButton = new Button();
        Button eraseButton = new Button();
        //Button eraseButton = new Button("Erase");
        //Button textButton = new Button("Text");
        Button textButton = new Button();
        //Button cutPasteButton = new Button("Cut and Paste");
        Button cutPasteButton = new Button();
        //Button circleButton = new Button("Circle");
        Button circleButton = new Button();
        //Button squareButton = new Button("Square");
        Button squareButton = new Button();
        //Button rectangleButton = new Button("Rectangle");
        Button rectangleButton = new Button();
        //Button rectangleRoundedButton = new Button("Rounded Rectangle");
        Button rectangleRoundedButton = new Button();
       // Button ellipseButton = new Button("Ellipse");
        //Button polygonButton = new Button("Polygon");
        Button ellipseButton = new Button();
        Button polygonButton = new Button();
        
        
        //button icons
      
        String location = "File Didn't Load";
              try {
                  location = new File(
                          System.getProperty("user.dir") + File.separator + "rectangle.png"
                  ).toURI().toURL().toExternalForm();
              } catch (MalformedURLException ex) {
                  Logger.getLogger(MenuPain.class.getName()).log(Level.SEVERE, null, ex);
              }
        rectangleButton.setPrefSize(20, 20);
        Image img = new Image(location);
        ImageView viewRectangle = new ImageView(img);
        viewRectangle.setFitHeight(20);
        viewRectangle.setPreserveRatio(true);
        rectangleButton.setGraphic(viewRectangle);
        
        location = "File Didn't Load";
              try {
                  location = new File(
                          System.getProperty("user.dir") + File.separator + "square.png"
                  ).toURI().toURL().toExternalForm();
              } catch (MalformedURLException ex) {
                  Logger.getLogger(MenuPain.class.getName()).log(Level.SEVERE, null, ex);
              }
        squareButton.setPrefSize(20, 20);
        img = new Image(location);
        ImageView viewSquare = new ImageView(img);
        viewSquare.setFitHeight(20);
        viewSquare.setPreserveRatio(true);
        squareButton.setGraphic(viewSquare);
        
        location = "File Didn't Load";
              try {
                  location = new File(
                          System.getProperty("user.dir") + File.separator + "circle.png"
                  ).toURI().toURL().toExternalForm();
              } catch (MalformedURLException ex) {
                  Logger.getLogger(MenuPain.class.getName()).log(Level.SEVERE, null, ex);
              }
        circleButton.setPrefSize(20, 20);
        img = new Image(location);
        ImageView viewCircle = new ImageView(img);
        viewCircle.setFitHeight(20);
        viewCircle.setPreserveRatio(true);
        circleButton.setGraphic(viewCircle);
        
        
        location = "File Didn't Load";
              try {
                  location = new File(
                          System.getProperty("user.dir") + File.separator + "erase.png"
                  ).toURI().toURL().toExternalForm();
              } catch (MalformedURLException ex) {
                  Logger.getLogger(MenuPain.class.getName()).log(Level.SEVERE, null, ex);
              }
        eraseButton.setPrefSize(20, 20);
        img = new Image(location);
        ImageView viewErase = new ImageView(img);
        viewErase.setFitHeight(20);
        viewErase.setPreserveRatio(true);
        eraseButton.setGraphic(viewErase);
        
         location = "File Didn't Load";
              try {
                  location = new File(
                          System.getProperty("user.dir") + File.separator + "cutPaste.png"
                  ).toURI().toURL().toExternalForm();
              } catch (MalformedURLException ex) {
                  Logger.getLogger(MenuPain.class.getName()).log(Level.SEVERE, null, ex);
              }
        cutPasteButton.setPrefSize(20, 20);
        img = new Image(location);
        ImageView viewCutPaste = new ImageView(img);
        viewCutPaste.setFitHeight(20);
        viewCutPaste.setPreserveRatio(true);
        cutPasteButton.setGraphic(viewCutPaste);
        
        
        location = "File Didn't Load";
              try {
                  location = new File(
                          System.getProperty("user.dir") + File.separator + "colorDrop.png"
                  ).toURI().toURL().toExternalForm();
              } catch (MalformedURLException ex) {
                  Logger.getLogger(MenuPain.class.getName()).log(Level.SEVERE, null, ex);
              }
        colorDropperButton.setPrefSize(20, 20);
        img = new Image(location);
        ImageView viewColorDrop = new ImageView(img);
        viewColorDrop.setFitHeight(20);
        viewColorDrop.setPreserveRatio(true);
        colorDropperButton.setGraphic(viewColorDrop);
        
         location = "File Didn't Load";
              try {
                  location = new File(
                          System.getProperty("user.dir") + File.separator + "text.png"
                  ).toURI().toURL().toExternalForm();
              } catch (MalformedURLException ex) {
                  Logger.getLogger(MenuPain.class.getName()).log(Level.SEVERE, null, ex);
              }
        textButton.setPrefSize(20, 20);
        img = new Image(location);
        ImageView viewText = new ImageView(img);
        viewText.setFitHeight(20);
        viewText.setPreserveRatio(true);
        textButton.setGraphic(viewText);
        
        
        
         location = "File Didn't Load";
              try {
                  location = new File(
                          System.getProperty("user.dir") + File.separator + "polygon.png"
                  ).toURI().toURL().toExternalForm();
              } catch (MalformedURLException ex) {
                  Logger.getLogger(MenuPain.class.getName()).log(Level.SEVERE, null, ex);
              }
        polygonButton.setPrefSize(20, 20);
        img = new Image(location);
        ImageView viewPolygon = new ImageView(img);
        viewPolygon.setFitHeight(20);
        viewPolygon.setPreserveRatio(true);
        polygonButton.setGraphic(viewPolygon);
        
         location = "File Didn't Load";
              try {
                  location = new File(
                          System.getProperty("user.dir") + File.separator + "ellipse.png"
                  ).toURI().toURL().toExternalForm();
              } catch (MalformedURLException ex) {
                  Logger.getLogger(MenuPain.class.getName()).log(Level.SEVERE, null, ex);
              }
        ellipseButton.setPrefSize(20, 20);
        img = new Image(location);
        ImageView viewEllipse = new ImageView(img);
        viewEllipse.setFitHeight(20);
        viewEllipse.setPreserveRatio(true);
        ellipseButton.setGraphic(viewEllipse);
        
         location = "File Didn't Load";
              try {
                  location = new File(
                          System.getProperty("user.dir") + File.separator + "roundRect.png"
                  ).toURI().toURL().toExternalForm();
              } catch (MalformedURLException ex) {
                  Logger.getLogger(MenuPain.class.getName()).log(Level.SEVERE, null, ex);
              }
        rectangleRoundedButton.setPrefSize(20, 20);
        img = new Image(location);
        ImageView viewRoundRect = new ImageView(img);
        viewRoundRect.setFitHeight(20);
        viewRoundRect.setPreserveRatio(true);
        rectangleRoundedButton.setGraphic(viewRoundRect);
        
        
         location = "File Didn't Load";
              try {
                  location = new File(
                          System.getProperty("user.dir") + File.separator + "lineDraw.png"
                  ).toURI().toURL().toExternalForm();
              } catch (MalformedURLException ex) {
                  Logger.getLogger(MenuPain.class.getName()).log(Level.SEVERE, null, ex);
              }
        straightLineButton.setPrefSize(20, 20);
        img = new Image(location);
        ImageView viewStraightLine = new ImageView(img);
        viewStraightLine.setFitHeight(20);
        viewStraightLine.setPreserveRatio(true);
        straightLineButton.setGraphic(viewStraightLine);
        
         location = "File Didn't Load";
              try {
                  location = new File(
                          System.getProperty("user.dir") + File.separator + "freeHand.png"
                  ).toURI().toURL().toExternalForm();
              } catch (MalformedURLException ex) {
                  Logger.getLogger(MenuPain.class.getName()).log(Level.SEVERE, null, ex);
              }
        freeHandButton.setPrefSize(20, 20);
        img = new Image(location);
        ImageView viewFreeHand = new ImageView(img);
        viewFreeHand.setFitHeight(20);
        viewFreeHand.setPreserveRatio(true);
        freeHandButton.setGraphic(viewFreeHand);
        


        //create button tooltips
        freeHandButton.setTooltip(new Tooltip("Draw free hand with above color and width"));
        straightLineButton.setTooltip(new Tooltip("Draw a straight line with drag and release"));
        colorDropperButton.setTooltip(new Tooltip("Select a color from the canvas to draw with"));
        eraseButton.setTooltip(new Tooltip("Free hand eraser (transparant, not white)"));
        textButton.setTooltip(new Tooltip("Click and input text to be drawn on canvas"));
        cutPasteButton.setTooltip(new Tooltip("Click and release to select part of image. Click and drag into place"));
        circleButton.setTooltip(new Tooltip("Draw a circle, drag and release"));
        squareButton.setTooltip(new Tooltip("Draw a square, drag and release"));
        rectangleButton.setTooltip(new Tooltip("Draw a rectangle, drag and release"));
        rectangleRoundedButton.setTooltip(new Tooltip("Draw a rectangle with rounded corners, drag and release"));
        ellipseButton.setTooltip(new Tooltip("Draw an ellipse with drag and release"));
        polygonButton.setTooltip(new Tooltip("Draw a regular polygon with sides input below, drag and release"));
        
        //TextField for user input on polygon length
        userVertices = new TextField("Enter integer greater than 2 for # of polygon sides");
        userVertices.setTooltip(new Tooltip("Enter integer greater than 2 for # of polygon sides"));
        
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
        slider.setTooltip(new Tooltip("Move slider to change width of line tools"));
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(0.25f);
        slider.setBlockIncrement(0.1f);
    }
//intialize colorPicker data/formatting
    public static void colorPickerInit() {
        colorPicker = new ColorPicker();
        colorPicker.setTooltip(new Tooltip("Select a color to draw with"));
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
