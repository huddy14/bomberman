package com.bomberman.game.Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.bomberman.game.AssetsPaths;
import com.bomberman.game.Bomberman;
import com.bomberman.game.BombermanPreferances;
import com.bomberman.game.Interfaces.ISoundEffects;

/**
 * Created by huddy on 6/24/16.
 */
public class AudioManager implements ISoundEffects {
    //TODO: dodac komentarze gdzie trzeba; przycisk mute w main menu
    private static AudioManager ourInstance;

    private final Sound bonus = Gdx.audio.newSound(Gdx.files.internal(AssetsPaths.AUDIO_BONUS));
    private final Sound explosion = Gdx.audio.newSound(Gdx.files.internal(AssetsPaths.AUDIO_EXPLOSION));
    private final Sound death = Gdx.audio.newSound(Gdx.files.internal(AssetsPaths.AUDIO_GAME_OVER));
    private final Sound victory = Gdx.audio.newSound(Gdx.files.internal(AssetsPaths.AUDIO_BONUS));
    private final Music soundtrack = Gdx.audio.newMusic(Gdx.files.internal(AssetsPaths.AUDIO_SOUNDTRACK));
    private boolean soundEnabled;
    private float volume;



    public static AudioManager getInstance() {
        if(ourInstance==null)
            ourInstance=new AudioManager();
        return ourInstance;
    }

    private AudioManager() {
        soundEnabled = BombermanPreferances.getInstance().getSound();
        setVolume();
    }

    @Override
    public void onBonusTake() {
        bonus.play(volume);
    }

    @Override
    public void onPlayerDeath() {
        death.play(volume);
    }

    @Override
    public void onPlayerVicotry() {
        victory.play(volume);
    }

    @Override
    public void onBombEplosion() {
        explosion.play(volume);
    }

    public void playSoundtrack()
    {
        if(soundEnabled) {
            soundtrack.setLooping(true);
            soundtrack.play();
        }
    }

    public void stopSoundtrack() {
        soundtrack.stop();
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public void setSoundEnabled()
    {
        if(soundEnabled)
            soundEnabled = false;
        else soundEnabled = true;
        setVolume();
        BombermanPreferances.getInstance().setSound(soundEnabled);
    }

    private void setVolume()
    {
        if(this.soundEnabled)volume = 1f;
        else volume = 0f;
    }
}
