package com.alice.zhang.support.module.common.designpattern.builderpattern;

import com.alice.zhang.support.module.common.designpattern.builderpattern.item.burger.ChickenBurger;
import com.alice.zhang.support.module.common.designpattern.builderpattern.item.burger.VegBurger;
import com.alice.zhang.support.module.common.designpattern.builderpattern.item.colddrink.Coke;
import com.alice.zhang.support.module.common.designpattern.builderpattern.item.colddrink.Pepsi;

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
