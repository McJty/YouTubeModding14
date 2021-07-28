package com.mcjty.mytutorial.data;

import com.mcjty.mytutorial.MyTutorial;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Explosion;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class ChargeEventHandler {

    public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PathfinderMob) {
            EntityChargeProvider provider = new EntityChargeProvider();
            event.addCapability(new ResourceLocation(MyTutorial.MODID, "health"), provider);
            event.addListener(provider::invalidate);
        }
    }

    public static void onDeathEvent(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        entity.getCapability(CapabilityEntityCharge.ENTITY_CHARGE_CAPABILITY).ifPresent(h -> {
            int charge = h.getCharge();
            if (charge > 0) {
                entity.getCommandSenderWorld().explode(entity, entity.getX(), entity.getY(), entity.getZ(), charge * .3f + 1.0f, Explosion.BlockInteraction.DESTROY);
            }
        });
    }

    public static void onAttackEvent(AttackEntityEvent event) {
        Entity attacker = event.getEntity();
        if (attacker instanceof Player) {
            Player player = (Player) attacker;
            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() == Items.GUNPOWDER) {
                Entity target = event.getTarget();
                target.getCapability(CapabilityEntityCharge.ENTITY_CHARGE_CAPABILITY).ifPresent(h -> {
                    int charge = h.getCharge() + 1;
                    h.setCharge(charge);
                    player.displayClientMessage(new TranslatableComponent("message.increase_charge", Integer.toString(charge)), true);
                    stack.shrink(1);
                    player.setItemInHand(InteractionHand.MAIN_HAND, stack);
                    event.setCanceled(true);
                    target.getCommandSenderWorld().addParticle(ParticleTypes.FIREWORK, target.getX(), target.getY()+1, target.getZ(), 0.0, 0.0, 0.0);
                });
            }
        }
    }


}
