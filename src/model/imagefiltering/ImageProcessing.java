package model.imagefiltering;

import java.io.IOException;

/**
 * Interface represents all the possible operations could be done to process image in different
 * ways.
 */

public interface ImageProcessing {

  /**
   * Method apply used for applying a Filter to the given image.
   *
   * @param path path to the file the targeted image.
   * @throws IOException throws if image is not found or being unable to open.
   */
  void apply(String path) throws IOException;

  /**
   * Saves an image to the specified string path.
   * @param path to where to save the image.
   * @throws IOException throws IOException if the path doesn't exist.
   */
  void saveImage(String path) throws IOException;

}
