package com.mcjty.mytutorial.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityEntityCharge {

    @CapabilityInject(IEntityCharge.class)
    public static Capability<IEntityCharge> ENTITY_CHARGE_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IEntityCharge.class, new Storage(), DefaultEntityCharge::new);
    }

    public static class Storage implements Capability.IStorage<IEntityCharge> {

        @Nullable
        @Override
        public INBT writeNBT(Capability<IEntityCharge> capability, IEntityCharge instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            tag.putInt("charge", instance.getCharge());
            return tag;
        }

        @Override
        public void readNBT(Capability<IEntityCharge> capability, IEntityCharge instance, Direction side, INBT nbt) {
            int charge = ((CompoundNBT) nbt).getInt("charge");
            instance.setCharge(charge);
        }
    }
}
