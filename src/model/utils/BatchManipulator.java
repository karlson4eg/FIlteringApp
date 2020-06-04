package model.utils;

import java.io.IOException;
import model.flaggenerating.AbstractFlagGenerator;
import model.flaggenerating.FranceFlagGenerator;
import model.flaggenerating.GreeceFlagGenerator;
import model.flaggenerating.Size;
import model.flaggenerating.SwitzerlandFlagGenerator;
import model.imagefiltering.AbstractProcessing;
import model.imagefiltering.BlurImage;
import model.imagefiltering.Dithering;
import model.imagefiltering.GreyScale;
import model.imagefiltering.Mosaic;
import model.imagefiltering.Sepia;
import model.imagefiltering.SharpeningImage;
import model.imagegenerating.CheckerBoard;
import model.imagegenerating.HRainbow;
import model.imagegenerating.IImgGenerator;
import model.imagegenerating.VRainbow;

/**
 * The Batch Manipulator Class is used to control the program based on input from a batch file.
 */
public class BatchManipulator {

  private String path;

  public BatchManipulator() {

  }


  /**
   * Construct a batch manipulator from the batch file at the specified path.
   *
   * @param path to the batch file.
   */
  public BatchManipulator(String path) {
    this.path = path;
  }

  /**
   * Executes the program based on the various commands in the batch file.
   *
   * @throws IOException if a command or operation is invalid.
   * @throws IllegalArgumentException if operation requires another parameter not provided
   */
  public void execute(String line) throws IOException, IllegalArgumentException {
    //Scanner scanner = new Scanner(new File(path));
    AbstractProcessing proc = null;
    String file = null;
    boolean filtering = true;

    String[] cm = line.split("\n");
    int it = 0;

    while (it < cm.length) {
      String[] commands = cm[it].split(" ");
      it++;
      switch (commands[0]) {
        case "load":
          file = commands[1];
          continue;
        case "save":
          file = commands[1];
          proc.saveImage(file);
          break;
        case "mosaic":
          if (commands.length == 2) {
            proc = new Mosaic(Integer.valueOf(commands[1]));
          } else {
            proc = new Mosaic();
          }
          break;
        case "blur":
          proc = new BlurImage();
          break;
        case "dithering":
          proc = new Dithering();
          break;
        case "greyscale":
          proc = new GreyScale();
          break;
        case "sepia":
          proc = new Sepia();
          break;
        case "sharpening":
          proc = new SharpeningImage();
          break;
        case "generate":
          filtering = false;
          handleImageGeneration(commands);
          break;
        default:
          throw new IOException("Illegal Command");
      }
      if (filtering) {
        proc.apply(file);
      }



    }
  }

  /**
   * Deals with commands and flags associated with generate. Commands: rainbow, checkerboard flags
   * -f greece, france, switzerland -h height int -w width int -v vertical striped rainbow -s small
   * 500px -m medium 1000px -l large 2000px -o output file string
   * <p>
   * Examples: generate rainbow -w 100 -h 1000 -o res/rainbow.png generate checkerboard -h 50 -w 50
   * -o res/checkerboard.png generate -f greece -l -o res/greece.png generate -f france -o
   * res/france.png generate -f switzerland -o -m res/switzerland.png generate rainbow -v -o
   * res/verticalrainbow.png
   * </p>
   */
  private void handleImageGeneration(String[] commands)
      throws IllegalArgumentException, IOException {
    // default width and height

    int width = 500;
    int height = 500;
    boolean vertical = false;
    boolean generateFlag = false;
    AbstractFlagGenerator flagGenerator = null;
    IImgGenerator imgGenerator = null;
    String image = null;
    Size size = Size.MEDIUM;
    String outfile = null;

    // deals with flags and the name of the image to be generated
    for (int i = 1; i < commands.length; i++) {
      switch (commands[i]) {
        case "-h":
          try {
            height = Integer.parseInt(commands[i + 1]);
            i++;
          } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("please specify a height following -h");
          } catch (Exception e) {
            throw new IllegalArgumentException("height should be an integer");
          }
          continue;
        case "-w":
          try {
            width = Integer.parseInt(commands[i + 1]);
            i++;
          } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("please specify a width following -w");
          } catch (Exception e) {
            throw new IllegalArgumentException("width should be an integer");
          }
          continue;
        case "-v":
          vertical = true;
          continue;
        case "-f":
          generateFlag = true;
          try {
            image = commands[i + 1];
            i++;
          } catch (Exception e) {
            throw new IllegalArgumentException("You must specify a flag to generate");
          }
          continue;
        case "-s":
          size = Size.SMALL;
          width = height = Size.SMALL.getHeight();
          continue;
        case "-m":
          size = Size.MEDIUM;
          width = height = Size.MEDIUM.getHeight();
          continue;
        case "-l":
          size = Size.LARGE;
          width = height = Size.LARGE.getHeight();
          continue;
        case "-o":
          try {
            outfile = commands[i + 1];
            i++;
          } catch (Exception e) {
            throw new IllegalArgumentException("Must specify and outfile");
          }
          continue;
        default:
          image = commands[i];
          continue;
      }
    }

    // deals with the type of image to be generated
    switch (image) {
      case "greece":
        flagGenerator = new GreeceFlagGenerator();
        break;
      case "switzerland":
        flagGenerator = new SwitzerlandFlagGenerator();
        break;
      case "france":
        flagGenerator = new FranceFlagGenerator();
        break;
      case "checkerboard":
        imgGenerator = new CheckerBoard();
        break;
      case "rainbow":
        if (vertical) {
          imgGenerator = new VRainbow();
        } else {
          imgGenerator = new HRainbow();
        }
        break;
      default:
        throw new IllegalArgumentException("cannot generate that image");
    }

    if (outfile == null) {
      throw new IllegalArgumentException("Must specify and outfile using -o \"filepath\"");
    }
    if (generateFlag) {
      flagGenerator.create(size, outfile);
    } else {
      try {
        imgGenerator.generateImage(width, height, outfile);
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      } catch (Exception e) {
        throw new IllegalArgumentException("You must specify an image to generate");
      }
    }
  }

}
