package com.mcjty.mytutorial.setup;

import com.mcjty.mytutorial.MyTutorial;
import com.mcjty.mytutorial.blocks.FancyBakedModel;
import com.mcjty.mytutorial.blocks.ModBlocks;
import com.mcjty.mytutorial.items.ModItems;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MyTutorial.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistration {

    @SubscribeEvent
    public static void onItemColor(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, i) -> 0xff0000, ModItems.WEIRDMOB_EGG);
    }

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (!event.getMap().getBasePath().equals("textures")) {
            return;
        }
        event.addSprite(new ResourceLocation(MyTutorial.MODID, "block/fancyblock"));
    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        event.getModelRegistry().put(new ModelResourceLocation(ModBlocks.FANCYBLOCK.getRegistryName(), ""),
                new FancyBakedModel(DefaultVertexFormats.BLOCK));
        event.getModelRegistry().put(new ModelResourceLocation(ModBlocks.FANCYBLOCK.getRegistryName(), "inventory"),
                new FancyBakedModel(DefaultVertexFormats.ITEM));
    }
}
