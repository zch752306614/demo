package com.alice.zhang.support.module.common.designpattern.builderpattern.item.colddrink;

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
