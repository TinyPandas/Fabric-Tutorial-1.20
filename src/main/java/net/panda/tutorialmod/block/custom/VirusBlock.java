package net.panda.tutorialmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.panda.tutorialmod.Util.translationKey;

public class VirusBlock extends Block {

    public VirusBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 200));
        }

        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public void precipitationTick(BlockState state, World world, BlockPos pos, Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.RAIN) {
            BlockPos randomNextBlockPos = getRandomBlockPosOffset(pos);
            BlockState nextState = world.getBlockState(randomNextBlockPos);
            if (nextState != Blocks.AIR.getDefaultState()) {
                world.setBlockState(randomNextBlockPos, this.getDefaultState());
            }
        }
        super.precipitationTick(state, world, pos, precipitation);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);
        tooltip.add(Text.translatable(translationKey("tooltip", "virus_block")).formatted(Formatting.DARK_GREEN));
    }

    private BlockPos getRandomBlockPosOffset(BlockPos pos) {
        int randomDir = Random.createLocal().nextInt();
        Direction dir = Direction.byId(randomDir);
        return pos.offset(dir);
    }
}
