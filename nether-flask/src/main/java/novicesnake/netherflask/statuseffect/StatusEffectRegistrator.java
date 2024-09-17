package novicesnake.netherflask.statuseffect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import novicesnake.netherflask.NetherFlask;

public class StatusEffectRegistrator {

    public static StatusEffect FLASK_RECHARGER;



    public static void initializeAndRegister()
    {
        FLASK_RECHARGER = Registry.register(Registries.STATUS_EFFECT,
                new Identifier(NetherFlask.MOD_ID, "flask-recharger"),
                new NetherFlaskRechargingStatusEffect(StatusEffectCategory.BENEFICIAL, 0));
    }

}
