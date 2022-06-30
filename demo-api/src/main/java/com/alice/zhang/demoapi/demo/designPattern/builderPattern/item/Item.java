package com.alice.zhang.demoapi.demo.designPattern.builderPattern.item;

import com.alice.zhang.demoapi.demo.designPattern.builderPattern.packing.Packing;

public interface Item {
    public String name();
    public Packing packing();
    public float price();
}
