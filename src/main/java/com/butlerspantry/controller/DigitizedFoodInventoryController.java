package main.java.com.butlerspantry.controller;

import main.java.com.butlerspantry.connection.ReadFile;
import main.java.com.butlerspantry.logging.Logger;
import main.java.com.butlerspantry.model.FoodInventory;
import main.java.com.butlerspantry.model.RecipeBook;
import main.java.com.butlerspantry.service.DigitizedFoodInventoryServiceImplementation;
import main.java.com.butlerspantry.service.IngredientValidationLogicService;
import main.java.com.butlerspantry.service.ViewerService;

import java.io.File;
import java.io.FileNotFoundException;

public class DigitizedFoodInventoryController {

    private DigitizedFoodInventoryServiceImplementation session = new DigitizedFoodInventoryServiceImplementation();
    private FoodInventory mainPantry;
    private ViewerService viewerService;

    private RecipeBook recipes;

    private FoodInventory shoppingList;
/*
initialise app (run viewer service)
get input re: unit conversion option and load file
*/
    public DigitizedFoodInventoryController(FoodInventory currentPantry, ViewerService viewerService){
        this.viewerService = viewerService;
        this.mainPantry = currentPantry;
    }

    public String displayMainPantry(){
        return mainPantry.toString();
    }

    public void setMainPantry(){
        String filePath = viewerService.inputFile();
        try {
            File newPantry  = ReadFile.readFile(filePath);
            boolean validationResult = IngredientValidationLogicService.validateFile(newPantry);

        } catch (FileNotFoundException exceptionFromReadFile) {
            viewerService.loadFileWithConfirmation(false, exceptionFromReadFile.getMessage());
        }
    }




    public ViewerService getViewerService() {
        return viewerService;
    }

    public void setViewerService(ViewerService viewerService) {
        this.viewerService = viewerService;
    }

    public FoodInventory getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(FoodInventory shoppingList) {
        this.shoppingList = shoppingList;
    }

    public String getRecipes() {
        return recipes.toString();
    }

    /*public void run() {

    try {
        File currentPantry = viewerService();
    } catch (Exception e) {
        Logger.logLater(e.getMessage());

    }*/
}

