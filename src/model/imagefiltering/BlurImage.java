package model.imagefiltering;

/**
 * Class BlurImage represents the Blurring Filter for an image.
 */
public class BlurImage extends Filtering {

  @Override
  public void initKernel() {

    kernel = new double[][]{
        {1.0 / 16, 1.0 / 8, 1.0 / 16},
        {1.0 / 8, 1.0 / 4, 1.0 / 8},
        {1.0 / 16, 1.0 / 8, 1.0 / 16}
    };

  }
}
