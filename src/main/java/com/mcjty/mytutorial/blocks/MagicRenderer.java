package com.mcjty.mytutorial.blocks;

import com.mcjty.mytutorial.MyTutorial;
import com.mcjty.mytutorial.setup.Config;
import com.mcjty.mytutorial.setup.Registration;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

import java.util.Random;

public class MagicRenderer implements BlockEntityRenderer<MagicTile> {

    public static final ResourceLocation MAGICBLOCK_TEXTURE = new ResourceLocation(MyTutorial.MODID, "block/magicblock");

    public MagicRenderer(BlockEntityRendererProvider.Context context) {
    }

    private void add(VertexConsumer renderer, PoseStack stack, float x, float y, float z, float u, float v) {
        renderer.vertex(stack.last().pose(), x, y, z)
                .color(1.0f, 1.0f, 1.0f, 1.0f)
                .uv(u, v)
                .uv2(0, 240)
                .normal(1, 0, 0)
                .endVertex();
    }

    private static float diffFunction(long time, long delta, float scale) {
        long dt = time % (delta * 2);
        if (dt > delta) {
            dt = 2*delta - dt;
        }
        return dt * scale;
    }

    @Override
    public void render(MagicTile tileEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(MAGICBLOCK_TEXTURE);
        VertexConsumer builder = buffer.getBuffer(RenderType.translucent());

        Random rnd = new Random(tileEntity.getBlockPos().getX() * 337L + tileEntity.getBlockPos().getY() * 37L + tileEntity.getBlockPos().getZ() * 13L);

        long time = System.currentTimeMillis();
        float dx1 = diffFunction(time, 900 + rnd.nextInt(800), 0.00001f + rnd.nextFloat() * 0.0001f);
        float dx2 = diffFunction(time, 900 + rnd.nextInt(800), 0.00001f + rnd.nextFloat() * 0.0001f);
        float dx3 = diffFunction(time, 900 + rnd.nextInt(800), 0.00001f + rnd.nextFloat() * 0.0001f);
        float dx4 = diffFunction(time, 900 + rnd.nextInt(800), 0.00001f + rnd.nextFloat() * 0.0001f);
        float dy1 = diffFunction(time, 900 + rnd.nextInt(800), 0.00001f + rnd.nextFloat() * 0.0001f);
        float dy2 = diffFunction(time, 900 + rnd.nextInt(800), 0.00001f + rnd.nextFloat() * 0.0001f);
        float dy3 = diffFunction(time, 900 + rnd.nextInt(800), 0.00001f + rnd.nextFloat() * 0.0001f);
        float dy4 = diffFunction(time, 900 + rnd.nextInt(800), 0.00001f + rnd.nextFloat() * 0.0001f);

        double speed = Config.ROTATION_SPEED.get();
        float angle = (time / (int)speed) % 360;
        Quaternion rotation = Vector3f.YP.rotationDegrees(angle);
        float scale = 1.0f + diffFunction(time,900 + rnd.nextInt(800), 0.0001f + rnd.nextFloat() * 0.001f);

        matrixStack.pushPose();
        matrixStack.translate(.5, .5, .5);
        matrixStack.mulPose(rotation);
        matrixStack.scale(scale, scale, scale);
        matrixStack.translate(-.5, -.5, -.5);

        add(builder, matrixStack, 0 + dx1, 0 + dy1, .5f, sprite.getU0(), sprite.getV0());
        add(builder, matrixStack, 1 - dx2, 0 + dy2, .5f, sprite.getU1(), sprite.getV0());
        add(builder, matrixStack, 1 - dx3, 1 - dy3, .5f, sprite.getU1(), sprite.getV1());
        add(builder, matrixStack, 0 + dx4, 1 - dy4, .5f, sprite.getU0(), sprite.getV1());

        add(builder, matrixStack, 0 + dx4, 1 - dy4, .5f, sprite.getU0(), sprite.getV1());
        add(builder, matrixStack, 1 - dx3, 1 - dy3, .5f, sprite.getU1(), sprite.getV1());
        add(builder, matrixStack, 1 - dx2, 0 + dy2, .5f, sprite.getU1(), sprite.getV0());
        add(builder, matrixStack, 0 + dx1, 0 + dy1, .5f, sprite.getU0(), sprite.getV0());

        matrixStack.popPose();

        matrixStack.pushPose();

        matrixStack.translate(0.5, 1.5, 0.5);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = new ItemStack(Items.DIAMOND);
        BakedModel ibakedmodel = itemRenderer.getModel(stack, tileEntity.getLevel(), null, 0);  // @todo check the integer
        itemRenderer.render(stack, ItemTransforms.TransformType.FIXED, true, matrixStack, buffer, combinedLight, combinedOverlay, ibakedmodel);

        matrixStack.translate(-.5, 1, -.5);
        BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
        BlockState state = Blocks.ENDER_CHEST.defaultBlockState();
        blockRenderer.renderSingleBlock(state, matrixStack, buffer, combinedLight, combinedOverlay, EmptyModelData.INSTANCE);

        matrixStack.popPose();
    }

    public static void register() {
        BlockEntityRenderers.register(Registration.MAGICBLOCK_TILE.get(), MagicRenderer::new);
    }

}
