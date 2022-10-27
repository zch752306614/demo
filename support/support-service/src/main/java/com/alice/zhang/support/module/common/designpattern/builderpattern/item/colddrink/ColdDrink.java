package com.alice.zhang.support.module.common.designpattern.builderpattern.item.colddrink;

import com.alice.zhang.support.module.common.designpattern.builderpattern.item.Item;
import com.alice.zhang.support.module.common.designpattern.builderpattern.packing.Bottle;
import com.alice.zhang.support.module.common.designpattern.builderpattern.packing.Packing;

public abstract class ColdDrink implements Item {
    @Override
    public Packing packing() {
        return new Bottle();
    }

    @Override
    public abstract float price();
}
