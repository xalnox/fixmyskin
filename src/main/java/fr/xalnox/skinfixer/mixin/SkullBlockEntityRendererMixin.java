package fr.xalnox.skinfixer.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import fr.xalnox.skinfixer.utils.SkinAPIUtils;
import net.minecraft.client.render.block.entity.SkullBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(SkullBlockEntityRenderer.class)
public class SkullBlockEntityRendererMixin {

    @Inject(method = "method_4363", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/TextureManager;method_4310(Ljava/lang/String;)Z"))
    public void setSkin(float g, float h, float i, int j, float k, int string, String par7, CallbackInfo ci, @Local LocalRef<String> var9) {
        try {
            String[] newValue = var9.get().split("/|\\.png");
            var9.set(SkinAPIUtils.getSkinCapeURL(
                            SkinAPIUtils.getUUIDFromUsername(
                                    newValue[newValue.length - 1]))
                    .get(0));
        } catch (IOException e) {
        }
    }
}
