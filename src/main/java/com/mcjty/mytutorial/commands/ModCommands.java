package com.mcjty.mytutorial.commands;

import com.mcjty.mytutorial.MyTutorial;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class ModCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> cmdTut = dispatcher.register(
                Commands.literal(MyTutorial.MODID)
                        .then(CommandTest.register(dispatcher))
                        .then(CommandTpDim.register(dispatcher))
                        .then(CommandSpawner.register(dispatcher))
        );

        dispatcher.register(Commands.literal("tut").redirect(cmdTut));
    }

}
