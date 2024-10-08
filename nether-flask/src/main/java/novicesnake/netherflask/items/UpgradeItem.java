package novicesnake.netherflask.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * This Class extends the Standard Item in order to allow Tooltip Manipulation of Upgrade Items.
 */
public class UpgradeItem extends Item {

    public UpgradeItem(Settings settings) {
        super(settings);
    }


    /**
     * This Method defines the Tooltip Hint for each upgrade item.
     */
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        if (stack.isOf(ItemRegistrator.PURGING_STONE))
        {
            tooltip.add(Text.translatable("item.nether-flask.upgrade.purging-stone"));
        }

        if (stack.isOf(ItemRegistrator.NETHER_FLASK_SHARD))
        {
            tooltip.add(Text.translatable("item.nether-flask.upgrade.flask-shard"));
        }

        if (stack.isOf(ItemRegistrator.BONE_DUST))
        {
            tooltip.add(Text.translatable("item.nether-flask.upgrade.bone-dust"));
        }


        tooltip.add(Text.translatable("item.nether-flask.upgrade.campfirehint"));



    }

    }
