package pain.t.fx_2021;

import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import static pain.t.fx_2021.PainTFX_2021.tabPane;


/**
 <h1>Layout for drawing tools</h1>
* Extension of ToolBar with variables for tool buttons,
* sliders, fields, and labels. Records current color selection
* Displays if tab has changes.
* Methods handle with updating labels and initializing ToolBar
*
* @author  Erik Johnson
* @version 1.5
* @since   2021-10-16
 */
public class ToolBarPain extends ToolBar {
    
    //global variables for painting functionality
    static Slider slider;
    static ColorPicker colorPicker;
    static Color currentColor;
    static TextField userVertices;
    static TextField userText;
    static Label tabHasChanges;
    static ToggleButton freeHandButton;
    static ToggleButton  straightLineButton;
    static ToggleButton colorDropperButton;
    static ToggleButton eraseButton ;
    static ToggleButton textButton;
    static ToggleButton cutPasteButton;
    static ToggleButton copyPasteButton;
    static ToggleButton circleButton;
    static ToggleButton squareButton;
    static ToggleButton rectangleButton;
    static ToggleButton rectangleRoundedButton;
    static ToggleButton ellipseButton;
    static ToggleButton polygonButton;
    
    static ToggleGroup toggleGroup ;
  
    /**
     * Constructor for ToolBarPain
     * Creates all tool bar items. Calls setButtonIcons, setToolTips,
     * sliderInit, and colorPickerInit
     */
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
        
        tabHasChanges = new Label("No Changes To Tab");
        
        //create buttons
        freeHandButton = new ToggleButton();
        straightLineButton = new ToggleButton();
        colorDropperButton = new ToggleButton();
        eraseButton = new ToggleButton();
        textButton = new ToggleButton();
        cutPasteButton = new ToggleButton();
        copyPasteButton = new ToggleButton();
        circleButton = new ToggleButton();
        squareButton = new ToggleButton();
        rectangleButton = new ToggleButton();
        rectangleRoundedButton = new ToggleButton();
        ellipseButton = new ToggleButton();
        polygonButton = new ToggleButton();
        
        //toggle group 
        toggleGroup = new ToggleGroup();
        
        freeHandButton.setToggleGroup(toggleGroup);
        straightLineButton.setToggleGroup(toggleGroup);
        colorDropperButton.setToggleGroup(toggleGroup);
        eraseButton.setToggleGroup(toggleGroup);
        textButton.setToggleGroup(toggleGroup);
        cutPasteButton.setToggleGroup(toggleGroup);
        copyPasteButton.setToggleGroup(toggleGroup);
        circleButton.setToggleGroup(toggleGroup);
        squareButton.setToggleGroup(toggleGroup);
        rectangleButton.setToggleGroup(toggleGroup);
        rectangleRoundedButton.setToggleGroup(toggleGroup);
        ellipseButton.setToggleGroup(toggleGroup);
        polygonButton.setToggleGroup(toggleGroup);
        
        
        setButtonIcons();
        userVertices = new TextField("Enter integer greater than 2 for # of polygon sides");
        setToolTips();
        
        //add buttons, slider, colorpicker to this toolbar object
        this.getItems().addAll(tabHasChanges, sliderLabel,slider, colorPicker, freeHandButton, 
               straightLineButton, colorDropperButton, eraseButton, textButton, cutPasteButton, copyPasteButton, shapesLabel, circleButton, squareButton, rectangleButton, 
               rectangleRoundedButton, ellipseButton, polygonButton, userVertices);
       
        setOnButtonActions();
        
     }

    /**
     * Initializes slider data and formatting
     */
