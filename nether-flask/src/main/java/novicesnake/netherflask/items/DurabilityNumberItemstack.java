package novicesnake.netherflask.items;

import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public interface DurabilityNumberItemstack {

    public  String NBT_MAINKEY = "SOFTSTACK_MAINKEY";

    public  String NBT_USES = "SOFTSTACK_USES";
    public  String NBT_SOFTMAX = "SOFTSTACK_SOFTMAX";
    public  String NBT_HARDMAX = "SOFTSTACK_HARDMAX";




    public default void decrementUses(ItemStack stack)
    {
        NbtCompound nbtMain = stack.getOrCreateSubNbt(NBT_MAINKEY);

        int uses = nbtMain.getInt(NBT_USES);

        if (uses-1 < 0)
        {
            return;
        }

        nbtMain.putInt(NBT_USES, uses-1);

    }

    public default void incrementUses(ItemStack stack)
    {
        NbtCompound nbtMain = stack.getOrCreateSubNbt(NBT_MAINKEY);
        int uses = nbtMain.getInt(NBT_USES);

        if (uses+1 > nbtMain.getInt(NBT_SOFTMAX))
        {
            return;
        }

        nbtMain.putInt(NBT_USES, uses+1);

    }



    public default int getUses(ItemStack stack)
    {
        NbtCompound nbtMain = stack.getOrCreateSubNbt(NBT_MAINKEY);

        int uses = nbtMain.getInt(NBT_USES);
        return uses;
    }



    public default void setUses(ItemStack stack, int uses)
    {
        NbtCompound nbtMain = stack.getOrCreateSubNbt(NBT_MAINKEY);

        nbtMain.putInt(NBT_USES, uses);
    }


    public default void setNbtSoftmax(ItemStack stack, int uses)
    {
        NbtCompound nbtMain = stack.getOrCreateSubNbt(NBT_MAINKEY);

        nbtMain.putInt(NBT_SOFTMAX, uses);

    }


}
