/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/nBox/data/TriangleData.java /bibeans_root/4 2016/02/02 22:50:21 krajeswa Exp $ */

/* Copyright (c) 2014, 2015, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    jramanat    04/03/14 - Creation
 */

package oracle.adfdemo.view.feature.rich.nBox.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/nBox/data/TriangleData.java /bibeans_root/4 2016/02/02 22:50:21 krajeswa Exp $
 *  @author  jramanat
 *  @since   release specific (what release of product did this appear in)
 */

public class TriangleData {
  public static List<Node> getData(int nodes, int rows, int columns) {
    int[] rowCounts = new int[rows];
    int[] colCounts = new int[columns];
    for (int i = 0; i < rowCounts.length; i++) {
      rowCounts[i] = (int) (triangleArea((i + 1.0) / rows) * nodes);
    }
    for (int i = 0; i < colCounts.length; i++) {
      colCounts[i] = (int) (triangleArea((i + 1.0) / columns) * nodes);
    }
    for (int i = rowCounts.length - 1; i > 0; i--) {
      rowCounts[i] -= rowCounts[i - 1];
    }
    for (int i = colCounts.length - 1; i > 0; i--) {
      colCounts[i] -= colCounts[i - 1];
    }
    int[][] cellCounts = new int[rows][columns];
    for (int r = 0; r < cellCounts.length; r++) {
      for (int c = 0; c < cellCounts[r].length; c++) {
        cellCounts[r][c] = (int) ((rowCounts[r] * 1.0 / nodes) * colCounts[c]);
      }
    }
    boolean done = false;
    while (!done) {
      done = true;
      int r = findIncompleteRow(cellCounts, rowCounts, colCounts);
      int c = findIncompleteColumn(cellCounts, rowCounts, colCounts);
      if (r != -1 && c != -1) {
        done = false;
        cellCounts[r][c]++;
      }
    }
    List<Node> data = new ArrayList<Node>();
    int n = 0;
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < columns; c++) {
        int limit = n + cellCounts[r][c];
        while (n < limit) {
          data.add(new Node(n, r, c));
          n++;
        }
      }
    }
    return data;
  }

  private static double triangleArea(double num) {
    if (num <= 0.5) {
      return 2 * num * num;
    }
    else {
      return 1 - triangleArea(1 - num);
    }
  }

  private static int findIncompleteRow(int[][] cellCounts, int[] rowCounts, int[] colCounts) {
    for (int r = rowCounts.length - 1; r >= 0; r--) {
      int total = 0;
      for (int c = 0; c < colCounts.length; c++) {
        total += cellCounts[r][c];
      }
      if (total < rowCounts[r]) {
        return r;
      }
    }
    return -1;
  }

  private static int findIncompleteColumn(int[][] cellCounts, int[] rowCounts, int[] colCounts) {
    for (int c = colCounts.length - 1; c >= 0; c--) {
      int total = 0;
      for (int r = 0; r < rowCounts.length; r++) {
        total += cellCounts[r][c];
      }
      if (total < colCounts[c]) {
        return c;
      }
    }
    return -1;
  }

  public static class Node extends HashMap<String, Object> {
    private static final String[] NAMES = {
      "Nina Evans", "Diana Lorentz", "Lucy Whalen", "Laura Bissot", "Susan Higgins", "Kelly Sullivan", "Julia Chen",
      "Alaina Walsh", "Nancy Green", "Steven King", "Alex Hunold", "Curtis Davies", "James Marlow", "Jon Wu",
      "Dan Raphaely", "Simon Austin", "David Yong"
    };
    private static final int[] IMAGES = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16, 17, 18 };
    private static final String[] JOBS = {
      "Product Management Director", "Principal Product Manager", "Senior Product Manager",
      "Senior Documentation Manager", "Principal Technical Writer",
      "Marketing Director", "Principal Marketing Manager", "Senior Marketing Manager", 
      "Vice President", "Software Development Senior Director", "Architect", "Principal Software Engineer", "Senior Software Engineer"
    };
    private static final String[] DEPARTMENTS = {
      "Product Management", "Product Management", "Product Management", 
      "Documentation", "Documentation", 
      "Marketing", "Marketing", "Marketing", 
      "Development", "Development", "Development", "Development", "Development"
    };
    private static final String[] ROLES = {
      "Manager", "Individual Contributor", "Individual Contributor", 
      "Manager", "Individual Contributor", 
      "Manager", "Individual Contributor", "Individual Contributor", 
      "Manager", "Manager", "Individual Contributor", "Individual Contributor", "Individual Contributor"
    };
    private static final int[] EXPERIENCE = {
      5, 1, 7,
      4, 9, 
      8, 2, 4,
      3, 7, 2, 2, 5
    };
    private static final boolean[] REMOTE = {
      true, false, false,
      false, false,
      false, true, false,
      false, false, false, true, true
    };

    Node(int index, int row, int column) {
      put("index", index);
      put("row", row);
      put("column", column);
      put("name", NAMES[index % NAMES.length] + " " + (index / NAMES.length + 1));
      put("image", IMAGES[index % IMAGES.length] + ".png");
      put("job", JOBS[index % JOBS.length]);
      put("department", DEPARTMENTS[index % DEPARTMENTS.length]);
      put("role", ROLES[index % ROLES.length]);
      put("experience",  EXPERIENCE[index % EXPERIENCE.length]);
      put("remote", REMOTE[index % REMOTE.length]);
    }
  }

}
