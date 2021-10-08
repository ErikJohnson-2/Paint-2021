/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pain.t.fx_2021;

import java.util.ArrayList;

/**
* <h1>DrawMath</h1>
* Methods for Draw Tools
* Abstracts and cleans original implementation
*
* @author  Erik Johnson
* @version 1.4
* @since   2021-10-07
* @see ToolBarManager
*/
public class DrawMath {

    /**
* Returns an array of four doubles that are calculated
* from user's input while drawing.
     * @param lineFirstX Double of user's mouse press to start draw
     * @param lineFirstY Double of user's mouse press to start draw
     * @param currentX Double of user's mouse location when this is called
     * @param currentY Double of user's mouse location when this is called
* @return double_array of Upper X+Y Coordinates, width, and height
* @see ToolBarManager
*/  
    public static double[] mathRectangle(Double lineFirstX, Double lineFirstY, Double currentX, Double currentY){ 
            double upperLeftX;
            double upperLeftY;
            double width;
            double height;
            double[] double_array = new double[4];
            
            
            
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
            
            double_array[0] = upperLeftX;
            double_array[1] = upperLeftY;
            double_array[2] = width;
            double_array[3] = height;
            
            return double_array;
            
    }
    
  
/**
* Returns an array of two paired double ArrayLists
* derived from user's input while drawing.
     * @param lineFirstX Double of user's mouse press to start draw
     * @param lineFirstY Double of user's mouse press to start draw
     * @param currentX Double of user's mouse location when this is called
     * @param currentY Double of user's mouse location when this is called
     * @param vertices Int from userInput field, ranges from 3+
* @return double_array_list
* @see ToolBarManager
*/           
    public static ArrayList<double[]> mathPolygon(Double lineFirstX, Double lineFirstY, Double currentX, Double currentY, int vertices){
             ArrayList<double[]> double_array_list = new ArrayList<>();
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
                
                double_array_list.add(xValues);
                double_array_list.add(yValues);
                return double_array_list;

        }
     
    
   /**
* Returns an array of doubles for X+Y coordinates and diameter.
* Diameter is later passed as both height and width.
* Parameters derived from user's input while drawing.
     * @param lineFirstX Double of user's mouse press to start draw
     * @param lineFirstY Double of user's mouse press to start draw
     * @param currentX Double of user's mouse location when this is called
     * @param currentY Double of user's mouse location when this is called
* @return double_array 
* @see ToolBarManager
*/  
     public static double[] mathCircle(Double lineFirstX, Double lineFirstY, Double currentX, Double currentY){

            double[] double_array = new double[3];

            double radiusX = Math.abs(lineFirstX - currentX);
            double radiusY = Math.abs(lineFirstY - currentY);      
            double a,b,diameter = 0;   
                    
                if (radiusX > radiusY) { //the user dragged more horizontally than vertically
                    a = lineFirstX - radiusX;
                    b = lineFirstY - radiusX;
                    diameter = 2 * radiusX;
                    
                    
                } else { //the user dragged more vertically than horizontally
                     a = lineFirstX - radiusY;
                     b = lineFirstY - radiusY;
                     diameter =  2 * radiusY;   
                }
                double_array[0] = a;
                double_array[1] = b;
                double_array[2] = diameter;

                return double_array;
}
}
    

