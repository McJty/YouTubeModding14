package com.mcjty.mytutorial.commands;

import com.mcjty.mytutorial.dimension.ModDimensions;
import com.mcjty.mytutorial.tools.TeleportationTools;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CommandTpDim implements Command<CommandSource> {

    private static final CommandTpDim CMD = new CommandTpDim();

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("dim")
                .requires(cs -> cs.hasPermission(0))
                .executes(CMD);
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrException();
        int x = player.blockPosition().getX();
        int z = player.blockPosition().getZ();
        if (player.getCommandSenderWorld().dimension().equals(ModDimensions.TUTDIM)) {
            ServerWorld world = player.getServer().getLevel(World.OVERWORLD);
            TeleportationTools.teleport(player, world, new BlockPos(x, 200, z));
        } else {
            ServerWorld world = player.getServer().getLevel(ModDimensions.TUTDIM);
            TeleportationTools.teleport(player, world, new BlockPos(x, 200, z));
        }
        return 0;
    }

}