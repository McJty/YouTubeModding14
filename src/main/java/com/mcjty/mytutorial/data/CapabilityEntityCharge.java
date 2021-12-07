package com.mcjty.mytutorial.data;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class CapabilityEntityCharge {

    public static Capability<IEntityCharge> ENTITY_CHARGE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
}
