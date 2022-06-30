package com.alice.zhang.demoapi.demo.designPattern.builderPattern.item.burger;

import com.alice.zhang.demoapi.demo.designPattern.builderPattern.item.Item;
import com.alice.zhang.demoapi.demo.designPattern.builderPattern.packing.Packing;
import com.alice.zhang.demoapi.demo.designPattern.builderPattern.packing.Wrapper;

public abstract class Burger implements Item {
    @Override
    public Packing packing() {
        return new Wrapper();
    }

    @Override
    public abstract float price();
}
