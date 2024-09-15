package novicesnake.netherflask.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import novicesnake.netherflask.NetherFlask;

public class ItemRegistrator {



    public static Item NETHER_FLASK;


    public static void initializeAndRegister()
    {


        NETHER_FLASK = Registry.register(Registries.ITEM, new Identifier(NetherFlask.MOD_ID, "nether-flask"),
                new NetherFlaskItem(new FabricItemSettings().maxCount(1)  )
                );

        Registry.register(Registries.ITEM, new Identifier(NetherFlask.MOD_ID, "tester"), new DebugItem(new FabricItemSettings().maxCount(1)));

    }

}
