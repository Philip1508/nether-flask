package novicesnake.netherflask.blocks;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import novicesnake.netherflask.NetherFlask;
import novicesnake.netherflask.blocks.blockentity.StardustCampfireBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;


/**
 * This Class defines the Stardust Campfire Block.
 */
public class StardustCampfireBlock extends CampfireBlock {

    /**
     * Constructor of the Block - NOT meant to be called outside the Registration Context!
     * @param emitsParticles
     * @param fireDamage
     * @param settings
     */
    public StardustCampfireBlock(boolean emitsParticles, int fireDamage, Settings settings) {
        super(emitsParticles, fireDamage, settings);
    }


    /**
     * This Methods returns the Stardust Campfire BlockType.
     * This is necessary, because the normal Campfire makes it impossible to define it propertly.
     * @param pos
     * @param state
     * @return
     */
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state)
    {
        return new StardustCampfireBlockEntity(pos, state);
    }


    /**
     *
     * Override and Replica of Super getTicker()
     * Manipulated to use the Stardust Campfire EntityType.
     * @param world
     * @param state
     * @param type
     * @return
     * @param <T>
     */
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient) {
            return state.get(LIT) ? checkType(type, BlocksRegistrator.STARDUST_CAMPFIRE_ENTITY, CampfireBlockEntity::clientTick) : null;
        } else {
            return state.get(LIT)
                    ? checkType(type, BlocksRegistrator.STARDUST_CAMPFIRE_ENTITY, StardustCampfireBlockEntity::tick)
                    : checkType(type, BlocksRegistrator.STARDUST_CAMPFIRE_ENTITY, StardustCampfireBlockEntity::unlitServerTick);
        }


    }
}
