package main.java.com.butlerspantry.connection;

import main.java.com.butlerspantry.logging.Logger;
import java.io.File;
import java.util.Date;

public class ReadFile {

    public static File readFile(String fileName) throws Exception {
        File newFileObject = new File(fileName);
        if (!newFileObject.isFile()) {
            throw new Exception("No such file found. Please check input!");
        }
        String logSuccess = "readFile Successful on " + new Date();
        Logger.logNow(logSuccess);
        return newFileObject;
    }
}



