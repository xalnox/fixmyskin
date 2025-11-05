package fr.xalnox.skinfixer.mixin;

import net.minecraft.client.render.entity.model.SkullEntityModel;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SkullEntityModel.class)
public class SkullEntityModelMixin {
    @Redirect(method = "<init>(IIII)V", at= @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/model/SkullEntityModel;textureHeight:I",opcode = Opcodes.PUTFIELD))
    public void setTextures(SkullEntityModel instance, int value) {
        instance.textureHeight = 64;
    }
}
