package fr.xalnox.fixmyskin.mixin;

import net.minecraft.client.render.DownloadedSkinParser;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DownloadedSkinParser.class)
public abstract class DownloadSkinParserMixin {
    @Redirect(method = "parseSkin", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/DownloadedSkinParser;height:I", opcode = Opcodes.PUTFIELD))
    public void setParseSkin(DownloadedSkinParser parser, int value) {
        parser.height = 64;
    }
}
