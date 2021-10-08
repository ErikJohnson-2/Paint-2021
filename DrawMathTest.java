/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pain.t.fx_2021;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Erik
 */
public class DrawMathTest {
    
    public DrawMathTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of mathRectangle method, of class DrawMath.
     */
    @Test
    public void testMathRectangle() {
        System.out.println("mathRectangle");
        Double lineFirstX = 1.0;
        Double lineFirstY = 2.0;
        Double currentX = 3.0;
        Double currentY = 4.0;
        double[] expResult = {1.0, 2.0, 2.0, 2.0};
        double[] result = DrawMath.mathRectangle(lineFirstX, lineFirstY, currentX, currentY);
        
        for (int i = 0; i < result.length; i++) {
            double expDouble = expResult[i];
            double recievedDouble = result[i];
            assertEquals(expDouble, recievedDouble, 0.0);
            
        }
       
    }

    /**
     * Test of mathPolygon method, of class DrawMath.
     */
    @Test
    public void testMathPolygon() {
        System.out.println("mathPolygon");
        Double lineFirstX = 2.0;
        Double lineFirstY = 2.0;
        Double currentX = 4.0;
        Double currentY = 0.0;
        double[] xValues = {4.0,4.0,0.0,0.0};
        double[] yValues = {0.0,4.0,4.0,0.0};
        int vertices = 4;
        ArrayList<double[]> expResult = new ArrayList<>();
        expResult.add(xValues);
        expResult.add(yValues);
        
        ArrayList<double[]> result = DrawMath.mathPolygon(lineFirstX, lineFirstY, currentX, currentY, vertices);
        for (int i = 0; i < result.size(); i++) {
            double[] expDouble = expResult.get(i);
            double[] recievedDouble = result.get(i);
            assertArrayEquals(expDouble, recievedDouble, 0.01);
            
        }
       
    }

    /**
     * Test of mathCircle method, of class DrawMath.
     */
    @Test
    public void testMathCircle() {
        System.out.println("mathCircle");
        Double lineFirstX = 1.0;
        Double lineFirstY = 1.0;
        Double currentX = 4.0;
        Double currentY = 4.0;
        double[] expResult = {-2, -2, 6, 6};
        double[] result = DrawMath.mathCircle(lineFirstX, lineFirstY, currentX, currentY);
        
        for (int i = 0; i < result.length; i++) {
            double expDouble = expResult[i];
            double recievedDouble = result[i];
            assertEquals(expDouble, recievedDouble, 0.0);
        }

    }
    
}
