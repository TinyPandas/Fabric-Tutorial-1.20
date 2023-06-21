package net.panda.tutorialmod.util;

import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModelUtil {

    public static Model item(String parent, TextureKey...requiredTextureKeys) {
        return new Model(Optional.of(new Identifier("tutorialmod", "item/" + parent)), Optional.empty(), requiredTextureKeys);
    }
}
