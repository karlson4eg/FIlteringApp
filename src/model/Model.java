package model;

import java.io.File;
import model.flaggenerating.FranceFlagGenerator;
import model.flaggenerating.GreeceFlagGenerator;
import model.flaggenerating.IFlag;
import model.flaggenerating.Size;
import model.flaggenerating.SwitzerlandFlagGenerator;
import model.imagefiltering.AbstractProcessing;
import model.imagefiltering.BlurImage;
import model.imagefiltering.Mosaic;
import model.imagegenerating.CheckerBoard;
import model.imagegenerating.HRainbow;
import model.imagegenerating.IImgGenerator;
import model.imagegenerating.VRainbow;
import view.View;

public class Model {

  private View view;
  private File openFile;
  private int iter;
  private int numUndos;

  public Model(){
    openFile = null;
    numUndos = 5;
    iter = 0;
  }

  public void switchFilter(String actionCommand) {
    AbstractProcessing proc = null;
    if (openFile == null) {
      return;
    }
    switch(actionCommand) {
      case "Blur":
        proc = new BlurImage();
        break;
      case "Mosaic":
        proc = new Mosaic();
        break;
      //TODO: add other features like mosaic seeds
      default:
        return;
    }
    try {
      // copy the image
      System.out.println("opening" + openFile.getAbsolutePath());
      proc.apply(openFile.getAbsolutePath());
      nextTmp();
      System.out.println("saving to" + openFile.getAbsolutePath());
      proc.saveImage("temporary/result.png");
    } catch (Exception e) {
      e.printStackTrace();
      prevTmp();
    }
    view.displayImage(openFile.getAbsolutePath());
  }

  public void switchFlag(String actionCommand) {
    IFlag flag = null;
    switch (actionCommand) {
      case "Flag of France":
        // openFile = new File("./.tmp/flagOfFrance.png";
        flag = new FranceFlagGenerator();
        break;
      case "Flag of Greece":
        // openFile = new File("./.tmp/flagOfGreece.png";
        flag = new GreeceFlagGenerator();
        break;
      case "Flag of Switzerland":
        // openFile = new File("./.tmp/swissFlag.png";
        flag = new SwitzerlandFlagGenerator();
        break;
      default:
        return;
    }
    nextTmp();
    flag.create(Size.SMALL, openFile.getAbsolutePath());
    view.displayImage(openFile.getAbsolutePath());
  }

  public void switchGenerate(String actionCommand) {
    IImgGenerator imgGenerator = null;
    switch (actionCommand) {
      case "Vertical Rainbow":
        // openFile = new File("./.tmp/verticalRainbow.png");
        imgGenerator = new VRainbow();
        break;
      case "Horizontal Rainbow":
        // openFile = new File("./.tmp/hRainbow.png");
        imgGenerator = new HRainbow();
        break;
      case "Checker Board":
        // openFile = new File("./.tmp/checker.png");
        imgGenerator = new CheckerBoard();
        break;
      default:
        return;
    }
    nextTmp();
    imgGenerator.generateImage(500, 500, openFile.getAbsolutePath());
    view.displayImage(openFile.getAbsolutePath());
  }

  public void switchNav(String actionCommand) {
    switch (actionCommand) {
      case "Open":
        nextTmp();
        view.openFile(openFile);
        break;
      case "Save":
        view.saveFile();
        break;
      case "Close":
        view.clearImage();
        openFile = null;
        break;
      default:
        return;
    }

  }

  private void nextTmp() {
    openFile = new File("temporary/" + iter + ".png");
    iter = (iter + 1) % numUndos;
  }

  private void prevTmp() {
    iter = (iter - 1) % numUndos;
    openFile = new File("temporary/" + iter + ".png");
  }

  public File getOpenFile() {
    return openFile;
  }

  public void setView(View view) {
    this.view = view;
  }
}
