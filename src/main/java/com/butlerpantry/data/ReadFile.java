package com.butlerpantry.data;

import com.butlerpantry.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class ReadFile {

    //TODO Test would give file that doesnt exist and see if the corret logs and throws happen
    // give good file
    // give bad file

    public static File readFile(String fileName) {
            File myPantry = new File(fileName);
            String logSuccess = "readFile Successful on " + new Date();
            Logger.logNow(logSuccess);
            return myPantry;
            }

    }





