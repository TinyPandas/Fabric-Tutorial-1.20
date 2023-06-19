package net.panda.tutorialmod.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.panda.tutorialmod.networking.packet.EnergySyncPacket;

import static net.panda.tutorialmod.networking.ModMessages.ENERGY_SYNC;

public class ClientModMessages {
    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ENERGY_SYNC, EnergySyncPacket::receive);
    }
}