public void lengthsliderInit() {
    
        slider = new Slider(1, 10, 2);
        slider.setTooltip(new Tooltip("Move slider to change width of line tools"));
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(0.25f);
        slider.setBlockIncrement(0.1f);
    }

    /**
     * Initializes colorPicker data and formatting
     * Adds event handler to set fill of currentColor
     */
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

    /**
     * Get and return text from polygon TextfFeld
     * @return userInput String with the current text
     */
    public String polygonSidesInput(){
        String userInput = userVertices.getText();
        return userInput;
}

    /**
     * Check if current tab has changes and update tabHasChanges label
     */
    public static void checkTabChanges(){
        TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab.modifiedSinceSaved == true) {
            tabHasChanges.setText("Tab Has Unsaved Changes");
        } else {
            tabHasChanges.setText("No Unsaved Changes");
        }
}
    
    /**
     * Sets event handlers for clicking on toolBar buttons
     * Each handler redirects to ToolBarManager
     * It passes a string of the active tool name along with data for the tab and tool bar
     *
     */
    public void setOnButtonActions(){
       //event handlers for clicking on toolBar buttons
       TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
       ToolBarManager.toolBarManager("freeHand", selectedTab, tabPane, this);
       ToolBarPain toolBarCopy = this;
       
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
        copyPasteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ToolBarManager.toolBarManager("copyPaste", selectedTab, tabPane, toolBarCopy);
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
    
    /**
     * Sets tool-tips for all of the tool buttons and the polygon input textField
     */
    public static void setToolTips(){
        freeHandButton.setTooltip(new Tooltip("Draw free hand with above color and width"));
        straightLineButton.setTooltip(new Tooltip("Draw a straight line with drag and release"));
        colorDropperButton.setTooltip(new Tooltip("Select a color from the canvas to draw with"));
        eraseButton.setTooltip(new Tooltip("Free hand eraser (transparant, not white)"));
        textButton.setTooltip(new Tooltip("Click and input text to be drawn on canvas"));
        cutPasteButton.setTooltip(new Tooltip("Click and release to cut out part of image. Click and drag into place"));
        copyPasteButton.setTooltip(new Tooltip("Click and release to copy part of image. Click and drag into place"));
        circleButton.setTooltip(new Tooltip("Draw a circle, drag and release"));
        squareButton.setTooltip(new Tooltip("Draw a square, drag and release"));
        rectangleButton.setTooltip(new Tooltip("Draw a rectangle, drag and release"));
        rectangleRoundedButton.setTooltip(new Tooltip("Draw a rectangle with rounded corners, drag and release"));
        ellipseButton.setTooltip(new Tooltip("Draw an ellipse with drag and release"));
        polygonButton.setTooltip(new Tooltip("Draw a regular polygon with sides input below, drag and release"));

        userVertices.setTooltip(new Tooltip("Enter integer greater than 2 for # of polygon sides"));
    }
    
    /**
     * Sets icons for all of the buttons, they load from Icons folder
     */
    public static void setButtonIcons(){
        //button icons
        String location = "File Didn't Load";
              try {
                  location = new File(
                          System.getProperty("user.dir") + File.separator + "Icons" + File.separator +"rectangle.png"
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
                          System.getProperty("user.dir") + File.separator + "Icons" + File.separator + "square.png"
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
                          System.getProperty("user.dir") + File.separator + "Icons" + File.separator + "circle.png"
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
                          System.getProperty("user.dir") + File.separator + "Icons" + File.separator + "erase.png"
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
                          System.getProperty("user.dir") + File.separator + "Icons" + File.separator + "copyPaste.png"
                  ).toURI().toURL().toExternalForm();
              } catch (MalformedURLException ex) {
                  Logger.getLogger(MenuPain.class.getName()).log(Level.SEVERE, null, ex);
              }
        copyPasteButton.setPrefSize(20, 20);
        img = new Image(location);
        ImageView viewCopyPaste = new ImageView(img);
        viewCopyPaste.setFitHeight(20);
        viewCopyPaste.setPreserveRatio(true);
        copyPasteButton.setGraphic(viewCopyPaste);
        
         location = "File Didn't Load";
              try {
                  location = new File(
                          System.getProperty("user.dir") + File.separator + "Icons" + File.separator + "cutPaste.png"
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
                          System.getProperty("user.dir") + File.separator + "Icons" + File.separator + "colorDrop.png"
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
                          System.getProperty("user.dir") + File.separator + "Icons" + File.separator + "text.png"
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
                          System.getProperty("user.dir") + File.separator + "Icons" + File.separator + "polygon.png"
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
                          System.getProperty("user.dir") + File.separator + "Icons" + File.separator + "ellipse.png"
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
                          System.getProperty("user.dir") + File.separator + "Icons" + File.separator + "roundRect.png"
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
                          System.getProperty("user.dir") + File.separator + "Icons" + File.separator + "lineDraw.png"
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
                          System.getProperty("user.dir") + File.separator + "Icons" + File.separator + "freeHand.png"
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
    }
    
}
