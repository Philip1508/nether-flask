package novicesnake.netherflask.mixin;


import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import novicesnake.netherflask.NetherFlask;
import novicesnake.netherflask.items.ItemRegistrator;

import novicesnake.netherflask.items.NetherFlaskItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Mixin(CampfireBlockEntity.class)
public class CampfireFlaskrestorationMixin {



    @Inject(at = @At("HEAD"), method = "litServerTick")
    private static void litServerTick(World world, BlockPos pos, BlockState state, CampfireBlockEntity campfire, CallbackInfo ci)
    {
        if (state.getBlock() instanceof CampfireBlock campfireBlock)
        {
            Random numberGenerator = new Random();

            if (campfireBlock.getTranslationKey().equals("block.minecraft.soul_campfire") && world.getTime() % (20*20) == 0)
            {

                Box box = new Box(pos);
                world.getOtherEntities(null, box.expand(20))
                        .stream().filter(entity -> entity.getBlockPos().getManhattanDistance(pos) < 12)
                        .toList().forEach(entity -> {


                            if (entity instanceof PlayerEntity player)
                            {
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

                                if (size == 0)
                                {
                                    return;
                                }

                                int toRecharge = 0;

                                if (size > 0)
                                {
                                    try
                                    {
                                        toRecharge = numberGenerator.nextInt(0, size);
                                    }
                                    catch (Exception e)
                                    {
                                        NetherFlask.LOGGER.error("Random Number Generation on which flask to refill has failed!");
                                    }
                                }

                                ItemStack finalItem = itemList.get(toRecharge);

                                if (finalItem.getItem() instanceof NetherFlaskItem netherFlaskItem)
                                {
                                    if (netherFlaskItem.incrementUses(finalItem))
                                    {
                                        world.playSound(null,pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.AMBIENT, 0.6f,0.3f);
                                    }
                                }
                            }
                        });


            }

        }



    }
}
