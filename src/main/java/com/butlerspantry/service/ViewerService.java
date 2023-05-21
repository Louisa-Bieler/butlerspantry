package main.java.com.butlerspantry.service;

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

    public File chooseStarterFile() throws Exception;

    public void loadFileConfirmation(String fileName);


    public int viewOptions();

    public void viewCurrentPantry();

    public void viewCurrentRecipes();

    public void checkRecipe();

    public void persistShoppingList();

    public void addShoppingList();






}
