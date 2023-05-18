package main.java.com.butlerspantry.service;

import main.java.com.butlerspantry.implementation.FoodInventoryLogic;
import main.java.com.butlerspantry.implementation.UnitConversion;
import main.java.com.butlerspantry.model.FoodInventory;

import java.io.File;
import java.util.HashMap;

public interface DigitizedFoodInventoryService {

    void chooseUnitConversionFile(File unitConversion);

    //Recipes and Pantries are both FoodInventory objects
    FoodInventory produceFoodInventoryFromFile(File pantryInventory);

    boolean doIHaveEnoughFood(FoodInventory available, FoodInventory required);
    
    FoodInventory createShoppingList(FoodInventory available, FoodInventory required);
    
    void useFood(FoodInventory available, FoodInventory required);

    void saveFoodInventoryAsFileAutomaticName(FoodInventory saveToFile);

    void saveFoodInventoryAsFileUserSpecifiedName(FoodInventory saveToFile, String name);

    static void addFoodToFoodInventory(FoodInventory toUpdate, FoodInventory additions) {
    }

    FoodInventory produceFoodInventoryFromUserInput(String userInput);

}
