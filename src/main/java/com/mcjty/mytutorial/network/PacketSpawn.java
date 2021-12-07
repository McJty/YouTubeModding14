package com.mcjty.mytutorial.network;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;


public class PacketSpawn {

    private final ResourceLocation id;
    private final ResourceKey<Level> type;
    private final BlockPos pos;

    public PacketSpawn(FriendlyByteBuf buf) {
        id = buf.readResourceLocation();
        type = ResourceKey.create(Registry.DIMENSION_REGISTRY, buf.readResourceLocation());
        pos = buf.readBlockPos();
    }

    public PacketSpawn(ResourceLocation id, ResourceKey<Level> type, BlockPos pos) {
        this.id = id;
        this.type = type;
        this.pos = pos;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceLocation(id);
        buf.writeResourceLocation(type.location());
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerLevel spawnWorld = ctx.get().getSender().level.getServer().getLevel(type);
            EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(id);
            if (entityType == null) {
                throw new IllegalStateException("This cannot happen! Unknown id '" + id.toString() + "'!");
            }
            entityType.spawn(spawnWorld, null, null, pos, MobSpawnType.SPAWN_EGG, true, true);
        });
        return true;
    }

}
