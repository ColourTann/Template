package com.tann.jamgame.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;

import java.util.ArrayList;
import java.util.HashMap;

public class Sounds {


	public static AssetManager am= new AssetManager();

    public static String[] pew;
    public static String[] pow_little;
    public static String[] explod_smol;
    public static String[] shot;
    public static String[] reverse;
    public static String[] boost;
    public static String[] explodeMedium;
    public static String[] pew_smol;
    public static String explodePlayer;
    public static String pow_huge;
    public static String explod_bomber;
    public static String sad_long_pow;
    public static String molotov_launch;
    public static String speed;
    public static String[] molotov_explode;
    public static void setup(){
		//sfx//

        pew = makeSounds("pew", 1);
        pow_little = makeSounds("pow_little",2);
        shot = makeSounds("shot", 5);
        explod_smol = makeSounds("explod_smol", 1);
        reverse = makeSounds("reverse", 1);
        boost = makeSounds("boost", 3);
        pew_smol = makeSounds("pew_smol", 1);
        explodeMedium=makeSounds("explod_medium", 4);
        explodePlayer=makeSound("sfx/explod_player.wav", Sound.class);
        speed=makeSound("sfx/speed.wav", Sound.class);
        pow_huge = makeSound("sfx/pow_huge.wav", Sound.class);
        explod_bomber = makeSound("sfx/explod_bomber.wav", Sound.class);
        sad_long_pow = makeSound("sfx/sad_long_pow.wav", Sound.class);
        molotov_launch = makeSound("sfx/molotov_launch.wav", Sound.class);
        molotov_explode = makeSounds("molotov_explode", 3);
		//stuff to attempt to load sounds properly//
		am.finishLoading();
		Array<Sound> sounds = new Array<Sound>();
		am.getAll(Sound.class, sounds);
		for(Sound s:sounds)s.play(0);
		Array<Music> musics = new Array<Music>();
		am.getAll(Music.class, musics);
		for(Music m:musics){
			m.play();
			m.setVolume(1);
			m.stop();
		}
	}
	
	public static <T> T get(String name, Class<T> type){
		return am.get(name, type);

	}

    private static String makeSound(String path, Class type){
        am.load(path, type);
        return path;
    }

    private static String[] makeSounds(String path, int amount){
        return makeSounds(path,amount,".wav");
    }

    private static String[] makeSounds(String path, int amount, String extension){
        String[] strings = new String[amount];
        for(int i=0;i<amount;i++){
            String s = "sfx/"+path+"_"+i+extension;
            makeSound(s, Sound.class);
            strings[i]=s;
        }
        return strings;
    }
	
	private static ArrayList<Fader> faders = new ArrayList<Fader>();
	
	public static void fade(Music m, float targetVolume, float duration){
		faders.add(new Fader(m, targetVolume, duration));
	}
	
	public static void tickFaders(float delta){
		for(int i=faders.size()-1;i>=0;i--){
			Fader f = faders.get(i);
			f.tick(delta);
			if(f.done)faders.remove(f);
		}
	}
	
	private static Music previousMusic;
	private static Music currentMusic;
	public static void playMusic(String path){
        Music m = Sounds.get(path, Music.class);
        stopMusic();
		previousMusic=currentMusic;
		currentMusic=m;
		currentMusic.play();
		currentMusic.setLooping(true);
		updateMusicVolume();
	}

	public static void stopMusic(){
	    if(currentMusic!=null)  currentMusic.stop();
    }

	public static void updateMusicVolume(){
		if(currentMusic!=null)currentMusic.setVolume(Slider.music.getValue());
	}
	
	static class Fader{
		float startVolume;
		float targetVolume;
		Music music;
		boolean done;
		float duration;
		float ticks;
		public Fader(Music m, float targetVolume, float duration) {
			this.startVolume=m.getVolume();
			this.targetVolume=targetVolume;
			this.music=m;
			this.duration=duration;
		}
		public void tick(float delta){
			ticks+=delta;
			if(ticks>duration){
				ticks=duration;
				done=true;
			}
			float ratio = ticks/duration;
			float newVolume =startVolume+(targetVolume-startVolume)*ratio;
			music.setVolume(newVolume);
		}
	}



	static HashMap<String, Sound> soundMap = new HashMap<String, Sound>();
    public static void playSound(String string, Vector2 spot) {
        playSound(string, 1, 1, spot);
    }

    static HashMap<String, Long> lastPlayed = new HashMap<>();
    static final Long diff = 60l;
	public static void playSound(String string, float volume, float pitch, Vector2 spot) {

        float pan = 1;

        if(spot!=null) {
            float dx = spot.x - SpaceScreen.get().map.defender.getX();
            float dy = spot.y - SpaceScreen.get().map.defender.getY();
            float dist = (float) (Math.sqrt(dx * dx + dy * dy));
            float maxDist = 3000;
            dist = Math.min(maxDist, dist);
            float volumeMult = (maxDist - dist) / maxDist;
            volume *= volumeMult;
            float maxPan = 1400;
            if(dx <-maxPan) dx = -maxPan;
            if(dx >maxPan) dx = maxPan;
            pan = dx/maxPan;
            pan += 1;
            pan /= 2f;
        }


	    Long time = System.currentTimeMillis();
	    Long lp = lastPlayed.get(string);
	    if(lp!=null && time-lp < diff) return;
		Sound s = soundMap.get(string);
		if(s==null){
			s=get(string, Sound.class);
			soundMap.put(string, s);
		}
        s.play(Slider.SFX.getValue()*2*volume, pitch, pan);
		lastPlayed.put(string, time);
	}
    public static void playSound(String[] string, Vector2 spot) {
        playSound(string, 1, 1, spot);
    }
	public static void playSound(String[] strings, float volume, float pitch, Vector2 spot){
        playSound(strings[((int)(Math.random()* strings.length))], volume, pitch, spot);
    }

}
