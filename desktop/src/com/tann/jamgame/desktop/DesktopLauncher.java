package com.tann.jamgame.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.tann.jamgame.Main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DesktopLauncher {

  private static final boolean forcePack = false;
  public static void main (String[] arg)  {
    try {
      checkPack("../../images", "misc/imagehash2d.txt", false);
    }
    catch (Exception e){
      System.err.println("Probably running as standalone desktop");
    }
//        System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.foregroundFPS = 60;
    config.backgroundFPS = 20;
    config.width=1280;
    config.height=720;
    config.samples=10;
    config.title="jam template";
    config.resizable = false;
//    config.addIcon("misc/icon.png", Files.FileType.Internal);
    new LwjglApplication(new Main(), config);
  }

  private static void checkPack(String dir, String file, boolean threeD){

    if(forcePack){
      packImages(threeD);
      return;
    }
    int total = hash(new File(dir));
    int c = 0;
    try {
      File f= new File(file);
      if(!f.exists()){
        PrintWriter pw = new PrintWriter(f);
        pw.print(-1);
        pw.close();
      }
      Scanner s = new Scanner(f);
      c = s.nextInt();
      if (total != c){
        PrintWriter pw = new PrintWriter(f);
        pw.print(total);
        pw.close();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    if(total!=c){
      packImages(threeD);
    }

  }

  private static int hash(File f){
    int total = 0;
    if(f.isDirectory()) {
      for (File content :f.listFiles()){
        total += hash(content);
      }
    }
    return (int) (total + f.getName().hashCode() + f.lastModified());
  }

  private static void packImages(boolean threeD){
    System.out.println("packing "+(threeD?"3d":"2d"));
    TexturePacker.Settings settings = new TexturePacker.Settings();
    settings.silent = true;
    settings.maxWidth = 2048;
    settings.maxHeight = 2048;
    if(threeD){
      int size = 256;
      settings.minWidth = size;
      settings.minHeight = size;
      settings.maxWidth = size;
      settings.maxHeight = size;
      settings.paddingX = 2;
      settings.paddingY = 2;
      settings.combineSubdirectories = true;
      settings.filterMag = Texture.TextureFilter.MipMapLinearLinear;
      settings.filterMin = Texture.TextureFilter.MipMapLinearLinear;
      TexturePacker.process(settings, "../../images_3d", "3d", "atlas_image");
    }
    else{
      settings.combineSubdirectories = true;
      settings.filterMag = Texture.TextureFilter.Nearest;
      settings.filterMin = Texture.TextureFilter.Nearest;
      TexturePacker.process(settings, "../../images", "2d", "atlas_image");
    }
  }
}
