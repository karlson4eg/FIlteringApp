package model.imagegenerating;

import java.awt.image.BufferedImage;

/**
 * Class HRainbow represents the Horizontal Rainbow object to be created with 7 lines of different
 * color and same height.
 */
public class HRainbow extends RainbowGen {

  @Override
  protected void drawPic(BufferedImage img, int w, int h) {
    int colorNumber = -1;

    int module = (int) Math.ceil((double) h / 7);

    for (int y = 0; y < h; y++) {
      if (y % module == 0) {
        colorNumber++;
      }
      for (int x = 0; x < w; x++) {

        img.setRGB(x, y, this.getColors().get(colorNumber).getRGB());
      }
    }
  }
}
