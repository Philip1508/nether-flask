package novicesnake.netherflask.items;

import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

/***
 * This Interface is used to override the Item Count Rendering so that it displays (any) wanted number in the
 * Hotbar instead! (I.e. Remaining Uses of the Flask!)
 *
 *
 */
public interface DurabilityNumberItemstack {

      String NBT_MAINKEY = "SOFTSTACK_MAINKEY";
      String NBT_USES = "SOFTSTACK_USES";
      String NBT_SOFTMAX = "SOFTSTACK_SOFTMAX";
      String NBT_HARDMAX = "SOFTSTACK_HARDMAX";


    /***
     *  This Method decrements uses of an ItemStack
     * @param stack - ItemStack of which the uses need to be decremented.
     */
    default void decrementUses(ItemStack stack)
    {
        NbtCompound nbtMain = stack.getOrCreateSubNbt(NBT_MAINKEY);

        int uses = nbtMain.getInt(NBT_USES);

        if (uses-1 < 0)
        {
            return;
        }

        nbtMain.putInt(NBT_USES, uses-1);

    }

    /***
     *  This Method increments uses of an ItemStack
     * @param stack - ItemStack of which the uses need to be incremented.
     */
    public default boolean incrementUses(ItemStack stack)
    {
        NbtCompound nbtMain = stack.getOrCreateSubNbt(NBT_MAINKEY);
        int uses = nbtMain.getInt(NBT_USES);

        if (uses+1 > nbtMain.getInt(NBT_SOFTMAX))
        {
            return false;
        }

        nbtMain.putInt(NBT_USES, uses+1);
        return true;

    }



    /***
     *  This Method gets the uses of a given ItemStack
     * @param stack - ItemStack of which the uses determined.
     * @return integer - Uses of Item
     */
    public default int getUses(ItemStack stack)
    {
        NbtCompound nbtMain = stack.getOrCreateSubNbt(NBT_MAINKEY);

        int uses = nbtMain.getInt(NBT_USES);
        return uses;
    }



    public default int getSoftMax(ItemStack stack)
    {
        NbtCompound nbtMain = stack.getOrCreateSubNbt(NBT_MAINKEY);

        int uses = nbtMain.getInt(NBT_SOFTMAX);
        return uses;
    }




    /***
     *  This Method sets the uses of a given ItemStackk
     * @param stack - ItemStack of which the uses determined.
     * @param uses - Uses to be Set
     */
    public default void setUses(ItemStack stack, int uses)
    {
        NbtCompound nbtMain = stack.getOrCreateSubNbt(NBT_MAINKEY);

        nbtMain.putInt(NBT_USES, uses);
    }



    /***
     *  This Method sets the virtual maximum uses of a given ItemStack
     * @param stack - ItemStack of which the virtual Maximum needs to be determined.
     * @param  uses - Virtual Maximum Uses to Set
     */
    public default void setNbtSoftmax(ItemStack stack, int uses)
    {
        NbtCompound nbtMain = stack.getOrCreateSubNbt(NBT_MAINKEY);

        nbtMain.putInt(NBT_SOFTMAX, uses);
    }

    /***
     *  This Method sets the hard maximum uses of a given ItemStack
     * @param stack - ItemStack of which the virtual Maximum needs to be determined.
     * @param  maximum - hard Maximum Uses to Set
     */
    public default void setAbsoluteMaximum(ItemStack stack, int maximum)
    {
        NbtCompound nbtMain = stack.getOrCreateSubNbt(NBT_MAINKEY);
        nbtMain.putInt(NBT_HARDMAX, maximum);

    }


    /***
     *  This Method gets the hard maximum uses of a given ItemStack
     * @param stack - ItemStack of which the virtual Maximum needs to be determined.
     * @return   maximum - hard Maximum Uses
     */
    public default int getAbsoluteMaximum(ItemStack stack)
    {
        NbtCompound nbtMain = stack.getOrCreateSubNbt(NBT_MAINKEY);
        return nbtMain.getInt(NBT_HARDMAX);

    }


}
