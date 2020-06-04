package model.flaggenerating;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Class SwitzerlandFlagGenerator represents an object of the SwitzerlandF Flag to be created.
 */
public class SwitzerlandFlagGenerator extends AbstractFlagGenerator {

  @Override
  public void drawFlag(BufferedImage img, int width, int height) {
    int[] colors = { Color.red.getRGB(), Color.white.getRGB() };

    int s = height * 2 / 7;
    int w = height / 13;

    int centerX = width / 2;
    int centerY = height / 2;

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        boolean c = Math.abs(centerX - x) < w && Math.abs(centerY - y) < s
            || Math.abs(centerY - y) < w && Math.abs(centerX - x) < s;
        img.setRGB(x, y, colors[c ? 1 : 0]);
      }
    }
  }
}
