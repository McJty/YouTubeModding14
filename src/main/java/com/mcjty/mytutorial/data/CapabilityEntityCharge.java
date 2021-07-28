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

//    public static class Storage implements Capability.IStorage<IEntityCharge> {
//
//        @Nullable
//        @Override
//        public Tag writeNBT(Capability<IEntityCharge> capability, IEntityCharge instance, Direction side) {
//            CompoundTag tag = new CompoundTag();
//            tag.putInt("charge", instance.getCharge());
//            return tag;
//        }
//
//        @Override
//        public void readNBT(Capability<IEntityCharge> capability, IEntityCharge instance, Direction side, Tag nbt) {
//            int charge = ((CompoundTag) nbt).getInt("charge");
//            instance.setCharge(charge);
//        }
//    }
}
