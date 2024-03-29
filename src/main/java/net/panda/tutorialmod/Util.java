package net.panda.tutorialmod;

import net.minecraft.util.Identifier;

public class Util {

    public static Identifier id(String name) {
        return new Identifier(ExampleMod.MODID, name);
    }

    public static String translationKey(String prefix, String name) {
        return String.format("%s.%s.%s", prefix, ExampleMod.MODID, name);
    }
}
