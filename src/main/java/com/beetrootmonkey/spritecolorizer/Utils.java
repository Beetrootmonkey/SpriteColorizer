package main.java.com.beetrootmonkey.spritecolorizer;

import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Utils {


  public static int extractNumber(String fileName) {
    fileName = StringUtils.remove(fileName, ".png");
    fileName = StringUtils.remove(fileName, ".bmp");
    int number = -1;
    String[] parts = StringUtils.split(fileName, SpriteColorizer.NUMBER_SEPARATOR);
    if (parts.length >= 2) {
      try {
        number = Integer.parseInt(parts[parts.length - 1]);

      } catch (NumberFormatException e) {
        // Do nothing
      }
    }
    return number;
  }

  public static String extractName(String fileName) {
    fileName = StringUtils.remove(fileName, ".png");
    fileName = StringUtils.remove(fileName, ".bmp");
    fileName = StringUtils.remove(fileName, "$");

    String name = "";
    String[] parts = StringUtils.split(fileName, SpriteColorizer.NUMBER_SEPARATOR);
    for (int i = 0; i < parts.length - 1; i++) {
      name += parts[i];
    }
    return name.isEmpty() ? fileName : name;
  }

  public static BufferedImage joinBufferedImage(BufferedImage bottomImage, BufferedImage topImage) {
    int width = Math.max(bottomImage.getWidth(), topImage.getWidth());
    int height = Math.max(bottomImage.getHeight(), topImage.getHeight());
    //create a new buffer and draw two image into the new image
    BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = newImage.createGraphics();
//        g2.setPaint(Color.white);
    Color oldColor = g2.getColor();
    g2.fillRect(0, 0, width, height);
    //draw image
    g2.setColor(oldColor);
    g2.drawImage(bottomImage, null, 0, 0);
    g2.drawImage(topImage, null, 0, 0);
    g2.dispose();
    return newImage;
  }

  public static String applyPalette(String name, Palette palette) {
    return StringUtils.replace(name, SpriteColorizer.NAME_PLACEHOLDER, palette.getName());
  }
}
