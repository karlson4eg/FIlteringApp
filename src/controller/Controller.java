package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import model.flaggenerating.FranceFlagGenerator;
import model.flaggenerating.GreeceFlagGenerator;
import model.flaggenerating.IFlag;
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
import model.utils.BatchManipulator;
import view.View;

public class Controller implements ActionListener {

  private View view;
  private File openFile;
  private int iter;
  private int numUndos;
  private ArrayList<String> undo;
  private Stack<String> redo;
  private String tmpFolder;

  public Controller() {
    tmpFolder = "tmp/";
    new File(tmpFolder).mkdirs();
    openFile = null;
    numUndos = 5;
    iter = -1;
    undo = new ArrayList();
    redo = new Stack();
  }

  public void setView(View view) {
    this.view = view;
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    switchNav(e.getActionCommand());
    switchGenerate(e.getActionCommand());
    switchFlag(e.getActionCommand());
    switchFilter(e.getActionCommand());
    switchEdit(e.getActionCommand());

    if (openFile != null) {
      System.out.println("Openfile: " + openFile.getAbsolutePath());
      System.out.println("iter: " + iter);

    }


  }

  private void switchEdit(String actionCommand){
    switch (actionCommand) {
      case "Undo":
        if (undo.size() > 1) {
          prevTmp();
          view.displayImage(openFile.getAbsolutePath());
        }
        break;
      case "Redo":
        if (redo.size() > 0) {
          openFile = new File(redo.pop());
          iter = (iter + 1) % numUndos;
          undo.add(openFile.getAbsolutePath()); // remove from redo and add back to undo
          view.displayImage(openFile.getAbsolutePath()); // display what we popped
        }
        break;
      default:
        return;
    }
  }

  private void switchFilter(String actionCommand) {
    AbstractProcessing proc = null;
    if (openFile == null) {
      return;
    }
    switch (actionCommand) {
      case "Blur":
        proc = new BlurImage();
        break;
      case "Dither":
        proc = new Dithering();
        break;
      case "Greyscale":
        proc = new GreyScale();
        break;
      case "Mosaic":
        proc = new Mosaic();
        break;
      case "Sepia":
        proc = new Sepia();
        break;
      case "Sharpen":
        proc = new SharpeningImage();
        break;
      default:
        return;
    }
    try {
      // copy the image
      proc.apply(openFile.getAbsolutePath());
      nextTmp();
      proc.saveImage(openFile.getAbsolutePath());
    } catch (Exception e) {
      e.printStackTrace();
    }
    view.displayImage(openFile.getAbsolutePath());
  }

  private void switchFlag(String actionCommand) {
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

  private void switchGenerate(String actionCommand) {
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

  private void switchNav(String actionCommand) {
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
      case "Batch":

        BatchManipulator bm = new BatchManipulator();
        try {
          bm.execute(view.getJtextAreaText());

        } catch (IOException e) {
          e.printStackTrace();
        }

        break;
      default:
        return;
    }

  }

  private void nextTmp() {
    iter = (iter + 1) % numUndos;
    openFile = new File(tmpFolder + iter + ".png");
    redo = new Stack(); // empty redo
    undo.add(tmpFolder + iter + ".png");
    while (undo.size() > numUndos) {
      undo.remove(0);
    }
  }

  private void prevTmp() {
    iter = (iter - 1) % numUndos;
    if (iter < 0) {
      iter += numUndos; // we don't want negative numbers
    }
    redo.add(undo.remove(undo.size() - 1)); // pop the top of undo and add it to redo
    openFile = new File(tmpFolder + iter + ".png");
  }

  public File getOpenFile() {
    return openFile;
  }
}
