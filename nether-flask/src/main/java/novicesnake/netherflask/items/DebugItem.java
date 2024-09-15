package novicesnake.netherflask.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DebugItem extends Item {

    public DebugItem(Settings settings) {
        super(settings);
    }



    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        for (int i= 0; i < user.getInventory().size(); ++i)
        {
            ItemStack item = user.getInventory().getStack(i);
            if (item.getItem() instanceof NetherFlaskItem netherFlaskItem)
            {
                PotionUtil.setPotion(item, Potions.REGENERATION);

                netherFlaskItem.setUses(item, 3);
                netherFlaskItem.setNbtSoftmax(item, 12);


            }
        }





        return TypedActionResult.pass(user.getStackInHand(hand));

    }

}
