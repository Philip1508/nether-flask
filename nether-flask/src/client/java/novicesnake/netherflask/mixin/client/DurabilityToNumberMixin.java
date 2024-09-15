package novicesnake.netherflask.mixin.client;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import novicesnake.netherflask.NetherFlask;
import novicesnake.netherflask.items.DurabilityNumberItemstack;
import novicesnake.netherflask.items.NetherFlaskItem;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawContext.class)
public abstract class DurabilityToNumberMixin {


    @Shadow
    public void fill(RenderLayer layer, int x1, int y1, int x2, int y2, int color) {
    }


    @Shadow
    public int drawText(TextRenderer textRenderer, @Nullable String text, int x, int y, int color, boolean shadow) {
    return 0;
    }


    @Shadow @Final private MatrixStack matrices;

    @Shadow @Final private MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", cancellable = true)
    public void drawItemInSlot(TextRenderer textRenderer, ItemStack stack, int x, int y, @Nullable String countOverride, CallbackInfo ci)
    {

        if (stack.getItem() instanceof DurabilityNumberItemstack softwrap)
        {







            this.matrices.push();
            if (stack.getCount() != 1 || countOverride != null || true) {

                int uses =  softwrap.getUses(stack);

                String string = countOverride == null ? String.valueOf(uses) : countOverride;
                this.matrices.translate(0.0F, 0.0F, 200.0F);

                int color = 16777215;

                if (uses == 0)
                {

                    color = 255 << 16;
                }

                this.drawText(textRenderer, string, x + 19 - 2 - textRenderer.getWidth(string), y + 6 + 3, color, true);
            }

            if (stack.isItemBarVisible()) {
                int i = stack.getItemBarStep();
                int j = stack.getItemBarColor();
                int k = x + 2;
                int l = y + 13;
                this.fill(RenderLayer.getGuiOverlay(), k, l, k + 13, l + 2, -16777216);
                this.fill(RenderLayer.getGuiOverlay(), k, l, k + i, l + 1, j | 0xFF000000);
            }

            ClientPlayerEntity clientPlayerEntity = this.client.player;
            float f = clientPlayerEntity == null ? 0.0F : clientPlayerEntity.getItemCooldownManager().getCooldownProgress(stack.getItem(), this.client.getTickDelta());
            if (f > 0.0F) {
                int k = y + MathHelper.floor(16.0F * (1.0F - f));
                int l = k + MathHelper.ceil(16.0F * f);
                this.fill(RenderLayer.getGuiOverlay(), x, k, x + 16, l, Integer.MAX_VALUE);
            }

            this.matrices.pop();


            ci.cancel();
        }


    }


}
