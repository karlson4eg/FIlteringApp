package model.flaggenerating;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Class FranceFlagGenerator represents an object of the France Flag to be created.
 */
public class FranceFlagGenerator extends AbstractFlagGenerator {

  @Override
  public void drawFlag(BufferedImage img, int width, int height) {
    int[] colors = {Color.blue.getRGB(), Color.white.getRGB(), Color.red.getRGB()};

    int w = width / 3;
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        img.setRGB(x, y, colors[x / w]);
      }
    }
  }
}
