package model.flaggenerating;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Class GreeceFlagGenerator represents an object of the Greece Flag to be created.
 */
public class GreeceFlagGenerator extends AbstractFlagGenerator {

  @Override
  public void drawFlag(BufferedImage img, int width, int height) {
    int[] colors = {Color.blue.getRGB(), Color.white.getRGB()};

    int h = height / 9;
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        img.setRGB(x, y, colors[y / h % 2]);
      }
    }
    for (int x = 0; x < h * 5; x++) {
      for (int y = 0; y < h * 5; y++) {
        img.setRGB(x, y, colors[y / h == 2 || x / h == 2 ? 1 : 0]);
      }
    }

  }
}
