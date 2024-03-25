package com.butlerpantry.interfaces;

import com.butlerpantry.logging.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Scanner;

public interface InputFileValidator {

    public static void filePathIsValid(String filepath) throws IllegalArgumentException {
        try {
            File file = new File(filepath);
            String logSuccess = "FilePath Is Valid at " + new Date();
            Logger.logNow(logSuccess);
        } catch (IllegalArgumentException e){
            Logger.logNow("FilePath is not valid because of " + e.getMessage() + " , at " + new Date());
            throw new IllegalArgumentException(e);
        }
    }

    public static void fileContainsData(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file).useDelimiter(",");
        try {

        }
    }
    public static void ingredientNameValidator(String name) throws IllegalArgumentException {

        if (name.isEmpty()){
            throw new IllegalArgumentException("Ingredient name is blank. Check input!");
        } else if (name.equals(null)){
            throw new IllegalArgumentException("Ingredient name was null. Check input!");
        } else if (name.matches("\\d+")) {
            throw new IllegalArgumentException("Ingredient name can not be a number. Check input!");
            //} else if (!(name.("[a-zA-Z]{1}+"))) {
            //throw new IllegalArgumentException("Ingredient name contains no letters. Check input!");
            //TODO find regex that means AT LEAST ONE LETTER
        }
    }

    public static void ingredientUnitValidator(String unit) throws IllegalArgumentException {
        if (unit.isEmpty()){
            throw new IllegalArgumentException("Ingredient unit is blank. Check input!");
        }
    }

    public static void ingredientAmountValidator(BigDecimal amount) throws IllegalArgumentException {
        if (amount.compareTo(BigDecimal.valueOf(0))<0) {
            throw new IllegalArgumentException("Input amount cannot be a negative number");
        }
    }
}
