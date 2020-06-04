package model.flaggenerating;

/**
 * Interface IFlag represents all common operations for AbstractFlagGenerator class.
 */
public interface IFlag {

  /**
   * Method create creates a Flag with a given Size and saves it with a provided name.
   *
   * @param size Enum of sizes, user picks up an option to choose a size of a generated Flag.
   * @param filename name of the generated file.
   */
  void create(Size size, String filename);
}
