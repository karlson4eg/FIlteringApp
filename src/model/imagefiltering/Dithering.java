package model.imagefiltering;

import java.io.IOException;

/**
 * Prints an image with black and white dots resembling an old newspaper print by implementing
 * the Floyd-Steinberg Algorithm.
 */
public class Dithering extends GreyScale {

  /**
   * Sets all the color channels for a pixel to a specified value.
   * @param x value of the pixel
   * @param y value of the pxiel
   * @param value of the color for the pixel to be changed to.
   */
  private void setPixel(int x, int y, int value) {
    if (x >= 0 && x < getImageWidth()
            && y >= 0 && y < getImageHeight()) {
      getProcessedImage()[y][x][0] =
      getProcessedImage()[y][x][1] =
      getProcessedImage()[y][x][2] = value;
    }
  }

  /**
   * Adds a value to the current pixel value at that location.
   * @param x value of the pixel
   * @param y value of the pixel
   * @param value of the color to be added to the pixel.
   */
  private void addPixel(int x, int y, int value) {
    if (x >= 0 && x < getImageWidth()
            && y >= 0 && y < getImageHeight()) {
      setPixel(x, y, getProcessedImage()[y][x][0] + value);
    }
  }

  @Override
  public void apply(String path) throws IOException {
    super.apply(path);
    for (int y = 0; y < getImageHeight(); y++) {
      for (int x = 0; x < getImageWidth(); x++) {
        int oldColor = getProcessedImage()[y][x][0];
        int newColor = oldColor > 127 ? 255 : 0;
        int error = oldColor - newColor;
        setPixel(x, y, newColor);

        // addPixel(x + 1, y, error * 7 / 16);
        // addPixel(x - 1, y - 1, error * 3 / 16);
        // addPixel(x, y - 1, error * 5 / 16);
        // addPixel(x + 1, y - 1, error / 16);

        addPixel(x + 1, y, (int)Math.round(error * 7.0 / 16));
        addPixel(x - 1, y + 1, (int)Math.round(error * 3.0 / 16));
        addPixel(x, y + 1, (int)Math.round(error * 5.0 / 16));
        addPixel(x + 1, y + 1, (int)Math.round(error / 16.0));
      }
    }
  }
}