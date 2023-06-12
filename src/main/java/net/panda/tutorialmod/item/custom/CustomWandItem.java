package net.panda.tutorialmod.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.panda.tutorialmod.Util;
import net.panda.tutorialmod.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomWandItem extends Item {

    public CustomWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world != null && !world.isClient()) {
            BlockPos pos = context.getBlockPos();
            world.setBlockState(pos, ModBlocks.FILLED_CUSTOM_BLOCK.getDefaultState());

            PlayerEntity user = context.getPlayer();
            if (user != null) {
                applyCooldown(user, 100);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient() && hand == Hand.MAIN_HAND) {
            outputRandomNumber(user);
            applyCooldown(user, 20);
        }

        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable(Util.translationKey("tooltip", "custom_wand_item")).formatted(Formatting.AQUA));
    }

    private void outputRandomNumber(PlayerEntity player) {
        player.sendMessage(Text.literal("Your Number is " + getRandomNumber()));
    }

    private int getRandomNumber() {
        return Random.createLocal().nextInt(10);
    }

    private void applyCooldown(PlayerEntity user, int ticks) {
        user.getItemCooldownManager().set(this, ticks);
    }
}
