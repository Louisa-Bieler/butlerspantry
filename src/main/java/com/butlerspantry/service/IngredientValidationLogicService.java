package main.java.com.butlerspantry.service;

import main.java.com.butlerspantry.connection.ReadFile;
import main.java.com.butlerspantry.implementation.IngredientLogic;
import main.java.com.butlerspantry.logging.Logger;
import main.java.com.butlerspantry.model.Ingredient;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Scanner;

public class IngredientValidationLogicService {

    public static boolean validateFile(File toTest) {
        try {
            Scanner scanner = new Scanner(toTest).useDelimiter(",");
            while (scanner.hasNextLine()) {
                Ingredient iterIngredient = IngredientLogic.returnIngredient(scanner.nextLine());
                ingredientNameValidator(iterIngredient.getName());
                ingredientAmountValidator(iterIngredient.getAmount());
                ingredientUnitValidator(iterIngredient.getUnit());
            }
            return true;
        } catch (FileNotFoundException scanner){
            Logger.logLater(scanner.getMessage());
            return false;
        } catch (IllegalArgumentException any){
            Logger.logLater(any.getMessage());
            return false;
        }
    }

    public static boolean validateString(String toTest) {
        try {
            Ingredient ingredientToTest = IngredientLogic.returnIngredient(toTest);
            ingredientNameValidator(ingredientToTest.getName());
            ingredientAmountValidator(ingredientToTest.getAmount());
            ingredientUnitValidator(ingredientToTest.getUnit());
            return true; }
        catch (IllegalArgumentException any) {
            Logger.logLater(any.getMessage());
            return false;
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
