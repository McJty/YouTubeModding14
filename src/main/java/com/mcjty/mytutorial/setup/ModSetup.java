package com.mcjty.mytutorial.setup;

import com.mcjty.mytutorial.MyTutorial;
import com.mcjty.mytutorial.commands.ModCommands;
import com.mcjty.mytutorial.data.CapabilityEntityHealth;
import com.mcjty.mytutorial.data.DefaultEntityHealth;
import com.mcjty.mytutorial.data.IEntityHealth;
import com.mcjty.mytutorial.dimension.ModDimensions;
import com.mcjty.mytutorial.network.Networking;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = MyTutorial.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {

    public static final ItemGroup ITEM_GROUP = new ItemGroup("mytutorial") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Registration.FIRSTBLOCK.get());
        }
    };

    public static void init(final FMLCommonSetupEvent event) {
        Networking.registerMessages();
        CapabilityEntityHealth.register();
    }

    @SubscribeEvent
    public static void serverLoad(FMLServerStartingEvent event) {
        ModCommands.register(event.getCommandDispatcher());
    }

    @SubscribeEvent
    public static void onDimensionRegistry(RegisterDimensionsEvent event) {
        ModDimensions.DIMENSION_TYPE = DimensionManager.registerOrGetDimension(ModDimensions.DIMENSION_ID, Registration.DIMENSION.get(), null, true);
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof CreatureEntity) {
            event.addCapability(new ResourceLocation(MyTutorial.MODID, "health"), new ICapabilityProvider() {
                private final LazyOptional<IEntityHealth> health = LazyOptional.of(DefaultEntityHealth::new);

                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    return health.cast();
                }
            });
        }
    }

    @SubscribeEvent
    public static void onAttackEvent(AttackEntityEvent event) {
        Entity attacker = event.getEntity();
        if (attacker instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) attacker;
            ItemStack stack = player.getHeldItemMainhand();
            if (stack.getItem() == Items.REDSTONE) {
                Entity target = event.getTarget();
                target.getCapability(CapabilityEntityHealth.ENTITY_HEALTH_CAPABILITY).ifPresent(h -> {
                    int health = h.getHealth() + 1;
                    h.setHealth(health);
                    player.sendStatusMessage(new TranslationTextComponent("message.increase_health", Integer.toString(health)), true);
                    stack.shrink(1);
                    player.setHeldItem(Hand.MAIN_HAND, stack);
                });
            }
        }
    }
}
