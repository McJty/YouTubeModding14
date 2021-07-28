package com.mcjty.mytutorial.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class ComplexMultipartBlock extends Block implements EntityBlock {

    public static final EnumProperty<ComplexMultipartTile.Mode> NORTH = EnumProperty.create("north", ComplexMultipartTile.Mode.class);
    public static final EnumProperty<ComplexMultipartTile.Mode> SOUTH = EnumProperty.create("south", ComplexMultipartTile.Mode.class);
    public static final EnumProperty<ComplexMultipartTile.Mode> WEST = EnumProperty.create("west", ComplexMultipartTile.Mode.class);
    public static final EnumProperty<ComplexMultipartTile.Mode> EAST = EnumProperty.create("east", ComplexMultipartTile.Mode.class);
    public static final EnumProperty<ComplexMultipartTile.Mode> UP = EnumProperty.create("up", ComplexMultipartTile.Mode.class);
    public static final EnumProperty<ComplexMultipartTile.Mode> DOWN = EnumProperty.create("down", ComplexMultipartTile.Mode.class);

    private static final VoxelShape RENDER_SHAPE = Shapes.box(0.1, 0.1, 0.1, 0.9, 0.9, 0.9);

    public ComplexMultipartBlock() {
        super(Properties.of(Material.METAL)
                .sound(SoundType.METAL)
                .strength(2.0f)
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter reader, List<Component> list, TooltipFlag flags) {
        list.add(new TranslatableComponent("message.complex_multipart"));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ComplexMultipartTile(blockPos, blockState);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter reader, BlockPos pos) {
        return RENDER_SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!world.isClientSide) {
            BlockEntity te = world.getBlockEntity(pos);
            if (te instanceof ComplexMultipartTile) {
                ComplexMultipartTile dimensionalCellTileEntity = (ComplexMultipartTile) te;
                dimensionalCellTileEntity.toggleMode(result.getDirection());
            }
        }
        return super.use(state, world, pos, player, hand, result);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH, SOUTH, WEST, EAST, DOWN, UP);
    }
}
