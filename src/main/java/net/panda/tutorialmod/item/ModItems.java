package net.panda.tutorialmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.panda.tutorialmod.ExampleMod;
import net.panda.tutorialmod.item.custom.CustomWandItem;

import static net.panda.tutorialmod.Util.id;

public class ModItems {

    public static final Item CUSTOM_ITEM = registerItem("custom_item",
            new Item(new FabricItemSettings()),
            ModItemGroup.registryKey());
    public static final Item FILLED_CUSTOM_ITEM = registerItem("filled_custom_item",
            new Item(new FabricItemSettings()),
            ModItemGroup.registryKey());

    public static final Item CUSTOM_WAND_ITEM = registerItem("custom_wand_item",
            new CustomWandItem(new FabricItemSettings().maxCount(1)),
            ModItemGroup.registryKey());

    public static final Item CUSTOM_BUCKET = registerItem("custom_bucket_item",
            new CustomBucket(4996656, new Item.Settings()),
            ModItemGroup.registryKey());

    public static final Item CUSTOM_BUCKET_2 = registerItem("custom_bucket_item_2",
            new CustomBucket(589707, new Item.Settings()),
            ModItemGroup.registryKey());

    private static Item registerItem(String name, Item item, RegistryKey<ItemGroup> itemGroup) {
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(new ItemStack(item)));
        return Registry.register(Registries.ITEM, id(name), item);
    }

    public static void registerModItems() {
        ExampleMod.LOGGER.debug("Registering Mod Items for " + ExampleMod.MODID);
    }
}
