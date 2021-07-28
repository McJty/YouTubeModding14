package com.mcjty.mytutorial.setup;

import com.mcjty.mytutorial.MyTutorial;
import com.mcjty.mytutorial.commands.ModCommands;
import com.mcjty.mytutorial.data.CapabilityEntityCharge;
import com.mcjty.mytutorial.data.ChargeEventHandler;
import com.mcjty.mytutorial.dimension.TutorialBiomeProvider;
import com.mcjty.mytutorial.dimension.TutorialChunkGenerator;
import com.mcjty.mytutorial.entities.WeirdMobEntity;
import com.mcjty.mytutorial.network.Networking;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = MyTutorial.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {

    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab("mytutorial") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Registration.FIRSTBLOCK.get());
        }
    };

    public static void init(final FMLCommonSetupEvent event) {
        Networking.registerMessages();
        CapabilityEntityCharge.register();

        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, ChargeEventHandler::onAttachCapabilitiesEvent);
        MinecraftForge.EVENT_BUS.addListener(ChargeEventHandler::onAttackEvent);
        MinecraftForge.EVENT_BUS.addListener(ChargeEventHandler::onDeathEvent);

        event.enqueueWork(() -> {
            Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(MyTutorial.MODID, "chunkgen"),
                    TutorialChunkGenerator.CODEC);
            Registry.register(Registry.BIOME_SOURCE, new ResourceLocation(MyTutorial.MODID, "biomes"),
                    TutorialBiomeProvider.CODEC);
        });
    }

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(Registration.WEIRDMOB.get(), WeirdMobEntity.prepareAttributes().build());
    }

    @SubscribeEvent
    public static void serverLoad(RegisterCommandsEvent event) {
        ModCommands.register(event.getDispatcher());
    }
}
