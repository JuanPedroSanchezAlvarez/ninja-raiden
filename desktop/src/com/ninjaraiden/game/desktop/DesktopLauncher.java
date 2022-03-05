package com.ninjaraiden.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ninjaraiden.game.NinjaRaidenGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Ninja Raiden";
		config.width = 800;
		config.height = 600;
		new LwjglApplication(new NinjaRaidenGame(), config);
	}
}
