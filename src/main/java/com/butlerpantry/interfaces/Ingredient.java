package com.butlerpantry.interfaces;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface Ingredient {

    public void addAmountFromShopping(BigDecimal amount);

    public void subtractAmountFromRecipe(BigDecimal amount);

}
