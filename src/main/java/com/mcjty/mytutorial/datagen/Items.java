package com.mcjty.mytutorial.datagen;

import com.mcjty.mytutorial.MyTutorial;
import com.mcjty.mytutorial.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class Items extends ItemModelProvider {

    public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MyTutorial.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        singleTexture(Registration.MAGICBLOCK_ITEM.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                "layer0", new ResourceLocation(MyTutorial.MODID, "item/magicblock_item"));
        singleTexture(Registration.FIRSTITEM.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                "layer0", new ResourceLocation(MyTutorial.MODID, "item/firstitem"));
        withExistingParent(Registration.FIRSTBLOCK_ITEM.get().getRegistryName().getPath(), new ResourceLocation(MyTutorial.MODID, "block/firstblock"));
    }
}
