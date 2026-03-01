package com.tearhpi.immortal.command;

// ModCommands.java

import com.tearhpi.immortal.Immortal;
import com.tearhpi.immortal.attribute.ModAttributes;
import com.tearhpi.immortal.entity.capability.*;
import com.tearhpi.immortal.networking.*;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import static com.tearhpi.immortal.task.ModMainTask.EndOfMainTask;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModCommands {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent e) {
        CommandDispatcher<CommandSourceStack> d = e.getDispatcher();

        // addSP
        // addSP <player>
        d.register(Commands.literal("addSP")
                .requires(src -> src.hasPermission(2))
                .executes(ctx -> addOneSP(ctx.getSource().getPlayerOrException()))
                .then(Commands.argument("target", EntityArgument.player())
                        .executes(ctx -> addOneSP(EntityArgument.getPlayer(ctx, "target")))
                )
        );
        d.register(Commands.literal("addCoin")
                .requires(src -> src.hasPermission(2))
                .executes(ctx -> addCoin(ctx.getSource().getPlayerOrException()))
                .then(Commands.argument("target", EntityArgument.player())
                        .executes(ctx -> addCoin(EntityArgument.getPlayer(ctx, "target")))
                )
        );
        d.register(Commands.literal("addMana")
                .requires(src -> src.hasPermission(2))
                .executes(ctx -> addMana(ctx.getSource().getPlayerOrException()))
                .then(Commands.argument("target", EntityArgument.player())
                        .executes(ctx -> addMana(EntityArgument.getPlayer(ctx, "target")))
                )
        );
        d.register(Commands.literal("removeMana")
                .requires(src -> src.hasPermission(2))
                .executes(ctx -> removeMana(ctx.getSource().getPlayerOrException()))
                .then(Commands.argument("target", EntityArgument.player())
                        .executes(ctx -> removeMana(EntityArgument.getPlayer(ctx, "target")))
                )
        );
        d.register(Commands.literal("testChatBox")
                .requires(src -> src.hasPermission(2))
                .executes(ctx -> testChatbox(ctx.getSource().getPlayerOrException()))
                .then(Commands.argument("target", EntityArgument.player())
                        .executes(ctx -> testChatbox(EntityArgument.getPlayer(ctx, "target")))
                )
        );
        d.register(Commands.literal("resetChatBox")
                .requires(src -> src.hasPermission(2))
                .executes(ctx -> resetChatbox(ctx.getSource().getPlayerOrException()))
                .then(Commands.argument("target", EntityArgument.player())
                        .executes(ctx -> resetChatbox(EntityArgument.getPlayer(ctx, "target")))
                )
        );
        d.register(Commands.literal("EndMainTask")
                .requires(src -> src.hasPermission(2))
                .executes(ctx -> EndMainTask(ctx.getSource().getPlayerOrException()))
                .then(Commands.argument("target", EntityArgument.player())
                        .executes(ctx -> resetChatbox(EntityArgument.getPlayer(ctx, "target")))
                )
        );
        d.register(Commands.literal("ResetMainTask")
                .requires(src -> src.hasPermission(2))
                .executes(ctx -> resetMainTask(ctx.getSource().getPlayerOrException()))
                .then(Commands.argument("target", EntityArgument.player())
                        .executes(ctx -> resetMainTask(EntityArgument.getPlayer(ctx, "target")))
                )
        );
    }

    private static int addOneSP(ServerPlayer player) {
        Skill skill = ((IImmortalPlayer) player).getSkill();
        skill.Skill_Point += 1;
        skill.Skill_Upgrade_Point += 1;
        CompoundTag confirmed = skill.getSkillCompoundTag(skill.getNumInfo()); // 若服务端做校正，这里返回校正后值
        ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),new SkillInfoSyncToClientPacket(confirmed));
        player.displayClientMessage(net.minecraft.network.chat.Component.literal("SP/SUP +1"), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int addCoin(ServerPlayer player) {
        Coin coin = ((IImmortalPlayer) player).getCoin();
        coin.add(1000);
        int confirmed = coin.get();
        ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),new CoinSyncToClientPacket(confirmed));
        player.displayClientMessage(net.minecraft.network.chat.Component.literal("SP/SUP +1"), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int addMana(ServerPlayer player) {
        ManaPoint manaPoint = ((IImmortalPlayer) player).getManaPoint();
        manaPoint.add(10, (int) player.getAttributeValue(ModAttributes.IMMORTAL_MAGIC_POINT_MAX.get()));
        int confirmed = manaPoint.get();
        ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),new ManaSyncToClientPacket(confirmed));
        player.displayClientMessage(net.minecraft.network.chat.Component.literal("Mana +10"), true);
        return Command.SINGLE_SUCCESS;
    }
    private static int removeMana(ServerPlayer player) {
        ManaPoint manaPoint = ((IImmortalPlayer) player).getManaPoint();
        manaPoint.remove(5);
        int confirmed = manaPoint.get();
        ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),new ManaSyncToClientPacket(confirmed));
        player.displayClientMessage(net.minecraft.network.chat.Component.literal("Mana -5"), true);
        return Command.SINGLE_SUCCESS;
    }
    private static int testChatbox(ServerPlayer player) {
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        iImmortalPlayer.getChatbox().Chatbox_id = 1;
        iImmortalPlayer.getChatbox().Chatbox_num = 0;
        iImmortalPlayer.getChatbox().Chatbox_countdown = 0;
        ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),new ChatboxSyncToClientPacket(iImmortalPlayer.getChatbox().Chatbox_id,iImmortalPlayer.getChatbox().Chatbox_num,iImmortalPlayer.getChatbox().Chatbox_countdown));
        //player.displayClientMessage(net.minecraft.network.chat.Component.literal("TestChatbox"), true);
        return Command.SINGLE_SUCCESS;
    }
    private static int resetChatbox(ServerPlayer player) {
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        iImmortalPlayer.getChatbox().Chatbox_id = -1;
        iImmortalPlayer.getChatbox().Chatbox_num = -1;
        iImmortalPlayer.getChatbox().Chatbox_countdown = 0;
        ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),new ChatboxSyncToClientPacket(iImmortalPlayer.getChatbox().Chatbox_id,iImmortalPlayer.getChatbox().Chatbox_num,iImmortalPlayer.getChatbox().Chatbox_countdown));
        player.displayClientMessage(net.minecraft.network.chat.Component.literal("ResetChatbox"), true);
        return Command.SINGLE_SUCCESS;
    }
    private static int resetMainTask(ServerPlayer player) {
        IImmortalPlayer iImmortalPlayer = (IImmortalPlayer) player;
        iImmortalPlayer.setMainTask(0);
        ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),new MainTaskSyncToClientPacket(0));
        return Command.SINGLE_SUCCESS;
    }
    private static int EndMainTask(ServerPlayer player) {
        EndOfMainTask(player);
        return Command.SINGLE_SUCCESS;
    }
}
