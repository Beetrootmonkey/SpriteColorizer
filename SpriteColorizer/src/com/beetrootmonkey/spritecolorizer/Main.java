package com.beetrootmonkey.spritecolorizer;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		SpriteColorizer colorizer = new SpriteColorizer("C:/Users/I Am The Issue/Desktop");
		colorizer.createExampleDirectory();
		colorizer.loadTemplates();
		colorizer.loadPalettes();
	}

}
