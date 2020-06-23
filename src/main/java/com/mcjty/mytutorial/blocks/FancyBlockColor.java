package com.mcjty.mytutorial.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;

import javax.annotation.Nullable;

public class FancyBlockColor implements IBlockColor {
    @Override
    public int getColor(BlockState blockState, @Nullable ILightReader world, @Nullable BlockPos pos, int tint) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof FancyBlockTile) {
            FancyBlockTile fancy = (FancyBlockTile) te;
            BlockState mimic = fancy.getMimic();
            if (mimic != null) {
                return Minecraft.getInstance().getBlockColors().getColor(mimic, world, pos, tint);
            }
        }

        return -1;
    }
}
