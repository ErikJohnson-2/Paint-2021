/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pain.t.fx_2021;

import java.util.Stack;
import javafx.scene.image.WritableImage;

/**
* <h1>Maintains stacks to revert canvas changes</h1>
* Creates two stacks for a Tab
* Provides methods for push and pop
*
* @author  Erik Johnson
* @version 1.4
* @since   2021-10-07
*/

public class UndoRedo {
 
     final Stack<WritableImage> redoStack ;
     final Stack<WritableImage> undoStack ; 
    
    /**
     *
     */
    public UndoRedo (){
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    
}
    
    /**
* Changes occurred, push previous data to undo
     * @param image WritableImage 

*/
public void pushChanges(WritableImage image) {
        //take canvas data
        //push canvas data to undo
        this.undoStack.push(image);    
    }    

  /**
* User wants to undo
* Push data from undo to redoStack
* @return data as a WritableImage from the undoStack
*/  
public WritableImage popUndo() {
        //pop undo and load that data
        WritableImage data = this.undoStack.pop();
        //push data from undo to redo
        this.redoStack.push(data);
         //return img?
        return data;
    } 

 /**
* User wants to redo
* Push data from redo to undoStack
* @return data as a WritableImage from the redoStack
*/  
public WritableImage popRedo() {
        //pop undo and load that data
        WritableImage data = this.redoStack.pop();
        //push data from undo to redo
        this.undoStack.push(data);
        return data;
    }
/**
* Push image data to redo
     * @param image Writable Image from undo
*/ 
public void pushRedo(WritableImage image) {
        //take canvas data
        //push data to redo
        this.redoStack.push(image);
        
    }

/**
* Push image data to undo
     * @param image Writable Image from redo or ChangesMade
     
*/ 
//shouldn't this be deprecated/refactored?
public void pushUndo(WritableImage image) {   
        //take canvas data
        //push data to undo 
        this.undoStack.push(image);    
    }

}
