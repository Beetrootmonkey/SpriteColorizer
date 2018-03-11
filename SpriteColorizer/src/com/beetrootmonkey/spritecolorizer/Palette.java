package com.beetrootmonkey.spritecolorizer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Palette {
	private BufferedImage image = null;
	private String name = "";
	
	
	
	public BufferedImage getImage() {
		return image;
	}

	public String getName() {
		return name;
	}

	public static Palette create(File file) {
		if (file == null) {
			return null;
		}

		// Create BufferedImage
		BufferedImage image = null;
		String name = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.out.println("Warning: File not an Image!");
			return null;
		}
		
		// Extract name and number
		name = Utils.extractName(file.getName());

		return new Palette(image, name);
	}
	
	public Palette(BufferedImage image, String name) {
		this.image = image;
		this.name = name;
	}
}
