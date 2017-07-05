package com.tann.jamgame.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class ImagePacker {
    public static void main(String[] args){
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.combineSubdirectories = false;
        settings.maxWidth=2048;
        settings.maxHeight=2048;
        TexturePacker.process(settings, "../images", "../core/assets", "atlas_image");
    }
}
