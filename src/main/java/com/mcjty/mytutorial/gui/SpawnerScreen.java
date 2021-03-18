package com.mcjty.mytutorial.gui;

import com.mcjty.mytutorial.MyTutorial;
import com.mcjty.mytutorial.network.Networking;
import com.mcjty.mytutorial.network.PacketSpawn;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class SpawnerScreen extends Screen {

    private static final int WIDTH = 179;
    private static final int HEIGHT = 151;

    private ResourceLocation GUI = new ResourceLocation(MyTutorial.MODID, "textures/gui/spawner_gui.png");


    public SpawnerScreen() {
        super(new TranslationTextComponent("screen.mytutorial.spawn"));
    }

    @Override
    protected void init() {
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;

        addButton(new Button(relX + 10, relY + 10, 160, 20, new StringTextComponent("Skeleton"), button -> spawn("minecraft:skeleton")));
        addButton(new Button(relX + 10, relY + 37, 160, 20, new StringTextComponent("Zombie"), button -> spawn("minecraft:zombie")));
        addButton(new Button(relX + 10, relY + 64, 160, 20, new StringTextComponent("Cow"), button -> spawn("minecraft:cow")));
        addButton(new Button(relX + 10, relY + 91, 160, 20, new StringTextComponent("Sheep"), button -> spawn("minecraft:sheep")));
        addButton(new Button(relX + 10, relY + 118, 160, 20, new StringTextComponent("Chicken"), button -> spawn("minecraft:chicken")));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void spawn(String id) {
        Networking.sendToServer(new PacketSpawn(new ResourceLocation(id), minecraft.player.getCommandSenderWorld().dimension(), minecraft.player.blockPosition()));
        minecraft.setScreen(null);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI);
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, WIDTH, HEIGHT);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }


    public static void open() {
        Minecraft.getInstance().setScreen(new SpawnerScreen());
    }
}
