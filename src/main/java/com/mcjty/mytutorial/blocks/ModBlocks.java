package com.mcjty.mytutorial.blocks;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

    @ObjectHolder("mytutorial:firstblock")
    public static FirstBlock FIRSTBLOCK;

    @ObjectHolder("mytutorial:firstblock")
    public static TileEntityType<FirstBlockTile> FIRSTBLOCK_TILE;
}
