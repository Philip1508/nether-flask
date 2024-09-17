package novicesnake.netherflask;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.CampfireBlockEntityRenderer;
import net.minecraft.recipe.BrewingRecipeRegistry;
import novicesnake.netherflask.blocks.BlocksRegistrator;
import novicesnake.netherflask.items.ItemRegistrator;
import novicesnake.netherflask.render.StardustCampfireBlockEntityRender;

public class NetherFlaskClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {



		BlockEntityRendererFactories.register(BlocksRegistrator.STARDUST_CAMPFIRE_ENTITY, StardustCampfireBlockEntityRender::new);
		BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistrator.STARDUST_CAMPFIRE, RenderLayer.getCutout());


		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}