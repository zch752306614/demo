package com.alice.zhang.demoapi.demo.designPattern.builderPattern.item.coldDrink;

public class Pepsi extends ColdDrink {
    @Override
    public float price() {
        return 35.0f;
    }

    @Override
    public String name() {
        return "Pepsi";
    }
}
