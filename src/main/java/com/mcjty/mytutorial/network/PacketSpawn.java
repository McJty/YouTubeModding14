package com.mcjty.mytutorial.network;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;


public class PacketSpawn {

    private final String id;
    private final DimensionType type;
    private final BlockPos pos;

    public PacketSpawn(PacketBuffer buf) {
        id = buf.readString();
        type = DimensionType.getById(buf.readInt());
        pos = buf.readBlockPos();
    }

    public PacketSpawn(String id, DimensionType type, BlockPos pos) {
        this.id = id;
        this.type = type;
        this.pos = pos;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeString(id);
        buf.writeInt(type.getId());
        buf.writeBlockPos(pos);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerWorld spawnWorld = ctx.get().getSender().world.getServer().func_71218_a(type);
            EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(id));
            if (entityType == null) {
                throw new IllegalStateException("This cannot happen! Unknown id '" + id + "'!");
            }
            entityType.spawn(spawnWorld, null, null, pos, SpawnReason.SPAWN_EGG, true, true);
        });
        ctx.get().setPacketHandled(true);
    }

}
