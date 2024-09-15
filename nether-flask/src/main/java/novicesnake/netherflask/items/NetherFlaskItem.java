package novicesnake.netherflask.items;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NetherFlaskItem extends PotionItem implements DurabilityNumberItemstack {
    public NetherFlaskItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultStack()
    {
        return ItemStack.EMPTY;
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
                        user.addStatusEffect(new StatusEffectInstance(statusEffectInstance));
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
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(Text.of(String.valueOf(getUses(stack))));
        }

        @Override

        public Text getName(ItemStack stack) {

            return Text.translatable("item.nether-flask.nether-flask");

        }


    @Override
    public UseAction getUseAction(ItemStack stack) {

        if (getUses(stack) <= 0)
        {
            return UseAction.NONE;
        }

        return UseAction.DRINK;
    }


}
