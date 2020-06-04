package model.imagegenerating;

import java.awt.image.BufferedImage;

/**
 * Class VRainbow represents the Vertical Rainbow object to be created with 7 lines of different
 * color and same width.
 */
public class VRainbow extends RainbowGen {

  @Override
  protected void drawPic(BufferedImage img, int w, int h) {
    int colorNumber = -1;

    int module = (int) Math.ceil((double) w / 7);

    for (int y = 0; y < h; y++) {
      colorNumber = -1;
      for (int x = 0; x < w; x++) {
        if (x % module == 0) {
          colorNumber++;
        }
        img.setRGB(x, y, this.getColors().get(colorNumber).getRGB());
      }
    }
  }
}
