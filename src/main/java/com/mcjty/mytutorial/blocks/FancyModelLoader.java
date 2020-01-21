package com.mcjty.mytutorial.blocks;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.client.model.IModelLoader;

public class FancyModelLoader implements IModelLoader<FancyModelGeometry> {

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    @Override
    public FancyModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
        return new FancyModelGeometry();
    }
}
