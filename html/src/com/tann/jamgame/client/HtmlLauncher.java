package com.tann.jamgame.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.tann.jamgame.Main;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
            GwtApplicationConfiguration gwtac = new GwtApplicationConfiguration(Main.width, Main.height);
            gwtac.
                return gwtac;
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new Main();
        }
}