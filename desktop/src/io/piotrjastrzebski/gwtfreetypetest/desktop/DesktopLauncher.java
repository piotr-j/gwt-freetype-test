package io.piotrjastrzebski.gwtfreetypetest.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.piotrjastrzebski.gwtfreetypetest.GwtFreetypeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new GwtFreetypeGame(), config);
	}
}
