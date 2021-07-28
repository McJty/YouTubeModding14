package com.mcjty.mytutorial.tools;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class TeleportationTools {

    public static void teleport(ServerPlayer entity, ServerLevel destination, BlockPos pos) {
        entity.changeDimension(destination, new ITeleporter() {
            @Override
            public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                entity = repositionEntity.apply(false);
                entity.teleportTo(pos.getX(), pos.getY(), pos.getZ());
                return entity;
            }
        });
    }
}
