package model.flaggenerating;

/**
 * Class Size is an enum for picking up a size of a generated image.
 */
public enum Size {
  SMALL(500),
  MEDIUM(1000),
  LARGE(2000);

  private final int height;

  Size(int height) {
    this.height = height;
  }

  public int getHeight() {
    return height;
  }
}
