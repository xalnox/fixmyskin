package fr.xalnox.fixmyskin.mixin;

import net.minecraft.client.TextureManager;
import net.minecraft.client.texture.TexturePackManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Mixin(TextureManager.class)
abstract class TextureManagerMixin {

    @Shadow
    private TexturePackManager packManager;
    @Shadow
    private HashMap<String, int[]> field_1974;

    @Shadow
    private IntBuffer field_1976;
    @Shadow
    private HashMap textureCache;

    @Shadow
    public abstract void method_1418(BufferedImage bufferedImage, int i);
    @Inject(method = "getTextureFromPath", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/ITexturePack;openStream(Ljava/lang/String;)Ljava/io/InputStream;"), cancellable = true)
    public void onOpenImageStream(String par1, CallbackInfoReturnable<Integer> cir) {

        if (par1.equals("/mob/char.png") || par1.matches("^/armor/[a-z]+_\\d+(?:_b)?\\.png$")) {
            // Resize the default skin.
            try {
                // Open the old skin
                InputStream stream = this.packManager.getCurrentTexturePack().openStream(par1);
                BufferedImage oldSkin = ImageIO.read(stream);
                stream.close();
                // Put it into 64x64 image
                BufferedImage newSkin = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
                Graphics2D graphics2D = newSkin.createGraphics();
                graphics2D.drawImage(oldSkin, 0, 0, null);
                graphics2D.dispose();
                // Follow the logic.

                int val = this.field_1976.get(0);
                this.method_1418(newSkin, val);
                this.textureCache.put(par1, val);
                cir.setReturnValue(val);
                cir.cancel();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
