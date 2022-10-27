package com.alice.zhang.support.demo.designpattern.builderpattern.item.colddrink;

public class Coke extends ColdDrink {
    @Override
    public float price() {
        return 30.0f;
    }

    @Override
    public String name() {
        return "Coke";
    }
}
