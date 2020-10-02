package com.kahveci.gossamer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kahveci.gossamer.Gossamer;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width =  Gossamer.WIDTH;
		config.height = Gossamer.HEIGHT;
		config.title = Gossamer.TITILE;
		new LwjglApplication(new Gossamer(), config);
	}
}
