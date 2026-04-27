package com.modforge.commandrules;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CommandRulesMod implements ModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("command-rules-moggiwb8");

    public static volatile boolean BLOCK_DROP_ENABLED = true;
    public static volatile int BLOCK_DROP_MULTIPLIER = 1;
    public static volatile boolean MOB_DROP_ENABLED = true;
    public static volatile int MOB_DROP_MULTIPLIER = 1;

    @Override
    public void onInitialize() {
        try {
            try {
                CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, env) ->
                        dispatcher.register(literal("multiplier")
                                .then(literal("on").executes(ctx -> {
                                    BLOCK_DROP_ENABLED = true;
                                    ctx.getSource().sendFeedback(
                                            () -> net.minecraft.text.Text.literal("Block drop multiplier ON (x" + BLOCK_DROP_MULTIPLIER + ")"),
                                            true
                                    );
                                    return 1;
                                }))
                                .then(literal("off").executes(ctx -> {
                                    BLOCK_DROP_ENABLED = false;
                                    ctx.getSource().sendFeedback(
                                            () -> net.minecraft.text.Text.literal("Block drop multiplier OFF"),
                                            true
                                    );
                                    return 1;
                                }))
                                .then(argument("value", IntegerArgumentType.integer(1, 1000)).executes(ctx -> {
                                    final int v = IntegerArgumentType.getInteger(ctx, "value");
                                    BLOCK_DROP_MULTIPLIER = v;
                                    BLOCK_DROP_ENABLED = true;
                                    ctx.getSource().sendFeedback(
                                            () -> net.minecraft.text.Text.literal("Block drop multiplier set to x" + BLOCK_DROP_MULTIPLIER),
                                            true
                                    );
                                    return 1;
                                }))
                        )
                );
            } catch (Throwable t) {
                LOGGER.error("failed to register /multiplier", t);
            }

            try {
                CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, env) ->
                        dispatcher.register(literal("mobmulti")
                                .then(literal("on").executes(ctx -> {
                                    MOB_DROP_ENABLED = true;
                                    ctx.getSource().sendFeedback(
                                            () -> net.minecraft.text.Text.literal("Mob drop multiplier ON (x" + MOB_DROP_MULTIPLIER + ")"),
                                            true
                                    );
                                    return 1;
                                }))
                                .then(literal("off").executes(ctx -> {
                                    MOB_DROP_ENABLED = false;
                                    ctx.getSource().sendFeedback(
                                            () -> net.minecraft.text.Text.literal("Mob drop multiplier OFF"),
                                            true
                                    );
                                    return 1;
                                }))
                                .then(argument("value", IntegerArgumentType.integer(1, 1000)).executes(ctx -> {
                                    final int v = IntegerArgumentType.getInteger(ctx, "value");
                                    MOB_DROP_MULTIPLIER = v;
                                    MOB_DROP_ENABLED = true;
                                    ctx.getSource().sendFeedback(
                                            () -> net.minecraft.text.Text.literal("Mob drop multiplier set to x" + MOB_DROP_MULTIPLIER),
                                            true
                                    );
                                    return 1;
                                }))
                        )
                );
            } catch (Throwable t) {
                LOGGER.error("failed to register /mobmulti", t);
            }
        } catch (Throwable t) {
            LOGGER.error("init failed", t);
        }
    }
}
