package novicesnake.netherflask.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import novicesnake.netherflask.NetherFlask;
import novicesnake.netherflask.blocks.BlocksRegistrator;


/**
 * This Class initializes and registers all items.
 */
public class ItemRegistrator {


    public static ItemGroup NETHER_FLASK_GROUP;



    public static Item NETHER_STARDUST;
    public static Item STARDUST_CAMPFIRE;
    public static Item RAW_NETHER_FLASK;
    public static Item NETHER_FLASK;
    public static Item NETHER_FLASK_SHARD;
    public static Item BONE_DUST;
    public static Item PURGING_STONE;


    /**
     * This method provides the initialization and registration process.
     */
    public static void initializeAndRegister()
    {

        RAW_NETHER_FLASK = Registry.register(Registries.ITEM, new Identifier(NetherFlask.MOD_ID, "raw-nether-flask"),
                new Item(new FabricItemSettings().maxCount(1)));


        STARDUST_CAMPFIRE = Registry.register(Registries.ITEM, new Identifier(NetherFlask.MOD_ID, "stardust-campfire-item"),
                new ToolTipBlockItem(BlocksRegistrator.STARDUST_CAMPFIRE, new FabricItemSettings().maxCount(1)));

        NETHER_STARDUST = Registry.register(Registries.ITEM, new Identifier(NetherFlask.MOD_ID, "nether-stardust"),
                new Item(new FabricItemSettings()));

        NETHER_FLASK = Registry.register(Registries.ITEM, new Identifier(NetherFlask.MOD_ID, "nether-flask"),
                new NetherFlaskItem(new FabricItemSettings().maxCount(1).fireproof()  )
                );

        NETHER_FLASK_SHARD = Registry.register(Registries.ITEM, new Identifier(NetherFlask.MOD_ID, "nether-flask-shard"),
                new UpgradeItem(new FabricItemSettings().maxCount(12)));

        BONE_DUST = Registry.register(Registries.ITEM, new Identifier(NetherFlask.MOD_ID, "sublime-bone-dust"),
                new UpgradeItem(new FabricItemSettings().maxCount(5)));

        PURGING_STONE = Registry.register(Registries.ITEM, new Identifier(NetherFlask.MOD_ID, "purging-stone"),
                new UpgradeItem(new FabricItemSettings().maxCount(1)));



        registerCreativeGroup();
        Registry.register(Registries.ITEM_GROUP, new Identifier(NetherFlask.MOD_ID, "nether-flask-creativegrup"), NETHER_FLASK_GROUP);

    }

    private static void registerCreativeGroup()
    {
        NETHER_FLASK_GROUP = FabricItemGroup.builder()
                .displayName(Text.translatable("creative.group.nether-flask"))
                .icon(() -> new ItemStack(NETHER_FLASK))
                .entries((context, entries) -> {
                    entries.add(RAW_NETHER_FLASK);
                    entries.add(NETHER_FLASK);
                    entries.add(NETHER_FLASK_SHARD);
                    entries.add(BONE_DUST);
                    entries.add(PURGING_STONE);

                    entries.add(NETHER_STARDUST);

                })
                .build();

    }

}
