package com.mcjty.mytutorial.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.block.AbstractBlock.Properties;

public class ComplexMultipartBlock extends Block {

    public static final EnumProperty<ComplexMultipartTile.Mode> NORTH = EnumProperty.create("north", ComplexMultipartTile.Mode.class);
    public static final EnumProperty<ComplexMultipartTile.Mode> SOUTH = EnumProperty.create("south", ComplexMultipartTile.Mode.class);
    public static final EnumProperty<ComplexMultipartTile.Mode> WEST = EnumProperty.create("west", ComplexMultipartTile.Mode.class);
    public static final EnumProperty<ComplexMultipartTile.Mode> EAST = EnumProperty.create("east", ComplexMultipartTile.Mode.class);
    public static final EnumProperty<ComplexMultipartTile.Mode> UP = EnumProperty.create("up", ComplexMultipartTile.Mode.class);
    public static final EnumProperty<ComplexMultipartTile.Mode> DOWN = EnumProperty.create("down", ComplexMultipartTile.Mode.class);

    private static final VoxelShape RENDER_SHAPE = VoxelShapes.box(0.1, 0.1, 0.1, 0.9, 0.9, 0.9);

    public ComplexMultipartBlock() {
        super(Properties.of(Material.METAL)
                .sound(SoundType.METAL)
                .strength(2.0f)
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> list, ITooltipFlag flags) {
        list.add(new TranslationTextComponent("message.complex_multipart"));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ComplexMultipartTile();
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return RENDER_SHAPE;
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (!world.isClientSide) {
            TileEntity te = world.getBlockEntity(pos);
            if (te instanceof ComplexMultipartTile) {
                ComplexMultipartTile dimensionalCellTileEntity = (ComplexMultipartTile) te;
                dimensionalCellTileEntity.toggleMode(result.getDirection());
            }
        }
        return super.use(state, world, pos, player, hand, result);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH, SOUTH, WEST, EAST, DOWN, UP);
    }
}
