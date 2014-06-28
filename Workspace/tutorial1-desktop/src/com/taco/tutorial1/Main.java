package com.taco.tutorial1;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "tutorial1";
		cfg.useGL20 = false;
		cfg.width = 780;
		cfg.height = 520;
		Texture.setEnforcePotImages(false); 
		
		new LwjglApplication(new tutorial1(), cfg);
	}
}
