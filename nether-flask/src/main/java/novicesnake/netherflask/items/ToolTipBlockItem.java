package novicesnake.netherflask.items;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * This Class extends the Standard BlockItem in order to allow Tooltip Manipulation.
 */
public class ToolTipBlockItem extends BlockItem {
    public ToolTipBlockItem(Block block, Settings settings) {
        super(block, settings);
    }


    /**
     * This Method defines the Hint displayed in the Stardust Campfire.
     */
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("item.nether-flask.stardust-campfire-block-hint"));
    }

}
