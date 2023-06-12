package net.panda.tutorialmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.panda.tutorialmod.ExampleMod;

import static net.panda.tutorialmod.Util.id;

public class ModItems {

    public static final Item CUSTOM_ITEM = registerItem("custom_item",
            new Item(new FabricItemSettings()),
            ModItemGroup.registryKey());
    public static final Item FILLED_CUSTOM_ITEM = registerItem("filled_custom_item",
            new Item(new FabricItemSettings()),
            ModItemGroup.registryKey());

    private static Item registerItem(String name, Item item, RegistryKey<ItemGroup> itemGroup) {
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(new ItemStack(item)));
        return Registry.register(Registries.ITEM, id(name), item);
    }

    public static void registerModItems() {
        ExampleMod.LOGGER.debug("Registering Mod Items for " + ExampleMod.MODID);
    }
}