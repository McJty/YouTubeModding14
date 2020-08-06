package com.mcjty.mytutorial.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityChargeProvider implements ICapabilitySerializable<CompoundNBT> {

    private final DefaultEntityCharge charge = new DefaultEntityCharge();
    private final LazyOptional<IEntityCharge> chargeOptional = LazyOptional.of(() -> charge);

    public void invalidate() {
        chargeOptional.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return chargeOptional.cast();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (CapabilityEntityCharge.ENTITY_CHARGE_CAPABILITY == null) {
            return new CompoundNBT();
        } else {
            return (CompoundNBT) CapabilityEntityCharge.ENTITY_CHARGE_CAPABILITY.writeNBT(charge, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (CapabilityEntityCharge.ENTITY_CHARGE_CAPABILITY != null) {
            CapabilityEntityCharge.ENTITY_CHARGE_CAPABILITY.readNBT(charge, null, nbt);
        }
    }
}
