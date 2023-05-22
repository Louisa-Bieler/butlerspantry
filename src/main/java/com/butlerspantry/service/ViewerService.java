package main.java.com.butlerspantry.service;

import main.java.com.butlerspantry.model.FoodInventory;
import main.java.com.butlerspantry.model.RecipeBook;
import org.junit.jupiter.api.function.Executable;

import java.io.File;

public interface ViewerService {

/*
run app
offer user option re:  which unit conversion to load
offer user option re:  which CSV pantry to load
offer user options:

1) see current pantry
2) check recipe (can make?)
    a) make recipe (update pantry to reflect food use)
    b) dont make recipe yet
3) check recipe (cant make?)
    a) output shopping list
    b) dont output shopping list (try something else)
4) add shopping to existing pantry




 */



    public int viewOptions();
    //TODO Make Menu with all further methods

    public String inputFile();
    //TODO Take user input as a string and give it to the controller to make into a File and validate, and then load into the current FoodInventory
    //TODO Tell the User that you are working on it

    public String inputRecipeFile();
    //TODO same as above but load into the recipe book instead
    //let them hit a specific key to go back to the main menu

    public void viewCurrentPantry(FoodInventory currentPantry);

    public int viewCurrentRecipes(RecipeBook current);
    //TODO view list of recipe names with a number next to each one
    //take user input to allow user to check the pantry for the ingredients to this recipe or go back to the main menu;

    void loadFileWithConfirmation(boolean resultOfValidations, String anyErrorMessages);

    public void viewCurrentShoppingList(FoodInventory shoppingList);

    public int checkRecipe(FoodInventory shoppingList);
    //display results of checking a recipe









}
