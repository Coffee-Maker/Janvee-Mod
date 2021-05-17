package me.coffeemaker.janvee.mixin;

import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider {
    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) { super(screenHandler, playerInventory, text); }

    @Shadow @Final private static final Identifier RECIPE_BUTTON_TEXTURE = new Identifier("textures/gui/recipe_button.png");

    @Shadow @Final private final RecipeBookWidget recipeBook = new RecipeBookWidget();
    @Shadow private boolean narrow;
    @Shadow private boolean mouseDown;

    @Inject(
            method = "init()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/InventoryScreen;addButton(Lnet/minecraft/client/gui/widget/AbstractButtonWidget;)Lnet/minecraft/client/gui/widget/AbstractButtonWidget;")
    )
    private void addStatsButton(CallbackInfo ci) {
        System.out.println("Opened inventory!! And this is updated");
        this.addButton(new TexturedButtonWidget(this.x + 129, this.height / 2 - 22, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, (buttonWidget) -> {
            this.recipeBook.reset(this.narrow);
            this.recipeBook.toggleOpen();
            this.x = this.recipeBook.findLeftEdge(this.narrow, this.width, this.backgroundWidth);
            ((TexturedButtonWidget) buttonWidget).setPos(this.x + 129, this.height / 2 - 22);
            this.mouseDown = true;
        }));
    }
}
