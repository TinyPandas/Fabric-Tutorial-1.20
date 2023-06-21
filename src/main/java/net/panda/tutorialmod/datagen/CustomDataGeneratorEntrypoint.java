package net.panda.tutorialmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.panda.tutorialmod.item.ModItems;
import net.panda.tutorialmod.util.ModelUtil;

public class CustomDataGeneratorEntrypoint implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        final FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(CustomModelProvider::new);
    }
    
    private static class CustomModelProvider extends FabricModelProvider {
        private CustomModelProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        }

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {
            itemModelGenerator.register(ModItems.CUSTOM_BUCKET, ModelUtil.item("template_bucket"));
            itemModelGenerator.register(ModItems.CUSTOM_BUCKET_2, ModelUtil.item("template_bucket"));
        }
    }
}
