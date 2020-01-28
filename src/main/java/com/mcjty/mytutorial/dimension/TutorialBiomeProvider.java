package com.mcjty.mytutorial.dimension;

import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.feature.structure.Structure;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TutorialBiomeProvider extends BiomeProvider {

    private final Biome biome;
    private static final List<Biome> SPAWN = Collections.singletonList(Biomes.PLAINS);

    public TutorialBiomeProvider() {
        super(new HashSet<>(SPAWN));
        biome = Biomes.PLAINS;
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        return biome;
    }

    @Override
    public List<Biome> getBiomesToSpawnIn() {
        return SPAWN;
    }

    @Override
    public boolean hasStructure(Structure<?> structure) {
        return false;
    }

    @Override
    public Set<BlockState> getSurfaceBlocks() {
        if (topBlocksCache.isEmpty()) {
            topBlocksCache.add(biome.getSurfaceBuilderConfig().getTop());
        }

        return topBlocksCache;
    }
}
