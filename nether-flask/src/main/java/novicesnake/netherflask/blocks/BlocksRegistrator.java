package novicesnake.netherflask.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.Instrument;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import novicesnake.netherflask.NetherFlask;
import novicesnake.netherflask.blocks.blockentity.StardustCampfireBlockEntity;

/***
 * This Class registers the Blocks and BlockEntity's.
 */
public class BlocksRegistrator {

    public static BlockEntityType<StardustCampfireBlockEntity> STARDUST_CAMPFIRE_ENTITY;
    public static  Block STARDUST_CAMPFIRE;


    /**
     * This Method initializes and registers all Blocks and corresponding Entity
     * */
    public static void initializeAndRegister()
    {
        STARDUST_CAMPFIRE = Registry.register(Registries.BLOCK,
                new Identifier(NetherFlask.MOD_ID, "stardust-campfire-block"),
                new StardustCampfireBlock(false, 4,
                        FabricBlockSettings.create()
                                .mapColor(MapColor.SPRUCE_BROWN)
                                .instrument(Instrument.BASS)
                                .strength(2.5F)
                                .sounds(BlockSoundGroup.WOOD)
                                .luminance(Blocks.createLightLevelFromLitBlockState(15))
                                .nonOpaque()
                                .burnable()
                        )
                );


        STARDUST_CAMPFIRE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(NetherFlask.MOD_ID, "stardust-campfire-entity"),
                FabricBlockEntityTypeBuilder.create(StardustCampfireBlockEntity::new, STARDUST_CAMPFIRE).build());

    }
}
