package com.mcjty.mytutorial.blocks;

import com.mcjty.mytutorial.MyTutorial;
import com.mcjty.mytutorial.setup.Config;
import com.mcjty.mytutorial.setup.Registration;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.Random;

public class MagicRenderer extends TileEntityRenderer<MagicTile> {

    public static final ResourceLocation MAGICBLOCK_TEXTURE = new ResourceLocation(MyTutorial.MODID, "block/magicblock");

    public MagicRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v) {
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
    public void render(MagicTile tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(MAGICBLOCK_TEXTURE);
        IVertexBuilder builder = buffer.getBuffer(RenderType.translucent());

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
        IBakedModel ibakedmodel = itemRenderer.getModel(stack, tileEntity.getLevel(), null);
        itemRenderer.render(stack, ItemCameraTransforms.TransformType.FIXED, true, matrixStack, buffer, combinedLight, combinedOverlay, ibakedmodel);

        matrixStack.translate(-.5, 1, -.5);
        BlockRendererDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
        BlockState state = Blocks.ENDER_CHEST.defaultBlockState();
        blockRenderer.renderBlock(state, matrixStack, buffer, combinedLight, combinedOverlay, EmptyModelData.INSTANCE);

        matrixStack.popPose();
    }

    public static void register() {
        ClientRegistry.bindTileEntityRenderer(Registration.MAGICBLOCK_TILE.get(), MagicRenderer::new);
    }

}
