package main.java.com.beetrootmonkey.spritecolorizer;

public class Main {

	public static void main(String[] args) {
		SpriteColorizer colorizer = new SpriteColorizer("C:/Users/nadel/Desktop");
		colorizer.createExampleDirectory();
		colorizer.loadTemplates();
		colorizer.loadPalettes();
	}

}
