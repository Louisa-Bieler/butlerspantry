package main.java.com.butlerspantry.service;

import main.java.com.butlerspantry.connection.WriteFile;
import main.java.com.butlerspantry.implementation.IngredientLogic;
import main.java.com.butlerspantry.implementation.IngredientValidationLogic;
import main.java.com.butlerspantry.implementation.UnitConversion;
import main.java.com.butlerspantry.logging.Logger;
import main.java.com.butlerspantry.model.FoodInventory;
import main.java.com.butlerspantry.model.Ingredient;

import java.io.File;
import java.time.Instant;
import java.util.Scanner;

public class DigitizedFoodInventoryServiceImplementation implements DigitizedFoodInventoryService {


    @Override
    public void chooseUnitConversionFile(File unitConversion) {

    }

    @Override
    public FoodInventory produceFoodInventoryFromFile(File pantryInventory) {
            FoodInventory newFoodInventory = new FoodInventory();
            try (Scanner scanner = new Scanner(pantryInventory).useDelimiter(",")) {
                while (scanner.hasNextLine()) {
                    Ingredient iterIngredient = IngredientLogic.returnIngredient(scanner.nextLine());
                    IngredientValidationLogic.ingredientNameValidator(iterIngredient.getName());
                    IngredientValidationLogic.ingredientUnitValidator(iterIngredient.getUnit());
                    IngredientValidationLogic.ingredientAmountValidator(iterIngredient.getAmount());
                    newFoodInventory.setIngredientFromIngredient(iterIngredient);
                }
                return newFoodInventory;
            } catch (Exception e) {
                Logger.logLater(e.getMessage());
                return new FoodInventory();
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

    static void addFoodToFoodInventory(FoodInventory toUpdate, FoodInventory additions) {
        additions.getInventory().forEach(
                (key, value) ->
                        toUpdate.getInventory().merge(
                                key, value, (value1, value2) ->
                                {
                                    value1.addAmountFromShopping(value2.getAmount());
                                    return value1;
                                }
                        )
        );
    }

    @Override
    public FoodInventory produceFoodInventoryFromUserInput(String userInput) {
        return null;
    }
}
