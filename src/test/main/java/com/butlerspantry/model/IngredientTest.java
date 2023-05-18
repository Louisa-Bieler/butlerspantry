package main.java.com.butlerspantry.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientTest {

    Ingredient testIngredient = new Ingredient("Flour", "g", BigDecimal.valueOf(2000));


    @Test
    void testSetAmountHappyPath() {
        testIngredient.setAmount(BigDecimal.valueOf(1000));
        assertEquals(BigDecimal.valueOf(1000), testIngredient.getAmount());
    }

    @Test
    public void testAddAmountFromShopping() {
        Ingredient testIngredient = new Ingredient("Flour", "g", BigDecimal.valueOf(1000));
        testIngredient.addAmountFromShopping(BigDecimal.valueOf(500));
        assertEquals(BigDecimal.valueOf(1500).setScale(2), testIngredient.getAmount());
    }


    @Test
    void subtractAmountFromRecipeHappyPath() {
        testIngredient.subtractAmountFromRecipe(BigDecimal.valueOf(500));
        assertEquals(BigDecimal.valueOf(1500.00).setScale(2), testIngredient.getAmount());

    }

    @Test
    void testToString() {
        Assertions.assertEquals("Flour,g,2000.00\n", testIngredient.toString());
    }
}
