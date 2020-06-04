package model.imagefiltering;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Creates a stained glass window effect for an image using a specified number of seeds.
 */
public class Mosaic extends Filtering {

  private int seeds;

  /**
   * Construct a Mosaic from a number of seeds.
   */
  public Mosaic(int seeds) {
    this.seeds = seeds;
  }

  /**
   * Construct a Mosaic from 4000 seeds by default.
   */
  public Mosaic() {
    this.seeds = 4000;
  }

  @Override
  public void initKernel() {
    // does nothing
  }

  /**
   * Calculate the distance between (x1, y1) and (x2, y2).
   *
   * @param x1 point
   * @param y1 point
   * @param x2 point
   * @param y2 point
   * @return the Euclidean distance.
   */
  private double distance(int x1, int y1, int x2, int y2) {
    return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
  }

  @Override
  public void apply(String path) throws IOException {
    super.apply(path);
    int[][] points = new int[seeds][2];
    int[][] pixels = new int[getImageHeight() * getImageWidth()][2];
    int[] pixelsPerSeed = new int[seeds];
    int[][] avgColorPerSeed = new int[seeds][3];
    HashMap mapPixelToSeed = new HashMap();

    // generate random points which pixels will cluster to
    points = generateRandomPoints(seeds);

    // generate points to cluster the clusters
    int[][] abstractCluster = generateRandomPoints((int) Math.sqrt(seeds));
    // assign every cluster to an abstract cluster such that I can find the closest abstract
    // cluster and all the clusters mapped to it
    ArrayList[] clusterList = clusterSeeds(points, abstractCluster);
    // assign every pixel to a cluster based on the abstract cluster
    HashMap pixelMap = clusterPixels(pixels, clusterList, abstractCluster);

    HashMap pointIndex = new HashMap();
    int iterator = 0;
    int closest;
    for (int i = 0; i < pixels.length; i++) {
      int[] assignedSeed = (int[]) pixelMap.get(pixels[i]);
      if (pointIndex.get(assignedSeed) == null) {
        pointIndex.put(assignedSeed, iterator);
        iterator++;
      }
      closest = (int) pointIndex.get(assignedSeed);
      pixelsPerSeed[closest]++;
      avgColorPerSeed[closest][0] += getImage()[pixels[i][1]][pixels[i][0]][0];
      avgColorPerSeed[closest][1] += getImage()[pixels[i][1]][pixels[i][0]][1];
      avgColorPerSeed[closest][2] += getImage()[pixels[i][1]][pixels[i][0]][2];
    }

    // for every seed, divide the total number of pixels connected to that seed to get the average
    for (int i = 0; i < seeds; i++) {
      if (pixelsPerSeed[i] != 0) {
        avgColorPerSeed[i][0] = avgColorPerSeed[i][0] / pixelsPerSeed[i];
        avgColorPerSeed[i][1] = avgColorPerSeed[i][1] / pixelsPerSeed[i];
        avgColorPerSeed[i][2] = avgColorPerSeed[i][2] / pixelsPerSeed[i];
      }
    }

    // Apply the average color to a processed image using the map
    int seedPosition;
    int pixel;
    // for every pixel in the image, assign the average color
    for (int x = 0; x < getImageWidth(); x++) {
      for (int y = 0; y < getImageHeight(); y++) {
        // get's the position of the closest seed to this pixel based on the hashmap
        pixel = getImageHeight() * x + y;
        seedPosition = (int) pointIndex.get(pixelMap.get(pixels[pixel]));
        getProcessedImage()[y][x][0] = avgColorPerSeed[seedPosition][0];
        getProcessedImage()[y][x][1] = avgColorPerSeed[seedPosition][1];
        getProcessedImage()[y][x][2] = avgColorPerSeed[seedPosition][2];
      }
    }

  }

  private HashMap clusterColors(int[][] points, int[] colors) {
    HashMap map = new HashMap();

    for (int i = 0; i < points.length; i++) {
      map.put(points[i], colors[i]);
    }
    return map;
  }


  private HashMap clusterPixels(int[][] pixels, ArrayList[] clusterList, int[][] absCluster) {
    HashMap map = new HashMap();

    int closest;
    int closestAbs;
    double minDistance;
    double d;

    ArrayList points;

    // ArrayList[] listOfPixels = new ArrayList[seeds];

    // traverse every pixel
    // find the abstract cluster it's closest to
    // then find the point within that abstract cluster that it's closest to
    // map the pixel to that point
    int i;
    for (int x = 0; x < getImageWidth(); x++) {
      for (int y = 0; y < getImageHeight(); y++) {
        i = getImageHeight() * x + y;
        pixels[i][0] = x;
        pixels[i][1] = y;
        closestAbs = 0;
        minDistance = Double.POSITIVE_INFINITY;
        // find closest abs cluster
        for (int j = 0; j < absCluster.length; j++) {
          d = distance(x, y, absCluster[j][0], absCluster[j][1]);
          if (d < minDistance) {
            closestAbs = j;
            minDistance = d;
          }
        }

        // find closest point
        points = clusterList[closestAbs];
        Iterator pointIter = points.iterator();
        closest = 0;
        minDistance = Double.POSITIVE_INFINITY;
        int[] point;
        int j = 0;
        while (pointIter.hasNext()) {
          point = (int[]) pointIter.next();
          d = distance(x, y, point[0], point[1]);
          if (d < minDistance) {
            closest = j;
            minDistance = d;
          }
          j++;
        }
        map.put(pixels[i], clusterList[closestAbs].get(closest));
        // listOfPixels[closestSeedPoint].add(pixels[i]);
      }
    }
    return map;
  }


  private ArrayList[] clusterSeeds(int[][] points, int[][] abstractClusterPoints) {
    // an array of ArrayLists
    ArrayList[] absList = new ArrayList[abstractClusterPoints.length];
    double minimum;
    double d;
    int closest = 0;
    for (int i = 0; i < points.length; i++) {
      minimum = Double.POSITIVE_INFINITY;
      for (int j = 0; j < abstractClusterPoints.length; j++) {
        d = distance(points[i][0], points[i][1], abstractClusterPoints[j][0],
                abstractClusterPoints[j][1]);
        if (d < minimum) {
          closest = j;
          minimum = d;
        }
      }
      if (absList[closest] == null) {
        absList[closest] = new ArrayList<Integer>();
      }
      absList[closest].add(points[i]);
    }
    return absList;
  }

  private int[][] generateRandomPoints(int num) {
    int[][] points = new int[num][2];

    for (int i = 0; i < num; i++) {
      points[i][0] = (int) (Math.random() * getImageWidth());
      points[i][1] = (int) (Math.random() * getImageHeight());
    }
    return points;
  }

}