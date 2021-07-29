package com.mcjty.mytutorial.entities;

import com.mcjty.mytutorial.MyTutorial;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class WeirdMobModel extends EntityModel<WeirdMobEntity> {

    public static final String BODY = "body";

    public static ModelLayerLocation CUBE_LAYER = new ModelLayerLocation(new ResourceLocation(MyTutorial.MODID, "weirdmob"), BODY);

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild(BODY, CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-3, 14, -3, 6, 6, 6), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    private final ModelPart body;

    public WeirdMobModel(ModelPart body) {
        this.body = body;
    }

    @Override
    public void setupAnim(WeirdMobEntity weirdMobEntity, float v, float v1, float v2, float v3, float v4) {

    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer builder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        body.render(matrixStack, builder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
