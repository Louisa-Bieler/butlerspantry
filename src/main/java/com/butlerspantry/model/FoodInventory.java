package main.java.com.butlerspantry.model;



import java.lang.String;
import java.util.HashMap;
import java.util.Iterator;


public class FoodInventory {
    private HashMap<String, Ingredient> inventory;

    public String getNameIfRecipe() {
        return nameIfRecipe;
    }

    public void setNameIfRecipe(String nameIfRecipe) {
        this.nameIfRecipe = nameIfRecipe;
    }

    private String nameIfRecipe;

    public FoodInventory() {
        this.inventory = new HashMap<String, Ingredient>();
    }

    public HashMap<String, Ingredient> getInventory() {
        return inventory;
    }

    public void setInventory(HashMap<String, Ingredient> inventory) {
        this.inventory = inventory;
    }

    public void setIngredientFromIngredient(Ingredient ingredientToAdd) {
        if (this.inventory.containsKey(ingredientToAdd.getName()+ingredientToAdd.getUnit())) {
            this.inventory.get(ingredientToAdd.getName()+ingredientToAdd.getUnit()).addAmountFromShopping(ingredientToAdd.getAmount());
        } else {
            this.inventory.put(ingredientToAdd.getName()+ingredientToAdd.getUnit(), ingredientToAdd);
        }
    }

    public void removeEntireIngredient(String name) {
        this.inventory.remove(name);
    }

    public void replaceIngredient(Ingredient replacement) {
        this.inventory.replace(replacement.getName()+replacement.getUnit(), replacement);
    }
    public Ingredient getIngredient(String ingredientKey) {
        return this.inventory.get(ingredientKey);
    }

    @Override
    public String toString() {
        Iterator<Ingredient> ingredientIterator = this.inventory.values().iterator();
        StringBuilder sb = new StringBuilder();
        while (ingredientIterator.hasNext()) {
            sb.append(ingredientIterator.next().toString());
        }
        return sb.toString();
    }



}
