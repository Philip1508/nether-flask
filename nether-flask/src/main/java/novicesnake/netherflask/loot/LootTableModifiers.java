package novicesnake.netherflask.loot;

import net.fabricmc.fabric.api.loot.v2.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import novicesnake.netherflask.items.ItemRegistrator;
/**
 * This Injects into the Loot Tables of the game.
 * */
public class LootTableModifiers {


    private static final Identifier WITHER_TABLE = new Identifier("minecraft", "entities/wither" );
    private static final Identifier WITHER_SKELETON_TABLE = new Identifier("minecraft", "entities/wither_skeleton" );
    private static final Identifier RUINED_PORTAL = new Identifier("minecraft", "chests/ruined_portal" );


    private static final Identifier ABANDONED_MINESHAFT_TABLE = new Identifier("minecraft", "chests/abandoned_mineshaft" );
    private static final Identifier BURIED_TREASURE_TABLE = new Identifier("minecraft", "chests/buried_treasure" );
    private static final Identifier DESERT_PYRAMID = new Identifier("minecraft", "chests/desert_pyramid" );
    private static final Identifier JUNGLE_TEMPLE = new Identifier("minecraft", "chests/jungle_temple" );
    private static final Identifier SIMPLE_DUNGEON = new Identifier("minecraft", "chests/simple_dungeon" );


    /**
     * Initialized Loot Table Injection.
     */
    public static void initializeAndRegister()
    {

        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {

            if (WITHER_TABLE.equals(id))
            {
                LootPool.Builder poolBuilder= LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.4f))
                        .with(ItemEntry.builder(ItemRegistrator.NETHER_FLASK_SHARD))
                        .with(ItemEntry.builder(ItemRegistrator.BONE_DUST))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,1)).build());

                tableBuilder.pool(poolBuilder.build());
            }

            if (WITHER_SKELETON_TABLE.equals(id))
            {
                LootPool.Builder poolBuilder= LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.02f))
                        .with(ItemEntry.builder(ItemRegistrator.NETHER_FLASK_SHARD))
                        .with(ItemEntry.builder(ItemRegistrator.BONE_DUST))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,1)).build());

                tableBuilder.pool(poolBuilder.build());
            }


            if (RUINED_PORTAL.equals(id))
            {
                LootPool.Builder poolBuilder= LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.22f))
                        .with(ItemEntry.builder(ItemRegistrator.NETHER_FLASK_SHARD))
                        .with(ItemEntry.builder(ItemRegistrator.BONE_DUST))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,1)).build());

                tableBuilder.pool(poolBuilder.build());
            }

            if (ABANDONED_MINESHAFT_TABLE.equals(id))
            {
                LootPool.Builder poolBuilder= LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.15f))
                        .with(ItemEntry.builder(ItemRegistrator.NETHER_FLASK_SHARD))
                        .with(ItemEntry.builder(ItemRegistrator.BONE_DUST))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,1)).build());

                tableBuilder.pool(poolBuilder.build());
            }

            if (BURIED_TREASURE_TABLE.equals(id))
            {
                LootPool.Builder poolBuilder= LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.5f))
                        .with(ItemEntry.builder(ItemRegistrator.NETHER_FLASK_SHARD))
                        .with(ItemEntry.builder(ItemRegistrator.BONE_DUST))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,2)).build());

                tableBuilder.pool(poolBuilder.build());
            }

            if (DESERT_PYRAMID.equals(id))
            {
                LootPool.Builder poolBuilder= LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.2f))
                        .with(ItemEntry.builder(ItemRegistrator.NETHER_FLASK_SHARD))
                        .with(ItemEntry.builder(ItemRegistrator.BONE_DUST))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,1)).build());

                tableBuilder.pool(poolBuilder.build());
            }

            if (JUNGLE_TEMPLE.equals(id))
            {
                LootPool.Builder poolBuilder= LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.15f))
                        .with(ItemEntry.builder(ItemRegistrator.NETHER_FLASK_SHARD))
                        .with(ItemEntry.builder(ItemRegistrator.BONE_DUST))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,1)).build());

                tableBuilder.pool(poolBuilder.build());
            }

            if (SIMPLE_DUNGEON.equals(id))
            {
                LootPool.Builder poolBuilder= LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.15f))
                        .with(ItemEntry.builder(ItemRegistrator.NETHER_FLASK_SHARD))
                        .with(ItemEntry.builder(ItemRegistrator.BONE_DUST))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,2)).build());

                tableBuilder.pool(poolBuilder.build());
            }






        }));




    }

}
