package com.mcjty.mytutorial;

import com.mcjty.mytutorial.blocks.*;
import com.mcjty.mytutorial.entities.WeirdMobEntity;
import com.mcjty.mytutorial.items.FirstItem;
import com.mcjty.mytutorial.items.WeirdMobEggItem;
import com.mcjty.mytutorial.setup.ClientProxy;
import com.mcjty.mytutorial.setup.IProxy;
import com.mcjty.mytutorial.setup.ModSetup;
import com.mcjty.mytutorial.setup.ServerProxy;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("mytutorial")
public class MyTutorial {

    public static final String MODID = "mytutorial";

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public static ModSetup setup = new ModSetup();

    private static final Logger LOGGER = LogManager.getLogger();

    public MyTutorial() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("mytutorial-client.toml"));
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("mytutorial-common.toml"));
    }

    private void setup(final FMLCommonSetupEvent event) {
        setup.init();
        proxy.init();
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            event.getRegistry().register(new FirstBlock());
            event.getRegistry().register(new FancyBlock());
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            Item.Properties properties = new Item.Properties()
                    .group(setup.itemGroup);
            event.getRegistry().register(new BlockItem(ModBlocks.FIRSTBLOCK, properties).setRegistryName("firstblock"));
            event.getRegistry().register(new BlockItem(ModBlocks.FANCYBLOCK, properties).setRegistryName("fancyblock"));

            event.getRegistry().register(new FirstItem());
            event.getRegistry().register(new WeirdMobEggItem());
        }

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
            event.getRegistry().register(TileEntityType.Builder.create(FirstBlockTile::new, ModBlocks.FIRSTBLOCK).build(null).setRegistryName("firstblock"));
        }

        @SubscribeEvent
        public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new FirstBlockContainer(windowId, MyTutorial.proxy.getClientWorld(), pos, inv, MyTutorial.proxy.getClientPlayer());
            }).setRegistryName("firstblock"));
        }

        @SubscribeEvent
        public static void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().register(EntityType.Builder.create(WeirdMobEntity::new, EntityClassification.CREATURE)
                    .size(1, 1)
                    .setShouldReceiveVelocityUpdates(false)
                    .build("weirdmob").setRegistryName(MyTutorial.MODID, "weirdmob"));

        }
    }
}
