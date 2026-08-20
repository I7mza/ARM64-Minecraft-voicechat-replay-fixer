package com.zalith.fix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(targets = "de.maxhenkel.replayvoicechat.rendering.VoiceChatAudioRenderer")
public class AudioEncoderMixin {

    @ModifyVariable(
        method = "renderAudio",
        at = @At("STORE"),
        ordinal = 0,
        remap = false
    )
    private String replaceMp3WithAac(String command) {
        if (command != null && (command.contains("libmp3lame") || command.contains("mp3"))) {
            // Replaces desktop MP3 library with Android-supported AAC codec
            return command.replace("libmp3lame", "aac")
                          .replace("-c:a mp3", "-c:a aac")
                          .replace("-q:a 0", "-b:a 192k");
        }
        return command;
    }
}
