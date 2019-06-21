package com.mcjty.mytutorial.setup;

import net.minecraft.world.World;

public interface IProxy {

    void init();

    World getClientWorld();
}
