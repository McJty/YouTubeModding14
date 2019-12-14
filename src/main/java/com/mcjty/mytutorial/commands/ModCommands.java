package com.mcjty.mytutorial.commands;

import com.mcjty.mytutorial.MyTutorial;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class ModCommands {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralCommandNode<CommandSource> cmdTut = dispatcher.register(
                Commands.literal(MyTutorial.MODID)
                        .then(CommandTest.register(dispatcher))
                        .then(CommandTpDim.register(dispatcher))
                        .then(CommandSpawner.register(dispatcher))
        );

        dispatcher.register(Commands.literal("tut").redirect(cmdTut));
    }

}
