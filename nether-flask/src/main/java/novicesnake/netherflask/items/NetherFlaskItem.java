package novicesnake.netherflask.items;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import novicesnake.netherflask.config.NetherFlaskRuntimeData;
import novicesnake.netherflask.statuseffect.StatusEffectRegistrator;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/***
 * This Class defines the Nether Flask Item and its correspondig Logic.
 * Some Logic is Outsorced to the DurabilityNumberItemstack Interface!
 */
public class NetherFlaskItem extends PotionItem implements DurabilityNumberItemstack {
    private static final String NETHER_FLASK_COMPBOUND = "NETHER_FLASK_COMPBOUND";



    private static final String IMBUED_WITH_POTION = "IMBUED_WITH_POTION";
    private static final String NETHER_FLASK_LEVEL = "NETHER_FLASK_LEVEL";

    private static final String NETHER_FLASK_CACHED_MODIFIER = "CACHED_MODIFIER";
    private static final String INITIALIZED = "INITIALIZED";
    private static final String NEAR_CAMPFIRE = "NEAR_CAMPFIRE";






    public NetherFlaskItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultStack()
    {
        ItemStack netherFlaskDefault = new ItemStack(ItemRegistrator.NETHER_FLASK);
        initializeFlask(netherFlaskDefault);
        return netherFlaskDefault;
    }


    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        NbtCompound mainNbt = stack.getOrCreateSubNbt(NETHER_FLASK_COMPBOUND);




        if (!mainNbt.getBoolean(INITIALIZED)) // => Initialize the Flask
        {
            if (!stack.getOrCreateSubNbt(NETHER_FLASK_COMPBOUND).getBoolean(INITIALIZED))
            {
                initializeFlask(stack);
            }
        }
        else
        {
            boolean nearCampfire = mainNbt.getBoolean(NEAR_CAMPFIRE);

            int timer = nearCampfire ? 50 : 400;

            if (world.getTime() % timer == 0)
            {
                if (entity instanceof PlayerEntity player)
                {
                    mainNbt.putBoolean(NEAR_CAMPFIRE, player.getStatusEffect(StatusEffectRegistrator.FLASK_RECHARGER) != null);
                }

                updateEffects(stack);
            }
        }





    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack offHandStack = user.getOffHandStack();

        NbtCompound flaskMainCompbound = user.getStackInHand(hand).getOrCreateSubNbt(NETHER_FLASK_COMPBOUND);
        boolean isImbued = flaskMainCompbound.getBoolean(IMBUED_WITH_POTION);
        boolean nearCampfire = flaskMainCompbound.getBoolean(NEAR_CAMPFIRE);

        if (hand == Hand.MAIN_HAND && offHandStack.isOf(Items.POTION) && !isImbued && nearCampfire)
        {
            List<StatusEffectInstance> scaledEffects = new ArrayList<>();
            for (StatusEffectInstance effect : PotionUtil.getPotion(offHandStack).getEffects()) {
                StatusEffect type = effect.getEffectType();

                int dur = effect.getDuration();
                int amp = effect.getAmplifier();
                StatusEffectInstance changedEffect = new StatusEffectInstance(type, dur, amp);
                scaledEffects.add(changedEffect);
            }

            PotionUtil.setCustomPotionEffects(user.getStackInHand(hand), scaledEffects);


            flaskMainCompbound.putBoolean(IMBUED_WITH_POTION, true);

            offHandStack.decrement(1);
            user.giveItemStack(Items.GLASS_BOTTLE.getDefaultStack());
            world.playSound(null,user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.AMBIENT, 1f,0.3f);

        }

