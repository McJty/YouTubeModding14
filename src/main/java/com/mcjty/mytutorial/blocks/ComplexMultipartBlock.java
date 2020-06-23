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

public class ComplexMultipartBlock extends Block {

    public static final EnumProperty<ComplexMultipartTile.Mode> NORTH = EnumProperty.create("north", ComplexMultipartTile.Mode.class);
    public static final EnumProperty<ComplexMultipartTile.Mode> SOUTH = EnumProperty.create("south", ComplexMultipartTile.Mode.class);
    public static final EnumProperty<ComplexMultipartTile.Mode> WEST = EnumProperty.create("west", ComplexMultipartTile.Mode.class);
    public static final EnumProperty<ComplexMultipartTile.Mode> EAST = EnumProperty.create("east", ComplexMultipartTile.Mode.class);
    public static final EnumProperty<ComplexMultipartTile.Mode> UP = EnumProperty.create("up", ComplexMultipartTile.Mode.class);
    public static final EnumProperty<ComplexMultipartTile.Mode> DOWN = EnumProperty.create("down", ComplexMultipartTile.Mode.class);

    private static final VoxelShape RENDER_SHAPE = VoxelShapes.create(0.1, 0.1, 0.1, 0.9, 0.9, 0.9);

    public ComplexMultipartBlock() {
        super(Properties.create(Material.IRON)
                .sound(SoundType.METAL)
                .hardnessAndResistance(2.0f)
        );
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> list, ITooltipFlag flags) {
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
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return RENDER_SHAPE;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (!world.isRemote) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof ComplexMultipartTile) {
                ComplexMultipartTile dimensionalCellTileEntity = (ComplexMultipartTile) te;
                dimensionalCellTileEntity.toggleMode(result.getFace());
            }
        }
        return super.onBlockActivated(state, world, pos, player, hand, result);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(NORTH, SOUTH, WEST, EAST, DOWN, UP);
    }
}
