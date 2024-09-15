package novicesnake.netherflask;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.minecraft.recipe.BrewingRecipeRegistry;
import novicesnake.netherflask.items.ItemRegistrator;

public class NetherFlaskClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {


		ItemRegistrator.initializeAndRegister();







		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}