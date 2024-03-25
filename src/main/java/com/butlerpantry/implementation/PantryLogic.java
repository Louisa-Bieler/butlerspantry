package com.butlerpantry.implementation;

import com.butlerpantry.data.WriteFile;
import com.butlerpantry.logging.Logger;
import com.butlerpantry.model.Ingredient;
import com.butlerpantry.model.Pantry;

import java.io.File;
import java.time.Instant;
import java.util.Scanner;

public class PantryLogic {

    public static Pantry producePantryFromFile(File defaultPantryFile) throws Exception {
        Pantry newPantry = new Pantry();
        try (Scanner scanner = new Scanner(defaultPantryFile).useDelimiter(",")) {
            while (scanner.hasNextLine()) {
                Ingredient iterIngredient = IngredientLogic.returnIngredient(scanner.nextLine());
                IngredientValidationLogic.ingredientNameValidator(iterIngredient.getName());
                IngredientValidationLogic.ingredientUnitValidator(iterIngredient.getUnit());
                IngredientValidationLogic.ingredientAmountValidator(iterIngredient.getAmount());
                newPantry.setIngredientFromIngredient(iterIngredient);
            }
            return newPantry;
        } catch (Exception e) {
            Logger.logLater(e.getMessage());
            throw e;
        }
    }

    public static boolean checkRecipeAgainstPantry(Pantry toUpdate, Pantry recipe){
        boolean weHaveAllTheIngredients = toUpdate.getInventory().keySet().containsAll(recipe.getInventory().keySet());
        return weHaveAllTheIngredients;
    }

    public static Pantry createShoppingList(Pantry toUpdate, Pantry recipe) {
        Pantry shoppingList = new Pantry();
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

    public static void subtractRecipeFromPantry(Pantry toUpdate, Pantry recipe) {
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

    public static void saveShoppingListAsAFile(Pantry shoppingList) {
            WriteFile.writeShoppingListToCSV(shoppingList.toString(), "ShoppingList_From_" + Instant.now().toString());
        }


        public static void addShopping (Pantry toUpdate, Pantry entries){
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

