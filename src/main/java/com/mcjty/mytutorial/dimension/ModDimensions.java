package com.mcjty.mytutorial.dimension;

import com.mcjty.mytutorial.MyTutorial;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.Level;

public class ModDimensions {

    public static final ResourceKey<DimensionType> TUTDIM_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation(MyTutorial.MODID, "tutdim"));
    public static final ResourceKey<Level> TUTDIM = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(MyTutorial.MODID, "tutdim"));
}
