package com.mcjty.mytutorial.blocks;

import com.mcjty.mytutorial.setup.Registration;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class FancyBlock extends Block implements EntityBlock {

    private static final VoxelShape SHAPE = Shapes.box(.2, .2, .2, .8, .8, .8);

    public FancyBlock() {
        super(Properties.of(Material.METAL)
                .sound(SoundType.METAL)
                .strength(2.0f)
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter reader, List<Component> list, TooltipFlag flags) {
        list.add(new TranslatableComponent("message.fancyblock"));
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        BlockEntity te = world.getBlockEntity(pos);
        if (te instanceof FancyBlockTile) {
            BlockState mimic = ((FancyBlockTile) te).getMimic();
            if (mimic != null && mimic.getBlock() != Registration.FANCYBLOCK.get()) {
                return mimic.getLightEmission(world, pos);
            }
        }
        return super.getLightEmission(state, world, pos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
        BlockEntity te = reader.getBlockEntity(pos);
        if (te instanceof FancyBlockTile) {
            BlockState mimic = ((FancyBlockTile) te).getMimic();
            if (mimic != null && mimic.getBlock() != Registration.FANCYBLOCK.get()) {
                return mimic.getShape(reader, pos, context);
            }
        }
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FancyBlockTile(blockPos, blockState);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
        ItemStack item = player.getItemInHand(hand);
        if (!item.isEmpty() && item.getItem() instanceof BlockItem) {
            if (!world.isClientSide) {
                BlockEntity te = world.getBlockEntity(pos);
                if (te instanceof FancyBlockTile) {
                    BlockState mimicState = ((BlockItem) item.getItem()).getBlock().defaultBlockState();
                    if (mimicState.getBlock() != this) {
                        ((FancyBlockTile) te).setMimic(mimicState);
                    } else {
                        ((FancyBlockTile) te).setMimic(null);
                    }
                }
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(state, world, pos, player, hand, trace);
    }
}
