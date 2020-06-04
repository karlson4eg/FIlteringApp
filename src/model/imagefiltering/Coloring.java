package model.imagefiltering;

/**
 * Basic class for coloring filters.
 */
public abstract class Coloring extends AbstractProcessing {

  @Override
  public int getNewValue(int k, int i, int j) {
    int value = 0;
    for (int kj = 0; kj < kernel[k].length; kj++) {
      value += kernel[k][kj] * getImage()[i][j][kj];
    }
    return value;
  }
}
