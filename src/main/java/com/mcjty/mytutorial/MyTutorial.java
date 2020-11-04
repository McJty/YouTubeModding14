package com.mcjty.mytutorial;

import com.mcjty.mytutorial.setup.ClientSetup;
import com.mcjty.mytutorial.setup.Config;
import com.mcjty.mytutorial.setup.ModSetup;
import com.mcjty.mytutorial.setup.Registration;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MyTutorial.MODID)
public class MyTutorial {

    public static final String MODID = "mytutorial";

    public static final Logger LOGGER = LogManager.getLogger();

    public MyTutorial() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);

        Registration.init();

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModSetup::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
    }
}
