package view;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Controller;

public class View extends JFrame {
  final private Controller controller;
  // components of our view
  private JPanel mainPanel;
  private JScrollPane mainScrollPane;
  private JPanel imagePanel;
  private JMenuBar menuBar;
  private JMenu file, edit, filter, fileNew;
  private JFileChooser fileChooser;
  private JLabel imageLabel;
  private JScrollPane imageScrollPane;

  private JPanel batchPanel;
  private JTextArea textArea;
 private  JScrollPane scrollPane ;

  private int mainWidth;
  private int mainHeight;

  public View(Controller controller) {
    this.controller = controller;
    this.mainWidth = 1000;
    this.mainHeight = 1000;
    this.setTitle("FWI");
    this.setSize(mainWidth, mainHeight);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    fileChooser = new JFileChooser(".");
    fileChooser.setFileFilter(new FileNameExtensionFilter("JPG, GIF, & PNG Images", "jpg", "gif",
            "png"));

    initPanels();

    setMenuBar();

    this.setVisible(true);
  }

  private void initPanels() {
    // init main panel
    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //mainScrollPane = new JScrollPane(mainPanel);
    //this.add(mainScrollPane);
    this.add(mainPanel);

    // initialize image panel
    imagePanel = new JPanel();
    imagePanel.setLayout(new FlowLayout());
    mainPanel.add(imagePanel);

    imageLabel = new JLabel();
    imageScrollPane = new JScrollPane(imageLabel);


    batchPanel = new JPanel();
    textArea = new JTextArea(5,10);
    scrollPane = new JScrollPane( textArea );



    mainPanel.add(scrollPane);

    imageScrollPane.setPreferredSize(new Dimension(900, 500));
    imagePanel.add(imageScrollPane);
  }

  public void displayImage(String img) {
    ImageIcon imgDisplay = new ImageIcon(img);
    imgDisplay.getImage().flush();
    imageLabel.setIcon(imgDisplay);
  }

  public void openFile(File tmp) {
    if (fileChooser.showOpenDialog(mainPanel) == JFileChooser.APPROVE_OPTION) {
      File openFile = fileChooser.getSelectedFile();
      try {
        Files.copy(openFile.toPath(), tmp.toPath(), StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException e) {
        e.printStackTrace();
      }
      displayImage(tmp.getAbsolutePath());
    }
  }

  public void saveFile() {
    File saveFile;
    if (fileChooser.showSaveDialog(mainPanel) == JFileChooser.APPROVE_OPTION) {
      saveFile = new File(fileChooser.getSelectedFile().getAbsolutePath() + ".png");
      try {
        Files.copy(controller.getOpenFile().toPath(), saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void setMenuBar() {
    menuBar = new JMenuBar();
    this.setJMenuBar(menuBar);

    // Menu options
    file = new JMenu("File");
    edit = new JMenu("Edit");
    filter = new JMenu("Filter");
    fileNew = new JMenu("New");


    // menu items
    String[] fileNames = {"Open", "Save", "Close", "Batch"};
    String[] editNames = {"Undo", "Redo"};
    String[] generateNames = {"Vertical Rainbow", "Horizontal Rainbow", "Checker Board",
            "Flag of France", "Flag of Greece", "Flag of Switzerland"};
    String[] filterNames = {"Blur", "Dither", "Greyscale", "Mosaic", "Sepia", "Sharpen"};

    // add menu options to various menus
    addMenuItems(fileNew, generateNames);
    file.add(fileNew);
    addMenuItems(file, fileNames);
    addMenuItems(edit, editNames);
    addMenuItems(filter, filterNames);

    // add to menubar
    menuBar.add(file);
    menuBar.add(edit);
    menuBar.add(filter);
  }

  private void addMenuItems(JMenu menu, String[] menuItems) {
    for (String item : menuItems) {
      JMenuItem menuItem = new JMenuItem(item);
      menu.add(menuItem);
      menuItem.addActionListener(controller);
    }
  }

  @Override
  public int getHeight() {
    return mainHeight;
  }

  @Override
  public int getWidth() {
    return mainWidth;
  }

  public void clearImage() {
    imageLabel.setIcon(null);
  }

  public String getJtextAreaText(){
    return textArea.getText();
  }
}
