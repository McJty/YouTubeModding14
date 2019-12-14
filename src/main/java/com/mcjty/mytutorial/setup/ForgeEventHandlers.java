package com.mcjty.mytutorial.setup;

import com.mcjty.mytutorial.commands.ModCommands;
import com.mcjty.mytutorial.dimension.ModDimensions;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public class ForgeEventHandlers {

    @SubscribeEvent
    public void serverLoad(FMLServerStartingEvent event) {
        ModCommands.register(event.getCommandDispatcher());
    }

    @SubscribeEvent
    public void onDimensionRegistry(RegisterDimensionsEvent event) {
        ModDimensions.DIMENSION_TYPE = DimensionManager.registerOrGetDimension(ModDimensions.DIMENSION_ID, ModDimensions.DIMENSION, null, true);
    }

}
