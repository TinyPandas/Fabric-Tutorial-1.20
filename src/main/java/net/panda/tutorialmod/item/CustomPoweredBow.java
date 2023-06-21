package net.panda.tutorialmod.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import reborncore.common.powerSystem.RcEnergyItem;
import reborncore.common.powerSystem.RcEnergyTier;

public class CustomPoweredBow extends BowItem implements RcEnergyItem {

    private final int maxEnergy;
    private final RcEnergyTier tier;
    private final int costPerArrow;

    public CustomPoweredBow(Settings settings, int maxEnergy, RcEnergyTier tier, int costPerArrow) {
        super(settings);
        this.maxEnergy = maxEnergy;
        this.tier = tier;
        this.costPerArrow = costPerArrow;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        super.onStoppedUsing(stack, world, user, remainingUseTicks);

        int i = this.getMaxUseTime(stack) - remainingUseTicks;
        float progress = getPullProgress(i);
        if (!((double)progress < 0.1)) {
            tryUseEnergy(stack, costPerArrow);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (getEnergyCapacity() < costPerArrow) {
            return TypedActionResult.pass(itemStack);
        }

        return super.use(world, user, hand);
    }

    @Override
    public long getEnergyCapacity() {
        return maxEnergy;
    }

    @Override
    public RcEnergyTier getTier() {
        return tier;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.round((getStoredEnergy(stack) * 100f / getEnergyCapacity() * 13) / 100);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return 0x1076AD;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }
}
