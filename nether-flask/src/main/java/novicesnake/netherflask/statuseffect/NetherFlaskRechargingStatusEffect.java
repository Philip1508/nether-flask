package novicesnake.netherflask.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import novicesnake.netherflask.NetherFlask;
import novicesnake.netherflask.items.ItemRegistrator;
import novicesnake.netherflask.items.NetherFlaskItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/***
 * This Class defines the Status Effect for recharging the Nether Flask, which also unlocks the upgrading by being
 * on the player.
 * Has been Extended for Overriding and to avoid Code Injection into the vanilla code.
 */
public class NetherFlaskRechargingStatusEffect extends StatusEffect {


    // Random Number Generator to select a random flask in inventory.
    private static final Random RANDOM_NUMBER_GENERATOR = new Random();


    // Constructor of the StatusEffect
    protected NetherFlaskRechargingStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }




    /**
     * This method calculates the recharging of the flask.
     * It fetches the owner of the flask and chooses a single, randomly chosen, flask in his inventory to recharge.
     * @param living - The Entity holding the flask.
     * @param amplifier - Standard Amplifier of StatusEffect - Obsolete in this version.
     * */
    @Override
    public void applyUpdateEffect(LivingEntity living, int amplifier)
    {

        World world = living.getWorld();
        // Check if we're on the server and 30 Seconds have passed (30 * 20 Ticks) 20 Ticks are 1 Second.
        if (living instanceof ServerPlayerEntity player && world.getTime() % (30*20) == 0)
        {

                // This Fetches all Flasks from the player and puts them in an array.
                List<ItemStack> itemList = new ArrayList<>();
                int invSize = player.getInventory().size();

                for (int i = 0; i < invSize; i++)
                {
                    if (player.getInventory().getStack(i).isOf(ItemRegistrator.NETHER_FLASK) )
                    {
                        itemList.add(player.getInventory().getStack(i));
                    }

                }


                int size = itemList.size();

                if (size == 0) // => Return because no Flasks were found.
                {
                    return;
                }

                int toRecharge = 0;

                if (size > 1) // => If there are more than 1 Flasks, Roll the Dice
                {
                    try
                    {
                        toRecharge = RANDOM_NUMBER_GENERATOR.nextInt(0, size);
                    }
                    catch (Exception e)
                    {
                        NetherFlask.LOGGER.error("Random Number Generation on which flask to refill has failed!");
                    }
                }

                ItemStack finalItem = itemList.get(toRecharge);

                if (finalItem.getItem() instanceof NetherFlaskItem netherFlaskItem) // Safety Check.
                {
                    if (netherFlaskItem.incrementUses(finalItem)) // => If Recharge Successfull then Play sound
                    {
                        world.playSound(null,player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.AMBIENT, 0.6f,0.3f);
                    }
                }




        }

    }


    /**
     * This method returns a boolean wether the status effect can be applied or not.
     * This has more complicated logic in the parent class, however returning true is necessary for the logic
     * of the Flask Recharging.
     * @return - True;
     * */
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier)
    {
        return true;
    }


    /**
     * This method returns a boolean wether the status effect is beneficial, which it is.
     * */
    @Override
    public boolean isBeneficial()
    {
        return true;
    }


}




