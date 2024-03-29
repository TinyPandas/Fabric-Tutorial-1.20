package net.panda.tutorialmod.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.panda.tutorialmod.networking.packet.CustomC2SPacket;

import static net.panda.tutorialmod.Util.id;

public class ModMessages {

    public static final Identifier CUSTOM_ID = id("custom_message");
    public static final Identifier ENERGY_SYNC = id("energy_sync");
    public static final Identifier FLUID_SYNC = id("fluid_sync");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(CUSTOM_ID, CustomC2SPacket::receive);
    }
}
