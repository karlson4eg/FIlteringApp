package model.imagefiltering;

/**
 * Class Sepia represents the Sepia Filter for an image.
 */
public class Sepia extends Coloring {

  @Override
  public void initKernel() {
    kernel = new double[][]{
        {0.393, 0.769, 0.189},
        {0.349, 0.686, 0.168},
        {0.272, 0.534, 0.131},
    };
  }
}