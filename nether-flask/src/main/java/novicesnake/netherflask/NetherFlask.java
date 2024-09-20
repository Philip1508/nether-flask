package novicesnake.netherflask;

import net.fabricmc.api.ModInitializer;

import novicesnake.netherflask.blocks.BlocksRegistrator;
import novicesnake.netherflask.config.ConfigLoader;
import novicesnake.netherflask.items.ItemRegistrator;
import novicesnake.netherflask.loot.LootTableModifiers;
import novicesnake.netherflask.statuseffect.StatusEffectRegistrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetherFlask implements ModInitializer {
	public static final String MOD_ID = "nether-flask";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Loading Resources");
		//ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener());
		ConfigLoader.initializeConfig();



		LOGGER.info("Initializing Blocks and BlockEntity's");
		BlocksRegistrator.initializeAndRegister();

		LOGGER.info("Items and creative group");
		ItemRegistrator.initializeAndRegister();


		LOGGER.info("Initializing Status Effect");
		StatusEffectRegistrator.initializeAndRegister();

		LOGGER.info("Injecting into Loot Tables");
		LootTableModifiers.initializeAndRegister();




	}
}