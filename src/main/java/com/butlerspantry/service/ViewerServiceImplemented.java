package main.java.com.butlerspantry.service;

import main.java.com.butlerspantry.model.FoodInventory;
import main.java.com.butlerspantry.model.RecipeBook;

import java.util.Scanner;

public class ViewerServiceImplemented implements ViewerService {

    private Scanner commandLineInput = new Scanner(System.in);

    @Override
    public int viewOptions() {

        StringBuilder sb = new StringBuilder();

        sb.append("\n=====Butler's Pantry=====\n\n");
        sb.append("\t1) View current pantry\n");
        sb.append("\t2) View current recipes\n");
        sb.append("\t3) View the current shopping list\n");
        sb.append("\t4) Add new pantry items\n");
        sb.append("\t5) Add new recipe\n");
        sb.append("\t6) Save and exit\n");
        sb.append("\t7) Exit without save\n\n");
        sb.append("Choose [1-7]: ");

        System.out.print(sb);

        String selection = commandLineInput.next();
        while (!selection.matches("[1-7]")) {
            System.out.print("Please choose correct choice [1-7]: ");
            selection = commandLineInput.next();
        }

        int choice = Integer.parseInt(selection);
        return choice;
    }

    @Override
    public String inputFile(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n========Welcome to the Butler's Pantry!=========\n\n");
        sb.append("Please enter an absolute path to your desired starting pantry.\n");
        sb.append("To use the program, each row in your CSV must be formatted similarly to the following:\n\tFlour,kg,1\n");
        sb.append("To utilize the last default pantry, just hit enter.");
        System.out.println(sb);

        return commandLineInput.next();
    }

    @Override
    //caller must check if String returned is empty, then call viewOptions()
    //otherwise caller must call ReadFile and if it is bad, then loadFileWithConfirmation should rpint already false
    //if it is good then caller must go on to validation service
    //if this is all good (or at all bad) the loadFileWithConfirmation should be called with true or false and then
    //break
    public String inputRecipeFile() {

        StringBuilder sb = new StringBuilder();
        sb.append("\n=====Butler's Pantry=====\n\n");
        sb.append("To return to the previous menu, hit enter.\nTo add a filepath, enter the absolute path to your recipe file here: ");
        sb.append("\nTo work as a recipe, your csv file must have a file name that is the name of the recipe, and otherwise have the same csv structure as a pantry.");
        System.out.println(sb);

        return commandLineInput.next();
    }

    @Override
    public void loadFileWithConfirmation(boolean resultOfValidations, String anyErrorMessages) {
        String errorMessage;
        if (anyErrorMessages == null) {
            errorMessage = "Sorry, no error messages to help you here.";
        } else {
            errorMessage = anyErrorMessages;
        }

           if (resultOfValidations) {
                System.out.println("Congratuations! Your File has loaded successfully!!");
            } else {
                System.out.println("Oops, that didn't work. Check your path and file contents and try again.");
                System.out.println("It might have something to do with this error message: " + errorMessage);
            }

        }


    @Override
    public void viewCurrentPantry(FoodInventory currentPantry) {
        System.out.println(currentPantry.toString());
    }

    @Override
    public int viewCurrentRecipes(RecipeBook current) {
        System.out.println(current.toString());
        System.out.println("Please select the integer id of the recipe you wish to prepare.");
        while (Integer.parseInt(commandLineInput.next()) > current.getRecipes().size()){
            System.out.println("Please select the integer id of the recipe you wish to prepare.");
        }
        return Integer.parseInt(commandLineInput.next());
    }

    @Override
    public void viewCurrentShoppingList(FoodInventory shoppingList) {
        System.out.println(shoppingList.toString());
    }

    @Override
    public int checkRecipe(FoodInventory differenceList) {
        System.out.println("Here's what you don't have that you still need to get the recipe:\n");
        System.out.println(differenceList.toString());
        System.out.println("If you want to add these items to your shopping list, press 1. Otherwise press 2.");
        String input = commandLineInput.next();
        while (!input.matches("[1-2]")){
            System.out.println("If you want to add these items to your main shopping list, press 1. Otherwise press 2.");
        }
        return Integer.parseInt(input);
    }


}
