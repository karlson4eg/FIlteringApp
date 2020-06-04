package model.flaggenerating;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class AbstractFlagGenerator represents the Abstract parent class for minor Flag options. This
 * class contains basic operations for creating Flags of given countries.
 */
public abstract class AbstractFlagGenerator implements IFlag {

  /**
   * Method drawFlag that will be used necessarily by classes that extend AbstractFlagGenerator.
   *
   * @param img image that is being processed.
   * @param width width of image.
   * @param height height of image.
   */
  protected abstract void drawFlag(BufferedImage img, int width, int height);

  @Override
  public void create(Size size, String filename) {
    int height = size.getHeight();
    int width = (int) (height * 1.5);

    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    drawFlag(img, width, height);
    saveFlag(img, filename);
  }


  /**
   * Method saveFlag saves the processed image.
   *
   * @param img image canvas.
   * @param path path where the file should be sorted.
   */
  private void saveFlag(BufferedImage img, String path) {
    try {
      File f = new File(path);
      ImageIO.write(img, "png", f);
    } catch (IOException e) {
      System.out.println("Error: " + e);
    }
  }

}
