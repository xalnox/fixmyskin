package fr.xalnox.skinfixer.mixin;

import net.minecraft.client.render.DownloadedSkinParser;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;

@Mixin(DownloadedSkinParser.class)
public abstract class DownloadSkinParserMixin {


    @Shadow
    protected abstract void setOpaque(int uMin, int vMin, int uMax, int vMax);

    @Redirect(method = "parseSkin",at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/DownloadedSkinParser;height:I", opcode = Opcodes.PUTFIELD))
    public void setParseSkin(DownloadedSkinParser parser, int value) {
        parser.height = 64;
    }

    @Redirect(
            method = "parseSkin",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/DownloadedSkinParser;setTransparent(IIII)V"
            )
    )
    private void skipTransparent(DownloadedSkinParser instance, int u1, int v1, int u2, int v2) {
        //NUH UH
    }
}
