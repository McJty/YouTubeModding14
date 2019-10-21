package com.mcjty.mytutorial.blocks;

import com.google.common.util.concurrent.FakeTimeLimiter;
import com.mcjty.mytutorial.MyTutorial;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FancyBakedModel implements IDynamicBakedModel {

    private final VertexFormat format;
    private final ItemCameraTransforms transforms = getAllTransforms();

    public FancyBakedModel(VertexFormat format) {
        this.format = format;
    }

    private TextureAtlasSprite getTexture() {
        String name = MyTutorial.MODID + ":block/fancyblock";
        return Minecraft.getInstance().getTextureMap().getAtlasSprite(name);
    }

    private void putVertex(UnpackedBakedQuad.Builder builder, Vec3d normal,
                           double x, double y, double z, float u, float v, TextureAtlasSprite sprite, float r, float g, float b) {
        for (int e = 0; e < format.getElementCount(); e++) {
            switch (format.getElement(e).getUsage()) {
                case POSITION:
                    builder.put(e, (float) x, (float) y, (float) z, 1.0f);
                    break;
                case COLOR:
                    builder.put(e, r, g, b, 1.0f);
                    break;
                case UV:
                    if (format.getElement(e).getIndex() == 0) {
                        u = sprite.getInterpolatedU(u);
                        v = sprite.getInterpolatedV(v);
                        builder.put(e, u, v, 0f, 1f);
                        break;
                    }
                case NORMAL:
                    builder.put(e, (float) normal.x, (float) normal.y, (float) normal.z, 0f);
                    break;
                default:
                    builder.put(e);
                    break;
            }
        }
    }

    private BakedQuad createQuad(Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, TextureAtlasSprite sprite) {
        Vec3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();

        UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
        builder.setTexture(sprite);
        putVertex(builder, normal, v1.x, v1.y, v1.z, 0, 0, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v2.x, v2.y, v2.z, 0, 16, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v3.x, v3.y, v3.z, 16, 16, sprite, 1.0f, 1.0f, 1.0f);
        putVertex(builder, normal, v4.x, v4.y, v4.z, 16, 0, sprite, 1.0f, 1.0f, 1.0f);
        return builder.build();
    }

    private static Vec3d v(double x, double y, double z) {
        return new Vec3d(x, y, z);
    }


    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {

        BlockState mimic = extraData.getData(FancyBlockTile.MIMIC);
        if (mimic != null) {
            ModelResourceLocation location = BlockModelShapes.getModelLocation(mimic);
            if (location != null) {
                IBakedModel model = Minecraft.getInstance().getModelManager().getModel(location);
                if (model != null) {
                    return model.getQuads(mimic, side, rand, extraData);
                }
            }
        }

        if (side != null) {
            return Collections.emptyList();
        }

        TextureAtlasSprite texture = getTexture();
        List<BakedQuad> quads = new ArrayList<>();
        double l = .2;
        double r = 1-.2;
        quads.add(createQuad(v(l, r, l), v(l, r, r), v(r, r, r), v(r, r, l), texture));
        quads.add(createQuad(v(l, l, l), v(r, l, l), v(r, l, r), v(l, l, r), texture));
        quads.add(createQuad(v(r, r, r), v(r, l, r), v(r, l, l), v(r, r, l), texture));
        quads.add(createQuad(v(l, r, l), v(l, l, l), v(l, l, r), v(l, r, r), texture));
        quads.add(createQuad(v(r, r, l), v(r, l, l), v(l, l, l), v(l, r, l), texture));
        quads.add(createQuad(v(l, r, r), v(l, l, r), v(r, l, r), v(r, r, r), texture));

        return quads;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return getTexture();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.EMPTY;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return transforms;
    }

    public ItemCameraTransforms getAllTransforms() {
        ItemTransformVec3f tpLeft = this.getTransform(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND);
        ItemTransformVec3f tpRight = this.getTransform(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
        ItemTransformVec3f fpLeft = this.getTransform(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND);
        ItemTransformVec3f fpRight = this.getTransform(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND);
        ItemTransformVec3f head = this.getTransform(ItemCameraTransforms.TransformType.HEAD);
        ItemTransformVec3f gui = this.getTransform(ItemCameraTransforms.TransformType.GUI);
        ItemTransformVec3f ground = this.getTransform(ItemCameraTransforms.TransformType.GROUND);
        ItemTransformVec3f fixed = this.getTransform(ItemCameraTransforms.TransformType.FIXED);
        return new ItemCameraTransforms(tpLeft, tpRight, fpLeft, fpRight, head, gui, ground, fixed);
    }

    private ItemTransformVec3f getTransform(ItemCameraTransforms.TransformType type) {
        if (type.equals(ItemCameraTransforms.TransformType.GUI)) {
            return new ItemTransformVec3f(new Vector3f(200, 50, 100), new Vector3f(), new Vector3f(1.0F, 1.0F, 1.0F));
        }
        return ItemTransformVec3f.DEFAULT;
    }

}
