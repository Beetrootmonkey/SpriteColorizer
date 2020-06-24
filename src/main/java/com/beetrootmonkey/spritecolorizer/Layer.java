package main.java.com.beetrootmonkey.spritecolorizer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Layer {
	private BufferedImage image;
	private String name = "";
	private int number = -1;
	private boolean keepColor = false;

	public BufferedImage getImage() {
		return image;
	}

	public String getName() {
		return name;
	}

	public int getNumber() {
		return number;
	}

	public boolean getKeepColor() {
		return keepColor;
	}

	public static Layer create(File file) {
		if (file == null) {
			return null;
		}

		// Create BufferedImage
		BufferedImage image = null;
		String name = null;
		int number = -1;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.out.println("Warning: File not an Image!");
			return null;
		}

		// Extract name and number
		name = Utils.extractName(file.getName());
		number = Utils.extractNumber(file.getName());
		if (number < 0) {
			number = 0;
		}

		boolean keepColor = file.getName().startsWith("$");

		return new Layer(image, name, number, keepColor);
	}

	public BufferedImage color(Palette palette) {
		System.out.println("Coloring layer " + number + " of template " + name + " with palette " + palette.getName());
		BufferedImage coloredImage = new BufferedImage(image.getWidth(), image.getHeight(),
				BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < coloredImage.getWidth(); i++) {
			for (int j = 0; j < coloredImage.getHeight(); j++) {
				 coloredImage.setRGB(i, j, keepColor ? image.getRGB(i, j) : palette.apply(image.getRGB(i, j)).getRGB());
			}
		}

		return coloredImage;
	}

	public Layer(BufferedImage image, String name, int number, boolean keepColor) {
		this.image = image;
		this.name = name;
		this.number = number;
		this.keepColor = keepColor;
	}

}
