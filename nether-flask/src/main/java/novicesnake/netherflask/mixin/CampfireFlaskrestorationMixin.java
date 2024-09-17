package novicesnake.netherflask.mixin;


import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
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
import novicesnake.netherflask.statuseffect.StatusEffectRegistrator;
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

            if (campfireBlock.getTranslationKey().equals("block.minecraft.soul_campfire") && world.getTime() % (8*20) == 0)
            {

                Box box = new Box(pos);
                world.getOtherEntities(null, box.expand(20))
                        .stream().filter(entity -> entity.getBlockPos().getManhattanDistance(pos) < 12)
                        .toList().forEach(entity -> {


                            if (entity instanceof PlayerEntity player)
                            {
                                player.addStatusEffect(new StatusEffectInstance(StatusEffectRegistrator.FLASK_RECHARGER, 10*20, 0, true, true, false));
                            }

                        });


            }

        }



    }
}
