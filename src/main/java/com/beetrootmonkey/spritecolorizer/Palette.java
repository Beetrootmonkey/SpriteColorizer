package main.java.com.beetrootmonkey.spritecolorizer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

  public Color apply(int rgba) {
    int alpha = (rgba & 0xff000000) >> 24 & 0x000000ff;
    int red = (rgba & 0x00ff0000) >> 16;
    int green = (rgba & 0x0000ff00) >> 8;
    int blue = rgba & 0x000000ff;
    return apply(new Color(red, green, blue, alpha));
  }

  public Color apply(Color color) {
    int width = image.getWidth();
    int alpha = color.getAlpha();
    float brightness = (float) (0.2126 * color.getRed() + 0.7152 * color.getGreen() + 0.0722 * color.getBlue()) / 256; // = 0 bis < 256
    float index = width * (1 - brightness); // > 0 bis = 10
    int leftIndex = (int) Math.floor(index - 0.5);
    int rightIndex = (int) Math.ceil(index - 0.5);
    float leftIndexFactor = (rightIndex + 0.5f) - index;
    float rightIndexFactor = index - (leftIndex + 0.5f);

    if (leftIndex < 0) {
      leftIndex = 0;
      leftIndexFactor = 1;
      rightIndexFactor = 0;
    }
    if (rightIndex >= width) {
      rightIndex = width - 1;
      leftIndexFactor = 0;
      rightIndexFactor = 1;
    }

    Color clrLeft = new Color(image.getRGB(leftIndex, 0));
    Color clrRight = new Color(image.getRGB(rightIndex, 0));
    int red = (int) (clrLeft.getRed() * leftIndexFactor + clrRight.getRed() * rightIndexFactor);
    int green = (int) (clrLeft.getGreen() * leftIndexFactor + clrRight.getGreen() * rightIndexFactor);
    int blue = (int) (clrLeft.getBlue() * leftIndexFactor + clrRight.getBlue() * rightIndexFactor);

    return new Color(red, green, blue, alpha);
  }

  public Palette(BufferedImage image, String name) {
    this.image = image;
    this.name = name;
  }
}
