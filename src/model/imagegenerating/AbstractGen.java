package model.imagegenerating;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Class AbstractGen represents basic operations for generating images.
 */
public abstract class AbstractGen implements IImgGenerator {

  private List<Color> colors = new ArrayList<>();

  /**
   * Setting colors for generating an image.
   */
  public abstract void initColors();

  /**
   * Method drawPic has the main logic in drawing the image pattern.
   *
   * @param img the main canvas for drawing.
   * @param w width of canvas.
   * @param h height of canvas.
   */
  protected abstract void drawPic(BufferedImage img, int w, int h);

  /**
   * Method saveImg saves the processed image.
   *
   * @param img filled canvas.
   * @param path path where to store the file as well as the name.
   */
  private void saveImg(BufferedImage img, String path) {

    try {
      File f = new File(path);
      ImageIO.write(img, "png", f);
    } catch (IOException e) {
      System.out.println("Error: " + e);
    }
  }

  @Override
  public void generateImage(int w, int h, String name) {
    initColors();
    BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    drawPic(img, w, h);
    saveImg(img, name);
  }

  /**
   * Method getColors returns all colors that were setup.
   *
   * @return returns all colors that were setup.
   */
  public List<Color> getColors() {
    return colors;
  }


}
