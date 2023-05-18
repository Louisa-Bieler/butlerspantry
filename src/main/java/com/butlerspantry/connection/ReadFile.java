package main.java.com.butlerspantry.connection;

import main.java.com.butlerspantry.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class ReadFile {

    //TODO Test would give file that doesnt exist and see if the corret logs and throws happen
    // give good file
    // give bad file

    public static File readFile(String fileName) throws Exception {
        File myFoodInventory = new File(fileName);
        if (myFoodInventory.isFile()) {
            String logSuccess = "readFile Successful on " + new Date();
            Logger.logNow(logSuccess);
            return myFoodInventory;
        } else {
            throw new Exception("No such file found. Please check input!");
        }
    }

}



