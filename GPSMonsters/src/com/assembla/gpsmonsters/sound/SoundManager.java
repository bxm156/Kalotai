/***************************************
 * SoundManager
 * 
 * Author: Matt DelBrocco & Bryan Marty
 * 
 * Description: This manager will manage all the sounds in the game.
 * 
 * 
***************************************/
package com.assembla.gpsmonsters.sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import com.assembla.gpsmonsters.R;
import com.assembla.gpsmonsters.engine.GameManager;


public class SoundManager {
	//private AudioManager _audioManager;
	//Media player will be used for long audioclips and background music
	private MediaPlayer _mediaPlayer;
	//SoundPool will be used for short sound clips.
	private SoundPool _sounds;
	private Context _context;
	//Max number of sounds that can be played at any given instance
	private static final int MAX_STREAMS = 5;
	
	public SoundManager(Context context)
	{
	    _context = context;
		//_audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		_sounds = new SoundPool(MAX_STREAMS,AudioManager.STREAM_MUSIC,0);
		_mediaPlayer = MediaPlayer.create(context, R.raw.battle_theme_ogg); 
		
		// set data source & prepare, complete in line above?
		loadSounds(context);
		// release() when done with mediaPlayer
	}
	private void loadSounds(Context context)
	{
	    // If you change this sound list, MAKE SURE THE VALUES IN
	    // THE DATABASE ARE CONSISTANT! -mvd
	    SoundList.WIN = _sounds.load(context, R.raw.levelup, 1);					// 1
	    SoundList.AQUA_BEAR_WITH_NAME = _sounds.load(context, R.raw.aqua_bear_argh_with_name, 1);	// 2
	    SoundList.AQUA_BEAR_ROAR = _sounds.load(context, R.raw.aqua_bear_roar, 1);			// 3
	    SoundList.ARMOR_TURTLE = _sounds.load(context, R.raw.armor_turtle, 1);			// etc.
	    SoundList.ARMOR_TURTLE2 = _sounds.load(context, R.raw.armor_turtle2, 1);
	    SoundList.BIRD_RANDOM = _sounds.load(context, R.raw.bird_random, 1);
	    SoundList.BIRD_RANDOM2 = _sounds.load(context, R.raw.bird_random2, 1);
	    SoundList.BIRD_RANDOM3 = _sounds.load(context, R.raw.bird_random3, 1);
	    SoundList.BIRD_RANDOM4 = _sounds.load(context, R.raw.bird_random4, 1);
	    SoundList.BUBBLY_NOISE = _sounds.load(context, R.raw.bubbly_noise, 1);
	    SoundList.DEER_HOWL = _sounds.load(context, R.raw.deer_howl, 1);
	    SoundList.ELECTRIC_ZOOM = _sounds.load(context, R.raw.electric_zoom, 1);
	    SoundList.FISH_THING_BLOOP = _sounds.load(context, R.raw.fish_thing_bloop, 1);
	    SoundList.FISH_THING_BLOOP2 = _sounds.load(context, R.raw.fish_thing_bloop2, 1);
	    SoundList.GRUNT_NOISE = _sounds.load(context, R.raw.grunt_noise, 1);
	    SoundList.INSECT_NOISE = _sounds.load(context, R.raw.insect_noise, 1);
	    SoundList.KAZOOIE_NOISE = _sounds.load(context, R.raw.kazooie_noise, 1);
	    SoundList.SHADOW_BEAK = _sounds.load(context, R.raw.shadow_beak, 1);
	    SoundList.SHADOW_BEAK2 = _sounds.load(context, R.raw.shadow_beak2, 1);
	    SoundList.SHADOW_BEAK3 = _sounds.load(context, R.raw.shadow_beak3, 1);
	    SoundList.SHADOW_BEAK4DEATH = _sounds.load(context, R.raw.shadow_beak4death, 1);
	    SoundList.SIX_LEGS_BLA = _sounds.load(context, R.raw.six_legs_blablabla, 1);
	    SoundList.SIX_LEGS_BLONG = _sounds.load(context, R.raw.six_legs_blonggg, 1);
	    SoundList.SIX_LEGS_BLONG2 = _sounds.load(context, R.raw.six_legs_blonggg2, 1); 
	    SoundList.SIX_LEGS_DEATH = _sounds.load(context, R.raw.six_legs_death, 1);
	    SoundList.WOLF_DISTRESS_HOWL = _sounds.load(context, R.raw.wolf_distress_howl, 1);
	    SoundList.WOLF_GRR = _sounds.load(context, R.raw.wolf_grr, 1);
	    SoundList.WOLF_HOWL = _sounds.load(context, R.raw.wolf_howl, 1);
	    SoundList.WOLF_HOWL_DEATH = _sounds.load(context, R.raw.wolf_howl_death, 1);
	    SoundList.WOLF_HOWL_DISTRESS = _sounds.load(context, R.raw.wolf_howl_distress2, 1);
	    SoundList.ZOOM = _sounds.load(context, R.raw.zoom, 1);
	    SoundList.ZWIP = _sounds.load(context, R.raw.zwip, 1);
	}
	public void setSong(int songChoice)
	{
	    if (songChoice == 0)
		_mediaPlayer = MediaPlayer.create(_context, R.raw.battle_theme_ogg); 
	    // add more if more songs
	}
	public int playSound(int sound)
	{
		return _sounds.play(sound, 1, 1, 1, 0, 1);
	}
	public int playSong()
	{
	    try{
		_mediaPlayer.setLooping(true);
		_mediaPlayer.start();
	    }	
	    catch(Exception e)
	    {
		return -1;
	    }
	    return 0;
	    //return _mediaPlayer.
	}
	public int stopSong()
	{
	    try{
		_mediaPlayer.stop();
	    }
	    catch(Exception e)
	    {
		return -1;
	    }
	    return 0;
	}
	public void setVolume(int stream, float leftVolume, float rightVolume)
	{
		_sounds.setVolume(stream, leftVolume, rightVolume);
	}
	public void release()
	{
		_sounds.release();
		_sounds = null;
		_mediaPlayer.stop();
		_mediaPlayer.release();
		_mediaPlayer = null;
	}
}
