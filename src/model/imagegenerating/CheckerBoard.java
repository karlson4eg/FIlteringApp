package model.imagegenerating;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class CheckerBoard represents the checkerboard pattern with right proportions and given square
 * size.
 */
public class CheckerBoard implements IImgGenerator {

  private Color currentColor = Color.BLACK;

  /**
   * Method switchColor switches colors from one to another for creating a checkerboard right
   * pattern.
   *
   * @param step size of one square.
   * @param position current X position in checkerboard canvas.
   */
  private void switchColor(int step, int position) {
    if (position % step == 0) {
      if (currentColor == Color.BLACK) {
        currentColor = Color.WHITE;
      } else {
        currentColor = Color.BLACK;
      }

    }
  }

  @Override
  public void generateImage(int w, int h, String name) throws IllegalArgumentException {
    if (w != h) {
      throw new IllegalArgumentException("Square should have same width and height!");
    }

    int dim = w * 8;

    BufferedImage img = new BufferedImage(dim, dim, BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < dim; y++) {
      switchColor(w, y);

      for (int x = 0; x < dim; x++) {
        switchColor(w, x);
        img.setRGB(x, y, currentColor.getRGB());
      }
    }

    try {
      File f = new File(name);
      ImageIO.write(img, "png", f);
    } catch (IOException e) {
      System.out.println("Error: " + e);
    }
  }
}