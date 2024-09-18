package novicesnake.netherflask.statuseffect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import novicesnake.netherflask.NetherFlask;

/**
 * This Class Registers the Status Effects and binds a reference for each one onto a Class Field.
 * */
public class StatusEffectRegistrator {

    public static StatusEffect FLASK_RECHARGER;


    /**
     * This Method initializes and registers all Status Effects and binds them to a field.
     */
    public static void initializeAndRegister()
    {
        FLASK_RECHARGER = Registry.register(Registries.STATUS_EFFECT,
                new Identifier(NetherFlask.MOD_ID, "flask-recharger"),
                new NetherFlaskRechargingStatusEffect(StatusEffectCategory.BENEFICIAL, 0));
    }

}
