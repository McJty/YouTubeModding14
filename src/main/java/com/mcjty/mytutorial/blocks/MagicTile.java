package com.mcjty.mytutorial.blocks;

import com.mcjty.mytutorial.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class MagicTile extends BlockEntity {

    public MagicTile(BlockPos pos, BlockState state) {
        super(Registration.MAGICBLOCK_TILE.get(), pos, state);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(getBlockPos(), getBlockPos().offset(1, 3, 1));
    }
}
