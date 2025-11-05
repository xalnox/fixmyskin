package fr.xalnox.skinfixer.mixin;

import fr.xalnox.skinfixer.utils.SkinAPIUtils;
import net.minecraft.entity.player.OtherClientPlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.util.List;

@Mixin(OtherClientPlayerEntity.class)
public class OtherClientPlayerEntityMixin {

    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/OtherClientPlayerEntity;skinUrl:Ljava/lang/String;", opcode = Opcodes.PUTFIELD))
    public void setSkin(OtherClientPlayerEntity instance, String value) {
        try {
            String[] newValue = value.split("/|\\.png");
            instance.skinUrl = SkinAPIUtils.getSkinCapeURL(
                            SkinAPIUtils.getUUIDFromUsername(
                                    newValue[newValue.length - 1]))
                    .get(0);
        } catch (IOException e) {
            instance.skinUrl = value;
        }
    }

    @Redirect(method = "method_2510", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/OtherClientPlayerEntity;field_4008:Ljava/lang/String;", opcode = Opcodes.PUTFIELD))
    public void getCape(OtherClientPlayerEntity instance, String value) {
        try {
            String[] newValue = value.split("/|\\.png");
            List<String> skinCapeURL = SkinAPIUtils.getSkinCapeURL(
                    SkinAPIUtils.getUUIDFromUsername(
                            newValue[newValue.length - 1]));
            if (skinCapeURL.size() == 2) {
                instance.field_4008 = skinCapeURL.get(1);
            } else {
                instance.field_4008 = value;
            }
        } catch (IOException e) {
            instance.field_4008 = value;
        }
    }
}
