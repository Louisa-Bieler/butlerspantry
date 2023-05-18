package main.java.com.butlerspantry.implementation;

import main.java.com.butlerspantry.connection.WriteFile;
import main.java.com.butlerspantry.logging.Logger;
import main.java.com.butlerspantry.model.Ingredient;
import main.java.com.butlerspantry.model.FoodInventory;

import java.io.File;
import java.time.Instant;
import java.util.Scanner;

public class FoodInventoryLogic {

    public static FoodInventory produceFoodInventoryFromFile(File defaultFoodInventoryFile) throws Exception {
        FoodInventory newFoodInventory = new FoodInventory();
        try (Scanner scanner = new Scanner(defaultFoodInventoryFile).useDelimiter(",")) {
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
            throw e;
        }
    }

    public static boolean doIHaveEnoughFood(FoodInventory available, FoodInventory required){
        boolean weHaveAllTheIngredients = available.getInventory().keySet().containsAll(required.getInventory().keySet());
        return weHaveAllTheIngredients;
    }

    public static FoodInventory createShoppingList(FoodInventory toUpdate, FoodInventory recipe) {
        FoodInventory shoppingList = new FoodInventory();
        recipe.getInventory().forEach(
                (recipeIngredientKey, recipeIngredient) ->
                {
                    if (!toUpdate.getInventory().keySet().contains(recipeIngredientKey)) {
                        shoppingList.setIngredientFromIngredient(recipeIngredient);
                    } else if (toUpdate.getIngredient(recipeIngredientKey).getAmount().compareTo(recipeIngredient.getAmount()) == 1) {
                        return;
                    } else if (toUpdate.getIngredient(recipeIngredientKey).getAmount().equals(recipeIngredient.getAmount())) {
                        return;
                    } else {
                        shoppingList.setIngredientFromIngredient(recipeIngredient);
                    }
                }
        );
        return shoppingList;
    }

    public static void useFood(FoodInventory toUpdate, FoodInventory recipe) {
        recipe.getInventory().forEach(
                (recipeIngredientKey, recipeIngredient) ->
                {
                    if (toUpdate.getIngredient(recipeIngredientKey).getAmount().compareTo(recipeIngredient.getAmount()) == 1) {
                        toUpdate.getIngredient(recipeIngredientKey).subtractAmountFromRecipe(recipeIngredient.getAmount());
                    } else {
                        toUpdate.removeEntireIngredient(recipeIngredientKey);
                    }
                }
        );
    }

    public static void saveShoppingListAsAFile(FoodInventory shoppingList) {
        WriteFile.writeFoodInventory(shoppingList.toString(), "ShoppingList_From_" + Instant.now().toString());
        }


        public static void addFoodToFoodInventory (FoodInventory toUpdate, FoodInventory entries){
            entries.getInventory().forEach(
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
    }

