package main.java.com.butlerspantry.logging;

import java.io.File;
import java.io.FileWriter;
import java.time.Instant;
import java.util.Date;

public class Logger {

    public static void logNowOrLater(String message, char flag){
        char now = 'n';
        char later = 'l';

        try {
            if(flag == now){
                Logger.logNow(message);
            }
            if(flag == later){
                Logger.logLater(message);
            }}
        catch (Exception e){
            System.out.println("Please run implementation with command line configuration of flag 'n' for a console log or 'l' for a log File");
        }
    }


    public static void logNow(String message){
        System.out.println(message);
    }

    public static void logLater(String message){
        // Java program to create a text File using FileWriter
        String fileName = Date.from(Instant.now()).toString().replace( ':','_');
        File logStash = new File(fileName);

        try(FileWriter fw = new FileWriter(logStash)) {

            // attach a file to FileWriter


            // read each character from string and write
            // into FileWriter
            for (int i = 0; i < message.length(); i++)
                fw.write(message.charAt(i));

            System.out.println("Logfile successfully written");

            // close the file

        }
        catch (Exception e) {
            e.getStackTrace();
        }

    }

}

