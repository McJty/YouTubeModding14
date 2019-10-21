package com.mcjty.mytutorial.datagen;

import com.mcjty.mytutorial.blocks.ModBlocks;
import net.minecraft.data.DataGenerator;

public class LootTables extends BaseLootTableProvider {

    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        lootTables.put(ModBlocks.FIRSTBLOCK, createStandardTable("firstblock", ModBlocks.FIRSTBLOCK));
        lootTables.put(ModBlocks.FANCYBLOCK, createStandardTable("fancyblock", ModBlocks.FANCYBLOCK));
    }
}
