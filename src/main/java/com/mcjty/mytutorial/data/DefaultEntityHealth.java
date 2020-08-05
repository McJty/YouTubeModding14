package com.mcjty.mytutorial.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class DefaultEntityHealth implements IEntityHealth {

    private int health;

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getHealth() {
        return health;
    }

    public static class Storage implements Capability.IStorage<IEntityHealth> {

        @Nullable
        @Override
        public INBT writeNBT(Capability<IEntityHealth> capability, IEntityHealth instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            tag.putInt("health", instance.getHealth());
            return tag;
        }

        @Override
        public void readNBT(Capability<IEntityHealth> capability, IEntityHealth instance, Direction side, INBT nbt) {
            int health = ((CompoundNBT) nbt).getInt("health");
            instance.setHealth(health);
        }
    }
}
