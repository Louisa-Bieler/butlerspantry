package main.java.com.butlerspantry.model;

import java.util.List;

public class RecipeBook {

    private List<FoodInventory> recipes;

    public List<FoodInventory> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<FoodInventory> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        recipes.forEach(foodInventory -> sb.append("\t"+ recipes.indexOf(foodInventory) + ": " + foodInventory.getNameIfRecipe() + ":\n\t" + foodInventory.toString()));
        return "RecipeBook:\n" + sb;
    }
}
