package com.alice.zhang.demoapi.demo.designPattern.builderPattern;

import com.alice.zhang.demoapi.demo.designPattern.builderPattern.item.burger.ChickenBurger;
import com.alice.zhang.demoapi.demo.designPattern.builderPattern.item.burger.VegBurger;
import com.alice.zhang.demoapi.demo.designPattern.builderPattern.item.coldDrink.Coke;
import com.alice.zhang.demoapi.demo.designPattern.builderPattern.item.coldDrink.Pepsi;

public class MealBuilder {
    public Meal prepareVegMeal() {
        Meal meal = new Meal();
        meal.addItem(new VegBurger());
        meal.addItem(new Coke());
        return meal;
    }

    public Meal prepareNonVegMeal() {
        Meal meal = new Meal();
        meal.addItem(new ChickenBurger());
        meal.addItem(new Pepsi());
        return meal;
    }
}
