package com.mcjty.mytutorial.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class WeirdMobModel extends EntityModel<WeirdMobEntity> {

    public ModelRenderer body;

    public WeirdMobModel() {
        body = new ModelRenderer(this, 0, 0);
        body.func_228300_a_(-3, -3, -3, 6, 6, 6);
    }

    @Override
    public void func_225597_a_(WeirdMobEntity weirdMobEntity, float v, float v1, float v2, float v3, float v4) {

    }

    @Override
    public void func_225598_a_(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int i, int i1, float v, float v1, float v2, float v3) {
        body.func_228309_a_(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
    }

//    @Override
//    public void render(WeirdMobEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
//        setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
//        body.render(scale);
//    }
}
