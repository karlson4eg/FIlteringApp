package model.imagegenerating;

/**
 * Interface IImgGenerator represents operation that will be performed for getting an image.
 */
public interface IImgGenerator {

  /**
   * Method generateImage generates a picture with a given pattern.
   *
   * @param w needed width from user's input.
   * @param h needed height from user's input.
   * @param name needed name of the output picture.
   */
  void generateImage(int w, int h, String name);

}
