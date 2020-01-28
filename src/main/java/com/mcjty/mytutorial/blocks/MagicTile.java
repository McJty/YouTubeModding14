package com.mcjty.mytutorial.blocks;

import com.mcjty.mytutorial.setup.Registration;
import net.minecraft.tileentity.TileEntity;

public class MagicTile extends TileEntity {

    public MagicTile() {
        super(Registration.MAGICBLOCK_TILE.get());
    }
}
