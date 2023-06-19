package net.panda.tutorialmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.panda.tutorialmod.block.entity.FilledCustomBlockEntity;
import net.panda.tutorialmod.screen.FilledCustomBlockScreenHandler;

public class EnergySyncPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        long energy = buf.readLong();
        BlockPos position = buf.readBlockPos();

        if (client.world.getBlockEntity(position) instanceof FilledCustomBlockEntity blockEntity) {
            blockEntity.setEnergyLevel(energy);

            if (client.player.currentScreenHandler instanceof FilledCustomBlockScreenHandler screenHandler &&
                    screenHandler.blockEntity.getPos().equals(position)) {
                blockEntity.setEnergyLevel(energy);
            }
        }
    }
}
