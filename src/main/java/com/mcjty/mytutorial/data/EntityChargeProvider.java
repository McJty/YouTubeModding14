package com.mcjty.mytutorial.data;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityChargeProvider implements ICapabilitySerializable<CompoundTag> {

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
    public CompoundTag serializeNBT() {
        if (CapabilityEntityCharge.ENTITY_CHARGE_CAPABILITY == null) {
            return new CompoundTag();
        } else {
            CompoundTag tag = new CompoundTag();
            tag.putInt("charge", charge.getCharge());
            return tag;
        }
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (CapabilityEntityCharge.ENTITY_CHARGE_CAPABILITY != null) {
            charge.setCharge(nbt.getInt("charge"));
        }
    }
}
