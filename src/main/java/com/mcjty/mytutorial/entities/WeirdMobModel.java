package com.mcjty.mytutorial.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;

public class WeirdMobModel extends EntityModel<WeirdMobEntity> {

    public ModelPart body;

    public WeirdMobModel() {
//        List<ModelPart.Cube> cubes = Collections.singletonList(new ModelPart.Cube(-3, 14, -3, 6, 6, 6))
//        body = new ModelPart(this, 0, 0);
//        body.addBox(-3, 14, -3, 6, 6, 6);
    }

    @Override
    public void setupAnim(WeirdMobEntity weirdMobEntity, float v, float v1, float v2, float v3, float v4) {

    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer iVertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        body.render(matrixStack, iVertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
