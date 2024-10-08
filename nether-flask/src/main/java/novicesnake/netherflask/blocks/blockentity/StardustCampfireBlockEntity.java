package novicesnake.netherflask.blocks.blockentity;

import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import novicesnake.netherflask.blocks.BlocksRegistrator;
import novicesnake.netherflask.statuseffect.StatusEffectRegistrator;


/**
 * This Class defines the Stardust Campfire BlockEntity
 */
public class StardustCampfireBlockEntity extends CampfireBlockEntity {


    /**
     * Not meant to be used outside of registration context.
     * @param pos
     * @param state
     */
    public StardustCampfireBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }




    /**
     * This is necerssary because the Campfire blocks the Definition of the Type...
     * @return - BlockEntityType of Stardust Campfire.
     * */
    @Override
    public BlockEntityType<?> getType() {
        return BlocksRegistrator.STARDUST_CAMPFIRE_ENTITY;
    }


    /**
     * This Method defines the Tick of the StardustCampfire.
     * It calls the "super" Server Tick and then apply's the Recharge Effect to nearby players.
     * @param world
     * @param pos
     * @param state
     * @param campfire
     */
    public static void tick(World world, BlockPos pos, BlockState state, CampfireBlockEntity campfire)
    {

        litServerTick(world, pos, state, campfire);

        if (world.getTime() % (8*20) != 0)
        {
            return;
        }

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
