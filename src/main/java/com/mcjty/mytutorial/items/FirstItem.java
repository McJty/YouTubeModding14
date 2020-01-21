package com.mcjty.mytutorial.items;

import com.mcjty.mytutorial.setup.ModSetup;
import net.minecraft.item.Item;

public class FirstItem extends Item {

    public FirstItem() {
        super(new Item.Properties()
                .maxStackSize(1)
                .group(ModSetup.ITEM_GROUP));
    }
}
