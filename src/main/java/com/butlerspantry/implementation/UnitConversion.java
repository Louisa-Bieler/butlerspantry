package main.java.com.butlerspantry.implementation;

import main.java.com.butlerspantry.logging.Logger;
import main.java.com.butlerspantry.connection.ReadFile;
import main.java.com.butlerspantry.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class UnitConversion {

    private static HashMap<String, String[]> unitConversionMapping = new HashMap<>();

    public static void addUnitConversionMapping(String fileName) throws Exception {
        File conversions = ReadFile.readFile(String.valueOf(fileName));
        try (Scanner scanner = new Scanner(conversions).useDelimiter("\n")) {
            while (scanner.hasNextLine()) {
                String mapping = scanner.next();
                String[] conversionsArray = mapping.split(",");
                unitConversionMapping.put(conversionsArray[0], new String[]{conversionsArray[1], conversionsArray[2]});
            }
        }catch (Exception e) {
                Logger.logLater(e.getMessage());
                throw new RuntimeException(e);
        }
    }

    public static String[] unitConversion(String line){
        String[] lineToConvert = line.split(",");
        String[] returnValue = unitConversionMapping.get(lineToConvert[1]);
        return returnValue;
    }
}
