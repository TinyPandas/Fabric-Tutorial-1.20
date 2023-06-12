package net.panda.tutorialmod.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.panda.tutorialmod.networking.ModMessages;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_CUSTOM = "key.category.tutorialmod.custom";
    public static final String KEY_CUSTOM = "key.tutorialmod.custom";

    public static KeyBinding customKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (customKey.wasPressed()) {
                ClientPlayNetworking.send(ModMessages.CUSTOM_ID, PacketByteBufs.create());
            }
        });
    }

    public static void register() {
        customKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(KEY_CUSTOM, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_O, KEY_CATEGORY_CUSTOM));

        registerKeyInputs();
    }
}
