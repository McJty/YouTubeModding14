package com.mcjty.mytutorial.entities;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

public class WeirdMobModel extends EntityModel<WeirdMobEntity> {

    public RendererModel body;

    public WeirdMobModel() {
        body = new RendererModel(this, 0, 0);
        body.addBox(-3, -3, -3, 6, 6, 6);
    }

    @Override
    public void render(WeirdMobEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        body.render(scale);
    }

    @Override
    public void setRotationAngles(WeirdMobEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

    }
}
