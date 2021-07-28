package com.mcjty.mytutorial.commands;

import com.mcjty.mytutorial.dimension.ModDimensions;
import com.mcjty.mytutorial.tools.TeleportationTools;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

public class CommandTpDim implements Command<CommandSourceStack> {

    private static final CommandTpDim CMD = new CommandTpDim();

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("dim")
                .requires(cs -> cs.hasPermission(0))
                .executes(CMD);
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        int x = player.blockPosition().getX();
        int z = player.blockPosition().getZ();
        if (player.getCommandSenderWorld().dimension().equals(ModDimensions.TUTDIM)) {
            ServerLevel world = player.getServer().getLevel(Level.OVERWORLD);
            TeleportationTools.teleport(player, world, new BlockPos(x, 200, z));
        } else {
            ServerLevel world = player.getServer().getLevel(ModDimensions.TUTDIM);
            TeleportationTools.teleport(player, world, new BlockPos(x, 200, z));
        }
        return 0;
    }

}