package com.example.beatbox;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class SoundViewModel extends BaseObservable {

    private Sound sound;
    private BeatBox beatBox;

    public SoundViewModel(BeatBox beatBox) {
        this.beatBox = beatBox;
    }

    public Sound getSound() {
        return sound;
    }

    @Bindable
    public String getTitle() {
        return sound.getName();
    }

    public void setSound(Sound sound) {
        this.sound = sound;
        notifyChange();
    }

    public void onButtonClicked() {
        beatBox.play(sound);
    }
}
