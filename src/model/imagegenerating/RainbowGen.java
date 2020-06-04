package model.imagegenerating;

import java.awt.Color;

/**
 * Class RainbowGen represents the initialisation of colors for rainbow.
 */
public abstract class RainbowGen extends AbstractGen {

  @Override
  public void initColors() {
    this.getColors().add(Color.RED);
    this.getColors().add(Color.ORANGE);
    this.getColors().add(Color.YELLOW);
    this.getColors().add(Color.GREEN);
    this.getColors().add(Color.CYAN);
    this.getColors().add(Color.BLUE);
    this.getColors().add(Color.PINK);
  }


}
