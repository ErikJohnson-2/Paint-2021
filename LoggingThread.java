/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pain.t.fx_2021;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import static pain.t.fx_2021.PainTFX_2021.tabPane;

/**
* <h1>Threaded logger for output of logs and errors</h1>
* Implementation of threading for logging data
* Outputs logs to file system.
* Instantiated in PainTFX_2021
*
* @author  Erik Johnson
* @version 1.5
* @since   2021-10-12
*/
public class LoggingThread implements Runnable {

    static Logger logger;
    
    
    /**
* Instantiates log formatting and file location
* Names new file for this instance of the log with a timestamp
*/  
    @Override
    public void run() {
        //System.out.println("Hello from a thread!");
         //test logger
       logger = Logger.getLogger("MyLog");  
       FileHandler fh = null;  
       SimpleDateFormat format = new SimpleDateFormat("M-d_HHmmss"); 

        try {  

            // This block configure the logger with handler and formatter  
            fh = new FileHandler("I:\\cs250\\Pain(t)FX_2021\\test\\MyLogFile_" +
            format.format(Calendar.getInstance().getTime()) + ".log");
            logger.addHandler(fh);
            // the following statement is used to log any messages  
            //logger.info("My first log");  

        } catch (SecurityException | IOException e) {  
        }  
        fh.setFormatter(new SimpleFormatter());
        
    }

    /**
* Starts instance of thread
*/  
    public static void main() {
        (new Thread(new LoggingThread())).start();
    }
    
    /**
* Logs information about saves
* @param success a Boolean that defines whether the save failed
*/ 
     
    public static void logSave(Boolean success){
        if (success == true) {
            TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
            logger.info("Save to " + selectedTab.fileDest);
        } else {
        TabPain selectedTab = (TabPain) tabPane.getSelectionModel().getSelectedItem();
        logger.info("Save failed to " + selectedTab.fileDest);
        }
    }
    /**
* Logs information about tool changes
* @param toolName a string containing the name of the tool now in use
* @see ToolBarManager
*/ 
      public static void logToolChange(String toolName){
            logger.info("Tool Changed to "+toolName);
        
    }
      /**
* Logs unknown string without formatting
* @param anyInfo a string with unknown log information
*/ 
       public static void logGeneral(String anyInfo) {
       logger.info(anyInfo);
}
       
}