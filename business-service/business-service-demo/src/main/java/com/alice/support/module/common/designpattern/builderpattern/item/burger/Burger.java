package com.alice.support.module.common.designpattern.builderpattern.item.burger;

import com.alice.support.module.common.designpattern.builderpattern.item.Item;
import com.alice.support.module.common.designpattern.builderpattern.packing.Packing;
import com.alice.support.module.common.designpattern.builderpattern.packing.Wrapper;

public abstract class Burger implements Item {
    @Override
    public Packing packing() {
        return new Wrapper();
    }

    @Override
    public abstract float price();
}
