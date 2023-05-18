package main.java.com.butlerspantry.implementation;

import main.java.com.butlerspantry.model.Ingredient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IngredientLogicTest {

    @Test
    void testReturnIngredientHappyPath() throws Exception {
        String happyPathIngredientString = "Flour,g,1000";
        Ingredient testIngredient = IngredientLogic.returnIngredient(happyPathIngredientString);
        String testIngredientToString = testIngredient.toString();
        assertTrue(testIngredientToString.contains(happyPathIngredientString));
    }

    @Test
    void testReturnIngredientBadString() {
        String badString = "5.0,Sz||\\\\,glory";
        Exception badStringException = assertThrows(Exception.class, () -> {
            Ingredient willNotCome = IngredientLogic.returnIngredient(badString);
        });
        String expectedMessage = "glory";
        String actualMessage = badStringException.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }
}


