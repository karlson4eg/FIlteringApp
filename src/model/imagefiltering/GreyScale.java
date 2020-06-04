package model.imagefiltering;

/**
 * Class Greyscale represents the GreyScale Filter for an image.
 */
public class GreyScale extends Coloring {

  @Override
  public void initKernel() {
    kernel = new double[][]{
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
    };

  }
}