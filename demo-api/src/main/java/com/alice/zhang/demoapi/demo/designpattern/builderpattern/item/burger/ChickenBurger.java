package com.alice.zhang.demoapi.demo.designpattern.builderpattern.item.burger;

public class ChickenBurger extends Burger {
    @Override
    public float price() {
        return 50.5f;
    }

    @Override
    public String name() {
        return "Chicken Burger";
    }
}
