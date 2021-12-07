package com.mcjty.mytutorial.setup;

import com.mcjty.mytutorial.MyTutorial;
import com.mcjty.mytutorial.blocks.FancyBlockColor;
import com.mcjty.mytutorial.blocks.FancyModelLoader;
import com.mcjty.mytutorial.blocks.FirstBlockScreen;
import com.mcjty.mytutorial.blocks.MagicRenderer;
import com.mcjty.mytutorial.client.AfterLivingRenderer;
import com.mcjty.mytutorial.client.InWorldRenderer;
import com.mcjty.mytutorial.entities.WeirdMobModel;
import com.mcjty.mytutorial.entities.WeirdMobRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.mcjty.mytutorial.blocks.MagicRenderer.MAGICBLOCK_TEXTURE;

@Mod.EventBusSubscriber(modid = MyTutorial.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void init(final FMLClientSetupEvent event) {
        MenuScreens.register(Registration.FIRSTBLOCK_CONTAINER.get(), FirstBlockScreen::new);
        MinecraftForge.EVENT_BUS.addListener(InWorldRenderer::render);
        MinecraftForge.EVENT_BUS.addListener(AfterLivingRenderer::render);

        event.enqueueWork(() -> {
            MagicRenderer.register();
            ItemBlockRenderTypes.setRenderLayer(Registration.COMPLEX_MULTIPART.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Registration.FANCYBLOCK.get(), (RenderType) -> true);
            Minecraft.getInstance().getBlockColors().register(new FancyBlockColor(), Registration.FANCYBLOCK.get());
        });
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WeirdMobModel.CUBE_LAYER, WeirdMobModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Registration.WEIRDMOB.get(), WeirdMobRenderer::new);
    }

    @SubscribeEvent
    public static void onItemColor(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, i) -> 0xff0000, Registration.WEIRDMOB_EGG.get());
    }

    @SubscribeEvent
    public static void onModelRegistryEvent(ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(new ResourceLocation(MyTutorial.MODID, "fancyloader"), new FancyModelLoader());
    }

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (!event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
            return;
        }

        event.addSprite(MAGICBLOCK_TEXTURE);
    }

    @SubscribeEvent
    public void onTooltipPre(RenderTooltipEvent.GatherComponents event) {
        Item item = event.getItemStack().getItem();
        if (item.getRegistryName().getNamespace().equals(MyTutorial.MODID)) {
            event.setMaxWidth(200);
        }
    }
}
