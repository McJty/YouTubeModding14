package com.mcjty.mytutorial.data;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityEntityCharge {

    @CapabilityInject(IEntityCharge.class)
    public static Capability<IEntityCharge> ENTITY_CHARGE_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IEntityCharge.class);
    }
}
