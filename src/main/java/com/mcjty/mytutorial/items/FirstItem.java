package com.mcjty.mytutorial.items;

import com.mcjty.mytutorial.setup.ModSetup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class FirstItem extends Item {

    public FirstItem() {
        super(new Item.Properties()
                .stacksTo(1)
                .tab(ModSetup.ITEM_GROUP));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag flags) {
        list.add(new TranslatableComponent("message.firstitem"));
    }

}
