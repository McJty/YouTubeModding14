package com.mcjty.mytutorial.setup;

import com.mcjty.mytutorial.MyTutorial;
import com.mcjty.mytutorial.blocks.*;
import com.mcjty.mytutorial.dimension.TutorialModDimension;
import com.mcjty.mytutorial.entities.WeirdMobEntity;
import com.mcjty.mytutorial.items.FirstItem;
import com.mcjty.mytutorial.items.WeirdMobEggItem;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.mcjty.mytutorial.MyTutorial.MODID;

public class Registration {

    private static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<TileEntityType<?>> TILES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, MODID);
    private static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, MODID);
    private static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, MODID);
    private static final DeferredRegister<ModDimension> DIMENSIONS = new DeferredRegister<>(ForgeRegistries.MOD_DIMENSIONS, MODID);

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        DIMENSIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<FirstBlock> FIRSTBLOCK = BLOCKS.register("firstblock", FirstBlock::new);
    public static final RegistryObject<Item> FIRSTBLOCK_ITEM = ITEMS.register("firstblock", () -> new BlockItem(FIRSTBLOCK.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<TileEntityType<FirstBlockTile>> FIRSTBLOCK_TILE = TILES.register("firstblock", () -> TileEntityType.Builder.create(FirstBlockTile::new, FIRSTBLOCK.get()).build(null));

    public static final RegistryObject<ContainerType<FirstBlockContainer>> FIRSTBLOCK_CONTAINER = CONTAINERS.register("firstblock", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new FirstBlockContainer(windowId, MyTutorial.proxy.getClientWorld(), pos, inv, MyTutorial.proxy.getClientPlayer());
    }));

    public static final RegistryObject<FancyBlock> FANCYBLOCK = BLOCKS.register("fancyblock", FancyBlock::new);
    public static final RegistryObject<Item> FANCYBLOCK_ITEM = ITEMS.register("fancyblock", () -> new BlockItem(FANCYBLOCK.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<TileEntityType<FancyBlockTile>> FANCYBLOCK_TILE = TILES.register("fancyblock", () -> TileEntityType.Builder.create(FancyBlockTile::new, FANCYBLOCK.get()).build(null));

    public static final RegistryObject<MagicBlock> MAGICBLOCK = BLOCKS.register("magicblock", MagicBlock::new);
    public static final RegistryObject<Item> MAGICBLOCK_ITEM = ITEMS.register("magicblock", () -> new BlockItem(MAGICBLOCK.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<TileEntityType<MagicTile>> MAGICBLOCK_TILE = TILES.register("magicblock", () -> TileEntityType.Builder.create(MagicTile::new, MAGICBLOCK.get()).build(null));

    public static final RegistryObject<ComplexMultipartBlock> COMPLEX_MULTIPART = BLOCKS.register("complex_multipart", ComplexMultipartBlock::new);
    public static final RegistryObject<Item> COMPLEX_MULTIPART_ITEM = ITEMS.register("complex_multipart", () -> new BlockItem(COMPLEX_MULTIPART.get(), new Item.Properties().group(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<TileEntityType<ComplexMultipartTile>> COMPLEX_MULTIPART_TILE = TILES.register("complex_multipart", () -> TileEntityType.Builder.create(ComplexMultipartTile::new, COMPLEX_MULTIPART.get()).build(null));

    public static final RegistryObject<FirstItem> FIRSTITEM = ITEMS.register("firstitem", FirstItem::new);
    public static final RegistryObject<WeirdMobEggItem> WEIRDMOB_EGG = ITEMS.register("weirdmob_egg", WeirdMobEggItem::new);

    public static final RegistryObject<EntityType<WeirdMobEntity>> WEIRDMOB = ENTITIES.register("weirdmob", () -> EntityType.Builder.create(WeirdMobEntity::new, EntityClassification.CREATURE)
            .size(1, 1)
            .setShouldReceiveVelocityUpdates(false)
            .build("weirdmob"));

    public static final RegistryObject<TutorialModDimension> DIMENSION = DIMENSIONS.register("dimension", TutorialModDimension::new);

}
