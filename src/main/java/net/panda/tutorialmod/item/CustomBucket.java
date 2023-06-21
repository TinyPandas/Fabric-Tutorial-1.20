package net.panda.tutorialmod.item;

import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;

public class CustomBucket extends BucketItem {
    private final int primaryColor;

    public CustomBucket(int primaryColor, Item.Settings settings) {
        super(Fluids.WATER, settings);
        this.primaryColor = primaryColor;
    }

    public int getColor(int tintIndex) {
        return tintIndex == 1 ? primaryColor : -1;
    }
}
