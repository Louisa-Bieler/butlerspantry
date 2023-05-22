package main.java.com.butlerspantry.service;

import main.java.com.butlerspantry.connection.ReadFile;
import main.java.com.butlerspantry.connection.WriteFile;
import main.java.com.butlerspantry.implementation.IngredientLogic;
import main.java.com.butlerspantry.implementation.UnitConversion;
import main.java.com.butlerspantry.logging.Logger;
import main.java.com.butlerspantry.model.FoodInventory;
import main.java.com.butlerspantry.model.Ingredient;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.Scanner;

public class DigitizedFoodInventoryServiceImplementation implements DigitizedFoodInventoryService {



    public void chooseUnitConversionFile(File unitConversion) throws Exception {
        UnitConversion.addUnitConversionMapping("src/main/resources/unitConversions.csv");
    }

    @Override
    public FoodInventory produceFoodInventoryFromFile(File pantryInventory) {
        FoodInventory newFoodInventory = new FoodInventory();
        try {
            Scanner scanner = new Scanner(pantryInventory).useDelimiter(",");
            while (scanner.hasNextLine()) {
                Ingredient iterIngredient = IngredientLogic.returnIngredient(scanner.nextLine());
                newFoodInventory.setIngredientFromIngredient(iterIngredient);
            }
            return newFoodInventory;
        } catch (FileNotFoundException scanner) {
            Logger.logLater(scanner.getMessage());
            return newFoodInventory;
        }
    }

    @Override
    public boolean doIHaveEnoughFood(FoodInventory available, FoodInventory required) {
        boolean weHaveAllTheIngredients = available.getInventory().keySet().containsAll(required.getInventory().keySet());
        return weHaveAllTheIngredients;
    }

    @Override
    public FoodInventory createShoppingList(FoodInventory available, FoodInventory required) {
        FoodInventory shoppingList = new FoodInventory();
        required.getInventory().forEach(
                (recipeIngredientKey, recipeIngredient) ->
                {
                    if (!available.getInventory().keySet().contains(recipeIngredientKey)) {
                        shoppingList.setIngredientFromIngredient(recipeIngredient);
                    } else if (available.getIngredient(recipeIngredientKey).getAmount().compareTo(recipeIngredient.getAmount()) == 1) {
                        return;
                    } else if (available.getIngredient(recipeIngredientKey).getAmount().equals(recipeIngredient.getAmount())) {
                        return;
                    } else {
                        shoppingList.setIngredientFromIngredient(recipeIngredient);
                    }
                }
        );
        return shoppingList;
    }

    @Override
    public void useFood(FoodInventory available, FoodInventory required) {
        required.getInventory().forEach(
                (recipeIngredientKey, recipeIngredient) ->
                {
                    if (available.getIngredient(recipeIngredientKey).getAmount().compareTo(recipeIngredient.getAmount()) == 1) {
                        available.getIngredient(recipeIngredientKey).subtractAmountFromRecipe(recipeIngredient.getAmount());
                    } else {
                        available.removeEntireIngredient(recipeIngredientKey);
                    }
                }
        );
    }

    @Override
    public void saveFoodInventoryAsFileAutomaticName(FoodInventory toSave) {
        WriteFile.writeFoodInventory(toSave.toString(), "Food Inventory From " + Instant.now().toString());
    }

    @Override
    public void saveFoodInventoryAsFileUserSpecifiedName(FoodInventory toSave, String name) {
        WriteFile.writeFoodInventory(toSave.toString(), name);
    }

    @Override
    public void addFoodToFoodInventory(FoodInventory toUpdate, FoodInventory additions) {
        additions.getInventory().forEach(
                (key, ingredient) ->
                        toUpdate.getInventory().merge(
                                key, ingredient, (ingredientToUpdate, additionalIngredient) ->
                                {
                                    ingredientToUpdate.addAmountFromShopping(additionalIngredient.getAmount());
                                    return ingredientToUpdate;
                                }
                        )
        );
    }

    @Override
    public FoodInventory produceFoodInventoryFromUserInput(String userInput) {
        return null;
    }
}
