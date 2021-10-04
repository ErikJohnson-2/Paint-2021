/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pain.t.fx_2021;

import java.util.Stack;
import javafx.scene.image.WritableImage;

/**
 *
 * @author Erik
 */
public class UndoRedo {
 
     final Stack<WritableImage> redoStack ;
     final Stack<WritableImage> undoStack ; 
    
public UndoRedo (){
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    
}
    
    
public void pushChanges(WritableImage image) {
        //take canvas data
        //push canvas data to undo
        this.undoStack.push(image);    
    }    
    
public WritableImage popUndo() {
        //pop undo and load that data
        WritableImage data = this.undoStack.pop();
        //push data from undo to redo
        this.redoStack.push(data);
         //return img?
        return data;
    } 
public WritableImage popRedo() {
        //pop undo and load that data
        WritableImage data = this.redoStack.pop();
        //push data from undo to redo
        this.undoStack.push(data);
        return data;
    }
public void pushRedo(WritableImage image) {
        //take canvas data
        //push data to redo
        this.redoStack.push(image);
        
    }
//redundant with current implemention of pushChanges?
public void pushUndo(WritableImage image) {   
        //take canvas data
        //push data to undo 
        this.undoStack.push(image);    
    }

}
