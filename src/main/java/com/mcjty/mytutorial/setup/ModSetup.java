package com.mcjty.mytutorial.setup;

import com.mcjty.mytutorial.blocks.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModSetup {

    public ItemGroup itemGroup = new ItemGroup("mytutorial") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.FIRSTBLOCK);
        }
    };

    public void init() {

    }
}
