package com.bomberman.game.Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.bomberman.game.AssetsPaths;
import com.bomberman.game.Interfaces.ISoundEffects;

/**
 * Created by huddy on 6/24/16.
 */
public class AudioManager implements ISoundEffects {
    private static AudioManager ourInstance;

    private final Sound bonus = Gdx.audio.newSound(Gdx.files.internal(AssetsPaths.AUDIO_BONUS));
    private final Sound explosion = Gdx.audio.newSound(Gdx.files.internal(AssetsPaths.AUDIO_EXPLOSION));
    private final Sound death = Gdx.audio.newSound(Gdx.files.internal(AssetsPaths.AUDIO_GAME_OVER));
    private final Sound victory = Gdx.audio.newSound(Gdx.files.internal(AssetsPaths.AUDIO_BONUS));
    private final Music soundtrack = Gdx.audio.newMusic(Gdx.files.internal(AssetsPaths.AUDIO_SOUNDTRACK));



    public static AudioManager getInstance() {
        if(ourInstance==null)
            ourInstance=new AudioManager();
        return ourInstance;
    }

    private AudioManager() {
    }

    @Override
    public void onBonusTake() {
        bonus.play();
    }

    @Override
    public void onPlayerDeath() {
        death.play();
    }

    @Override
    public void onPlayerVicotry() {
        victory.play();
    }

    @Override
    public void onBombEplosion() {
        explosion.play();
    }

    public void playSoundtrack()
    {
        soundtrack.setLooping(true);
        soundtrack.play();
    }

    public void stopSoundtrack() {
        soundtrack.stop();
    }
}
