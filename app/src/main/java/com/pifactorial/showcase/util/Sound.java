package com.pifactorial.showcase.util;

import android.media.AudioManager;
import android.content.Context;

public class Sound {

    public static final int getRingtoneVolume(Context context) {
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audio.getStreamVolume(AudioManager.STREAM_RING);
        return currentVolume;
    }
}
