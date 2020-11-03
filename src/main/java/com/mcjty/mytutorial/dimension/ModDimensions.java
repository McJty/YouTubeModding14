package com.mcjty.mytutorial.dimension;

import com.mcjty.mytutorial.MyTutorial;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class ModDimensions {

    public static final RegistryKey<DimensionType> TUTDIM_TYPE = RegistryKey.getOrCreateKey(Registry.DIMENSION_TYPE_KEY, new ResourceLocation(MyTutorial.MODID, "tutdim"));
    public static final RegistryKey<World> TUTDIM = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(MyTutorial.MODID, "tutdim"));
}
