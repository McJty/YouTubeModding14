package com.mcjty.mytutorial.setup;

import com.mcjty.mytutorial.blocks.*;
import com.mcjty.mytutorial.entities.WeirdMobEntity;
import com.mcjty.mytutorial.items.FirstItem;
import com.mcjty.mytutorial.items.WeirdMobEggItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.mcjty.mytutorial.MyTutorial.MODID;

public class Registration {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<FirstBlock> FIRSTBLOCK = BLOCKS.register("firstblock", FirstBlock::new);
    public static final RegistryObject<Item> FIRSTBLOCK_ITEM = ITEMS.register("firstblock", () -> new BlockItem(FIRSTBLOCK.get(), new Item.Properties().tab(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<FirstBlockTile>> FIRSTBLOCK_TILE = TILES.register("firstblock", () -> BlockEntityType.Builder.of(FirstBlockTile::new, FIRSTBLOCK.get()).build(null));

    public static final RegistryObject<MenuType<FirstBlockContainer>> FIRSTBLOCK_CONTAINER = CONTAINERS.register("firstblock", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new FirstBlockContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<FancyBlock> FANCYBLOCK = BLOCKS.register("fancyblock", FancyBlock::new);
    public static final RegistryObject<Item> FANCYBLOCK_ITEM = ITEMS.register("fancyblock", () -> new BlockItem(FANCYBLOCK.get(), new Item.Properties().tab(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<FancyBlockTile>> FANCYBLOCK_TILE = TILES.register("fancyblock", () -> BlockEntityType.Builder.of(FancyBlockTile::new, FANCYBLOCK.get()).build(null));

    public static final RegistryObject<MagicBlock> MAGICBLOCK = BLOCKS.register("magicblock", MagicBlock::new);
    public static final RegistryObject<Item> MAGICBLOCK_ITEM = ITEMS.register("magicblock", () -> new BlockItem(MAGICBLOCK.get(), new Item.Properties().tab(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<MagicTile>> MAGICBLOCK_TILE = TILES.register("magicblock", () -> BlockEntityType.Builder.of(MagicTile::new, MAGICBLOCK.get()).build(null));

    public static final RegistryObject<ComplexMultipartBlock> COMPLEX_MULTIPART = BLOCKS.register("complex_multipart", ComplexMultipartBlock::new);
    public static final RegistryObject<Item> COMPLEX_MULTIPART_ITEM = ITEMS.register("complex_multipart", () -> new BlockItem(COMPLEX_MULTIPART.get(), new Item.Properties().tab(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<BlockEntityType<ComplexMultipartTile>> COMPLEX_MULTIPART_TILE = TILES.register("complex_multipart", () -> BlockEntityType.Builder.of(ComplexMultipartTile::new, COMPLEX_MULTIPART.get()).build(null));

    public static final RegistryObject<FirstItem> FIRSTITEM = ITEMS.register("firstitem", FirstItem::new);
    public static final RegistryObject<WeirdMobEggItem> WEIRDMOB_EGG = ITEMS.register("weirdmob_egg", WeirdMobEggItem::new);

    public static final RegistryObject<EntityType<WeirdMobEntity>> WEIRDMOB = ENTITIES.register("weirdmob", () -> EntityType.Builder.of(WeirdMobEntity::new, MobCategory.CREATURE)
            .sized(.5f, .5f)
            .setShouldReceiveVelocityUpdates(false)
            .build("weirdmob"));

}
