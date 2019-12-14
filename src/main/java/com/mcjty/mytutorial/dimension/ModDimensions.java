package com.mcjty.mytutorial.dimension;

import com.mcjty.mytutorial.MyTutorial;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.registries.ObjectHolder;

public class ModDimensions {

    public static final ResourceLocation DIMENSION_ID = new ResourceLocation(MyTutorial.MODID, "dimension");

    @ObjectHolder("mytutorial:dimension")
    public static ModDimension DIMENSION;

    public static DimensionType DIMENSION_TYPE;
}
