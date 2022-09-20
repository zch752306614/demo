package com.alice.zhang.demoapi.demo.designpattern.builderpattern.item;

import com.alice.zhang.demoapi.demo.designpattern.builderpattern.packing.Packing;

public interface Item {
    public String name();
    public Packing packing();
    public float price();
}
