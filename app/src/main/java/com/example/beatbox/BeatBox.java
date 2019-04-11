package com.example.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeatBox {

    private static final String TAG = "BeatBox";
    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static final int MAX_SOUND = 5;

    private AssetManager assets;
    private List<Sound> sounds = new ArrayList<>();
    private SoundPool soundPool;

    public BeatBox(Context context) {
        this.assets = context.getAssets();
        soundPool = new SoundPool(MAX_SOUND, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }

    private void loadSounds() {
        String[] soundNames;
        try{
            soundNames = this.assets.list(SOUNDS_FOLDER);
            Log.d(TAG, "LoadSounds: " + soundNames.length);
        } catch (IOException ioe) {
            Log.d(TAG, "Could not list assets", ioe);
            return;
        }

        for (String fileName : soundNames) {
            try{
                String assetPath = SOUNDS_FOLDER + "/" + fileName;
                Sound sound = new Sound(assetPath);
                load(sound);
                sounds.add(sound);
            } catch (IOException ioe) {
                Log.e(TAG, "loadSounds: " + fileName, ioe);
            }


        }
    }

    public void play (Sound sound) {
        Integer soundId = sound.getSoundId();
        if (soundId == null) {
            return;
        }
        soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    private void load(Sound sound) throws IOException {
        AssetFileDescriptor afd = assets.openFd(sound.getAssetPath());
        int soundId = soundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }


    public List<Sound> getSounds() {
        return sounds;
    }

    public void releace() {
        soundPool.release();
    }

}
