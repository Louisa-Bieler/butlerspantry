package com.butlerpantry.model;

import java.math.RoundingMode;
import java.math.BigDecimal;

public class Ingredient implements com.butlerpantry.interfaces.Ingredient {
    private String name;
    private String unit;
    private BigDecimal amount;


//Constructor:
    public Ingredient(String name, String unit, BigDecimal amount) {
            this.name = name;
            this.unit = unit;
            this.amount = amount;
    }
//Getters and Setters:
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }
    public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

    public void addAmountFromShopping(BigDecimal amount) {
        this.amount = this.amount.add(amount).setScale(2, RoundingMode.HALF_UP);
    }

    public void subtractAmountFromRecipe(BigDecimal amount) {
        this.amount = this.amount.subtract(amount).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name).append(",").append(this.unit).append(",").append(this.amount.setScale(2, RoundingMode.HALF_UP).toString()).append("\n");
        return sb.toString();
    }
}