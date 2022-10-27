package com.alice.zhang.support.module.common.designpattern.builderpattern.item;

import com.alice.zhang.support.module.common.designpattern.builderpattern.packing.Packing;

public interface Item {
    public String name();
    public Packing packing();
    public float price();
}
