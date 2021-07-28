package com.mcjty.mytutorial.items;

import com.mcjty.mytutorial.setup.ModSetup;
import com.mcjty.mytutorial.setup.Registration;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BaseSpawner;
import net.minecraftforge.common.util.Constants;

import java.util.Objects;

public class WeirdMobEggItem extends Item {

    public WeirdMobEggItem() {
        super(new Item.Properties()
                .stacksTo(1)
                .tab(ModSetup.ITEM_GROUP));
    }

    /**
     * Called when this item is used when targetting a Block
     */
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemstack = context.getItemInHand();
            BlockPos blockpos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockstate = world.getBlockState(blockpos);
            BlockEntity tileentity = world.getBlockEntity(blockpos);
            if (tileentity instanceof SpawnerBlockEntity) {
                BaseSpawner abstractspawner = ((SpawnerBlockEntity) tileentity).getSpawner();
                abstractspawner.setEntityId(Registration.WEIRDMOB.get());
                tileentity.setChanged();
                world.sendBlockUpdated(blockpos, blockstate, blockstate, Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
                itemstack.shrink(1);
                return InteractionResult.SUCCESS;
            }

            BlockPos blockpos1;
            if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            } else {
                blockpos1 = blockpos.relative(direction);
            }

            if (Registration.WEIRDMOB.get().spawn((ServerLevel) world, itemstack, context.getPlayer(), blockpos1, MobSpawnType.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP) != null) {
                itemstack.shrink(1);
            }

            return InteractionResult.SUCCESS;
        }
    }

}
