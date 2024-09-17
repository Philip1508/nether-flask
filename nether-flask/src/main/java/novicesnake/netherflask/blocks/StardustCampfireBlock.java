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

public class StardustCampfireBlock extends CampfireBlock {
    public StardustCampfireBlock(boolean emitsParticles, int fireDamage, Settings settings) {
        super(emitsParticles, fireDamage, settings);
    }




    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state)
    {
        return new StardustCampfireBlockEntity(pos, state);
    }


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
