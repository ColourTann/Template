package com.tann.jamgame.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class Prefs {



    private static Preferences prefs;
    private static Preferences get(){
        if (prefs==null){
            prefs = Gdx.app.getPreferences("tannjam");
        }
        return prefs;
    }

    public static void setString(String key, String value){
        get().putString(key, value);
        get().flush();
    }

    public static void setInt(String key, int value){
        get().putInteger(key, value);
        get().flush();
    }

    public static void setFloat(String key, float value){
        get().putFloat(key, value);
        get().flush();
    }

    public static void setBoolean(String key, boolean value){
        get().putBoolean(key, value);
        get().flush();
    }

    public static boolean getBoolean(String key, boolean def){
        return get().getBoolean(key, def);
    }

    public static String getString(String key, String def){
        return get().getString(key, def);
    }

    public static int getInt(String key, int def){
        return get().getInteger(key, def);
    }

    public static float getFloat(String key, float def){
        return get().getFloat(key, def);
    }


    public static void RESETSAVEDATA() {
        get().clear();
        get().flush();
    }

    public static void add(String key, int i) {
        setInt(key, getInt(key, 0)+i);
    }
}
