package model.imagefiltering;

/**
 * Basic class for Filters.
 */
public abstract class Filtering extends AbstractProcessing {

  @Override
  public int getNewValue(int k, int i, int j) {
    int value = 0;
    for (int ki = 0; ki < kernel.length; ki++) {
      for (int kj = 0; kj < kernel[ki].length; kj++) {
        int imageX = (j - kernel[ki].length / 2 + kj + getImageWidth()) % getImageWidth();
        int imageY = (i - kernel.length / 2 + ki + getImageHeight()) % getImageHeight();
        value += kernel[ki][kj] * getImage()[imageY][imageX][k];
      }
    }
    return value;
  }


}