        if (hand == Hand.MAIN_HAND && nearCampfire &&

                (offHandStack.isOf(ItemRegistrator.NETHER_FLASK_SHARD)
                || offHandStack.isOf(ItemRegistrator.BONE_DUST)
                || offHandStack.isOf(ItemRegistrator.PURGING_STONE))

        )
        {

            if (offHandStack.isOf(ItemRegistrator.BONE_DUST))
            {
                if (upgradeFlaskPower(user.getStackInHand(hand)))
                {
                    offHandStack.decrement(1);
                    updateEffects(user.getStackInHand(hand));
                    world.playSound(null,user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.AMBIENT, 1f,0.5f);

                }

            }

            if (offHandStack.isOf(ItemRegistrator.NETHER_FLASK_SHARD))
            {
                if (upgradeFlaskMaximum(user.getStackInHand(hand)))
                {
                    offHandStack.decrement(1);
                    world.playSound(null,user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_ANVIL_USE, SoundCategory.AMBIENT, 1f,0.8f);

                }

            }

            if (offHandStack.isOf(ItemRegistrator.PURGING_STONE) && isImbued )
            {
                PotionUtil.setPotion(user.getStackInHand(hand), Potions.WATER);
                NbtCompound absoluteNbt = user.getStackInHand(hand).getNbt();
                if (absoluteNbt != null)
                {
                    absoluteNbt.remove("CustomPotionEffects");

                }

                world.playSound(null,user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.AMBIENT, 1f,0.5f);

                user.getStackInHand(hand).getOrCreateSubNbt(NETHER_FLASK_COMPBOUND).putBoolean(IMBUED_WITH_POTION, false);
                user.getOffHandStack().decrement(1);

            }


        }



        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user)
    {

        if (user instanceof PlayerEntity player)
        {

            if (player instanceof ServerPlayerEntity serverPlayer)
            {
                Criteria.CONSUME_ITEM.trigger(serverPlayer, stack);

                List<StatusEffectInstance> effectList = PotionUtil.getPotionEffects(stack);

                effectList.forEach(statusEffectInstance -> {
                    if (statusEffectInstance.getEffectType().isInstant())
                    {
                        statusEffectInstance.getEffectType().applyInstantEffect(player, player, user, statusEffectInstance.getAmplifier(), 1.0);
                    }
                    else {
                        StatusEffect type = statusEffectInstance.getEffectType();

                        int duration = Math.round(statusEffectInstance.getDuration() *
                                stack.getOrCreateSubNbt(NETHER_FLASK_COMPBOUND).getFloat(NETHER_FLASK_CACHED_MODIFIER));
                        int amp = statusEffectInstance.getAmplifier();

                        user.addStatusEffect(new StatusEffectInstance(type, duration, amp));
                    }

                } );

                if (!player.getAbilities().creativeMode)
                {
                    decrementUses(stack);
                }

            }


            player.incrementStat(Stats.USED.getOrCreateStat(this));


        }

        user.emitGameEvent(GameEvent.DRINK);

        return stack;



    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        PotionUtil.buildTooltip(stack, tooltip, stack.getOrCreateSubNbt(NETHER_FLASK_COMPBOUND).getFloat(NETHER_FLASK_CACHED_MODIFIER));
        tooltip.add(Text.translatable("item.nether-flask.tooltipuses", getUses(stack), getSoftMax(stack)));

        if (!stack.getOrCreateSubNbt(NETHER_FLASK_COMPBOUND).getBoolean(IMBUED_WITH_POTION))
        {
            tooltip.add(Text.translatable("item.nether-flask.tooltip-imbuehint"));
            tooltip.add(Text.translatable("item.nether-flask.tooltip-imbuehint-warning"));
        }

    }

    @Override
    public Text getName(ItemStack stack) {

        String string =  " +"+stack.getOrCreateSubNbt(NETHER_FLASK_COMPBOUND).getInt(NETHER_FLASK_LEVEL);
            return Text.of(Text.translatable("item.nether-flask.nether-flask").getString() + string);
        }


    @Override
    public UseAction getUseAction(ItemStack stack) {

        if (getUses(stack) <= 0)
        {
            return UseAction.NONE;
        }

        return UseAction.DRINK;
    }




    private void initializeFlask(ItemStack item)
    {

        int configuredStartUses = NetherFlaskRuntimeData.loadedConfiguration.newFlaskMaxUses;
        int configuredHardMaximumUses = NetherFlaskRuntimeData.loadedConfiguration.flaskMaxUpgradableUses;

        setUses(item, configuredStartUses);
        setNbtSoftmax(item, configuredStartUses);

        setAbsoluteMaximum(item, configuredHardMaximumUses);

        updateEffects(item);

        PotionUtil.setPotion(item, Potions.WATER);
        item.getOrCreateSubNbt(NETHER_FLASK_COMPBOUND).putBoolean(INITIALIZED, true);
        item.getOrCreateSubNbt(NETHER_FLASK_COMPBOUND).putInt(NETHER_FLASK_LEVEL, 0);
    }


    private void updateEffects(ItemStack item)
    {
        int flaskLevel = item.getOrCreateSubNbt(NETHER_FLASK_COMPBOUND).getInt(NETHER_FLASK_LEVEL);

        float baseModifier = NetherFlaskRuntimeData.loadedConfiguration.flaskBaseDurationModifier;
        float flaskLevelScaleDurationModifier = NetherFlaskRuntimeData.loadedConfiguration.flaskLevelScaleDurationModifier;

        item.getOrCreateSubNbt(NETHER_FLASK_COMPBOUND).putFloat(NETHER_FLASK_CACHED_MODIFIER,
                baseModifier + (flaskLevelScaleDurationModifier * flaskLevel));
    }


    private boolean upgradeFlaskPower(ItemStack item)
    {
        NbtCompound mainCompbound = item.getOrCreateSubNbt(NETHER_FLASK_COMPBOUND);

        int maximumLevel = NetherFlaskRuntimeData.loadedConfiguration.flaskMaxLevel;

        int level = mainCompbound.getInt(NETHER_FLASK_LEVEL);
        if (level + 1  <= maximumLevel)
        {
            mainCompbound.putInt(NETHER_FLASK_LEVEL, level + 1);
            return true;
        }
        return false;

    }


    private boolean upgradeFlaskMaximum(ItemStack item)
    {

        int softMax = getSoftMax(item);
        if (softMax + 1  <= getAbsoluteMaximum(item))
        {
            setNbtSoftmax(item, softMax+1);
            return true;
        }
        return false;
    }



    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getOrCreateSubNbt(NETHER_FLASK_COMPBOUND).getBoolean(NEAR_CAMPFIRE);
    }

    @Override
    public int getAbsoluteMaximum(ItemStack stack) {
        return NetherFlaskRuntimeData.loadedConfiguration.flaskMaxUpgradableUses;
    }




}
