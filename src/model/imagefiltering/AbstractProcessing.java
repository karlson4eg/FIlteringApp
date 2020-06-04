package model.imagefiltering;

import java.io.IOException;
import model.utils.ImageUtil;

/**
 * Class AbstractProcessing represents basic operations for image processing.
 */
public abstract class AbstractProcessing implements ImageProcessing {

  double[][] kernel;
  private int kernelWidth;
  private int kernelHeight;

  private int[][][] image;
  private int imageWidth;
  private int imageHeight;
  private int[][][] processedImage;


  /**
   * Method getNewValue generates and returns a new pixel value.
   *
   * @param a channel number.
   * @param b X position of pixel.
   * @param c Y position of pixel.
   * @return returns a new processed pixel based on original pixel.
   */
  public abstract int getNewValue(int a, int b, int c);

  /**
   * Method initKernel used to set up a kernel for a chosen Filter that will be overridden in
   * subclasses which will represent filters.
   */
  public abstract void initKernel();

  /**
   * Method AbstractProcessing sets up kernel's basic properties.
   */
  public AbstractProcessing() {
    this.kernelWidth = 3;
    this.kernelHeight = 3;
    kernel = new double[kernelHeight][kernelWidth];
  }

  /**
   * Method loadImage sets up the data from the given image.
   *
   * @param pathToFile path to the file.
   * @throws IOException throws if file is unable to locate or open.
   */
  public void loadImage(String pathToFile) throws IOException {
    image = ImageUtil.readImage(pathToFile);
    imageWidth = ImageUtil.getWidth(pathToFile);
    imageHeight = ImageUtil.getHeight(pathToFile);
    processedImage = new int[imageHeight][imageWidth][3];
  }

  /**
   * Method saveImage saves the processed image to the given path.
   *
   * @param path place where user will store the image.
   * @throws IOException throws if it is impossible to save file.
   */
  public void saveImage(String path) throws IOException {
    ImageUtil.writeImage(processedImage, imageWidth, imageHeight, path);
  }

  @Override
  public void apply(String path) throws IOException {
    loadImage(path);
    initKernel();
    setKernelDim();

    for (int k = 0; k < 3; k++) {
      for (int i = 0; i < imageHeight; i++) {
        for (int j = 0; j < imageWidth; j++) {

          processedImage[i][j][k] = clampingValue(getNewValue(k, i, j));
        }
      }
    }
  }

  /**
   * If the new RGB value is over 255 or less than 0, cast it to the max or min values.
   *
   * @param value value of the one RGB channel of the processed pixel.
   * @return new value of the one RGB channel of the processed pixel.
   */
  private int clampingValue(int value) {
    if (value < 0) {
      value = 0;
    } else if (value > 255) {
      value = 255;
    }
    return value;
  }

  /**
   * Method setKernelDim gets the kernel sizes and sets up them into properties.
   */
  private void setKernelDim() {
    this.kernelHeight = kernel.length;
    this.kernelWidth = kernel[0].length;

  }

  /**
   * Method getImage returns the image as a 3d array.
   *
   * @return returns the image as a 3d array.
   */
  public int[][][] getImage() {
    return image;
  }

  /**
   * Method getImageWidth returns the width of the loaded image.
   *
   * @return the width of the loaded image.
   */
  public int getImageWidth() {
    return imageWidth;
  }

  /**
   * Method getImageHeight returns the height of the loaded image.
   *
   * @return the height of the loaded image.
   */
  public int getImageHeight() {
    return imageHeight;
  }

  public int[][][] getProcessedImage() {
    return processedImage;
  }
}