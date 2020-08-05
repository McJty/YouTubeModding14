package com.mcjty.mytutorial.data;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityEntityHealth {

    @CapabilityInject(IEntityHealth.class)
    public static Capability<IEntityHealth> ENTITY_HEALTH_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IEntityHealth.class, new DefaultEntityHealth.Storage(), DefaultEntityHealth::new);
    }

}
