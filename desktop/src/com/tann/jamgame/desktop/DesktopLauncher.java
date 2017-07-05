package com.tann.jamgame.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tann.jamgame.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.resizable=true;
        config.vSyncEnabled=true;
        config.foregroundFPS=60;
        config.width=Main.width;
        config.height=Main.height;
        config.title="template";
//        config.addIcon("icon.png", Files.FileType.Internal);
        new LwjglApplication(new Main(), config);
	}
}
