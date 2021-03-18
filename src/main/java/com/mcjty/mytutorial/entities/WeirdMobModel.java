package com.mcjty.mytutorial.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class WeirdMobModel extends EntityModel<WeirdMobEntity> {

    public ModelRenderer body;

    public WeirdMobModel() {
        body = new ModelRenderer(this, 0, 0);
        body.addBox(-3, 14, -3, 6, 6, 6);
    }

    @Override
    public void setupAnim(WeirdMobEntity weirdMobEntity, float v, float v1, float v2, float v3, float v4) {

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        body.render(matrixStack, iVertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
