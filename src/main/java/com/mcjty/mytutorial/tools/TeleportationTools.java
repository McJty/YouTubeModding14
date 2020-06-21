package com.mcjty.mytutorial.tools;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldInfo;

public class TeleportationTools {

    public static ServerPlayerEntity teleport(ServerPlayerEntity entity, DimensionType destination, BlockPos pos) {
        if (entity.dimension.equals(destination)) {
            // @todo check
            entity.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
            return entity;
        }

        if (!net.minecraftforge.common.ForgeHooks.onTravelToDimension(entity, destination)) {
            return null;
        }
        DimensionType source = entity.dimension;
        ServerWorld originalWorld = entity.server.getWorld(source);
        entity.dimension = destination;
        ServerWorld destinationWorld = entity.server.getWorld(destination);
        WorldInfo worldinfo = entity.world.getWorldInfo();
        net.minecraftforge.fml.network.NetworkHooks.sendDimensionDataPacket(entity.connection.netManager, entity);
        entity.connection.sendPacket(new SRespawnPacket(destination, WorldInfo.byHashing(worldinfo.getSeed()), worldinfo.getGenerator(), entity.interactionManager.getGameType()));
        entity.connection.sendPacket(new SServerDifficultyPacket(worldinfo.getDifficulty(), worldinfo.isDifficultyLocked()));
        PlayerList playerlist = entity.server.getPlayerList();
        playerlist.updatePermissionLevel(entity);
        originalWorld.removeEntity(entity, true); //Forge: the player entity is moved to the new world, NOT cloned. So keep the data alive with no matching invalidate call.
        entity.revive();
        Vec3d vec = entity.getPositionVec();
        double d0 = vec.x;
        double d1 = vec.y;
        double d2 = vec.z;
        float f = entity.rotationPitch;
        float f1 = entity.rotationYaw;
        double d3 = 8.0D;
        originalWorld.getProfiler().startSection("moving");
        double moveFactor = originalWorld.getDimension().getMovementFactor() / destinationWorld.getDimension().getMovementFactor();
        d0 *= moveFactor;
        d2 *= moveFactor;

        entity.setLocationAndAngles(d0, d1, d2, f1, f);
        originalWorld.getProfiler().endSection();
        originalWorld.getProfiler().startSection("placing");
        double d7 = Math.min(-2.9999872E7D, destinationWorld.getWorldBorder().minX() + 16.0D);
        double d4 = Math.min(-2.9999872E7D, destinationWorld.getWorldBorder().minZ() + 16.0D);
        double d5 = Math.min(2.9999872E7D, destinationWorld.getWorldBorder().maxX() - 16.0D);
        double d6 = Math.min(2.9999872E7D, destinationWorld.getWorldBorder().maxZ() - 16.0D);
        d0 = MathHelper.clamp(d0, d7, d5);
        d2 = MathHelper.clamp(d2, d4, d6);
        entity.setLocationAndAngles(d0, d1, d2, f1, f);
//        if (!destinationWorld.getDefaultTeleporter().placeInPortal(entity, f1)) {
//            destinationWorld.getDefaultTeleporter().makePortal(entity);
//            destinationWorld.getDefaultTeleporter().placeInPortal(entity, f1);
//        }

        originalWorld.getProfiler().endSection();
        entity.setWorld(destinationWorld);
        destinationWorld.addDuringPortalTeleport(entity);
        vec = entity.getPositionVec();
        entity.connection.setPlayerLocation(vec.x, vec.y, vec.z, f1, f);
        entity.interactionManager.world = destinationWorld;
        entity.connection.sendPacket(new SPlayerAbilitiesPacket(entity.abilities));
        playerlist.sendWorldInfo(entity, destinationWorld);
        playerlist.sendInventory(entity);

        for (EffectInstance effectinstance : entity.getActivePotionEffects()) {
            entity.connection.sendPacket(new SPlayEntityEffectPacket(entity.getEntityId(), effectinstance));
        }

        entity.connection.sendPacket(new SPlaySoundEventPacket(1032, BlockPos.ZERO, 0, false));
        entity.setExperienceLevel(entity.experienceLevel);
        entity.setHealth(entity.getHealth());
//        entity.lastHealth = -1.0F;
//        entity.lastFoodLevel = -1;
        net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerChangedDimensionEvent(entity, source, destination);
        return entity;
    }
}
