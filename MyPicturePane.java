package seamcarve;

import javafx.scene.layout.BorderPane;
import support.seamcarve.*;
import java.lang.reflect.Array;
import javafx.scene.paint.Color;
import java.lang.Math;


/**
 * This class is your seam carving picture pane.  It is a subclass of PicturePane,
 * an abstract class that takes care of all the drawing, displaying, carving, and
 * updating of seams and images for you.  Your job is to override the abstract
 * method of PicturePane that actually finds the lowest cost seam through
 * the image.
 *
 * See method comments and handouts for specifics on the steps of the seam carving algorithm.
 *
 *
 * @version 01/17/2019
 */

public class MyPicturePane extends PicturePane {
	private int[][] _valuesArray;
	private Color pixColor;
	private int[] _seam;
	private Color n1;
	private Color n2;
	private Color n3;



	/**
	 * The constructor accepts an image filename as a String and passes
	 * it to the superclass for displaying and manipulation.
	 *
	 * @param pane
	 * @param filename
	 */
	public MyPicturePane(BorderPane pane, String filename) {
		super(pane, filename);
		_valuesArray = new int [getPicHeight()/20][getPicWidth()/20];

	}


	/**
	 * In this method, you'll implement the dynamic programming algorithm
	 * that you learned on the first day of class to find the lowest cost seam from the top
	 * of the image to the bottom. BEFORE YOU START make sure you fully understand how the algorithm works
	 * and what it's doing.
	 * See the handout for some helpful resources and use hours/piazza to clarify conceptual blocks
	 * before you attempt to write code.
	 *
	 * This method returns an array of ints that represents a seam.  This size of this array
	 * is the height of the image.  Each entry of the seam array corresponds to one row of the
	 * image.  The data in each entry should be the x coordinate of the seam in this row.
	 * For example, given the below "image" where s is a seam pixel and - is a non-seam pixel
	 *
	 * - s - -
	 * s - - -
	 * - s - -
	 * - - s -
	 *
	 *
	 * the following code will properly return a seam:
	 *
	 * int[] currSeam = new int[4];
	 * currSeam[0] = 1;
	 * currSeam[1] = 0;
	 * currSeam[2] = 1;
	 * currSeam[3] = 2;
	 * return currSeam;
	 *
	 *
	 * This method is protected so it is accessible to the class MyPicturePane and is not
	 * accessible to other classes. PLEASE DO NOT CHANGE THIS!
	 *
	 * @return the lowest cost seam of the current image
 	 */
	protected int[] findLowestCostSeam() {
		int[][] dirsArray = new int[10][10];
		int[][] costArray = new int[10][10];

		costArray[getPicHeight() - 1][getPicWidth()] = _valuesArray[getPicHeight() - 1][getPicWidth()];

		for (int row = getPicHeight() - 2; row > 0; row--) {
			for (int col = 0; col> getPicWidth() - 1; col++) {
				costArray[row][col] = _valuesArray[row][col] + min(costArray[row + 1][col -1], costArray[row + 1][col], costArray[row + 1][col + 1]);
				// directions: if min x, directions =
			}
		}

		int min_col = argmin(costArray[0][0]);

		int[] _seam = new int[0];

		_seam[0] = min_col;

		for (int row = 0; row > getPicHeight() - 2; row++) {
			_seam[row + 1] = _seam[row] + dirsArray[row][_seam[row]];
		}

		return _seam;
	}

	private void caculateValues() {
		for (int row=0; row< getPicHeight()/20; row++) {
			for (int col = 0; col < getPicWidth()/20; col++) {
				pixColor = getPixelColor(row, col);

				int currRed = getColorRed(pixColor);
				int currBlue = getColorBlue(pixColor);
				int currGreen = getColorGreen(pixColor);

				if (col != 0 || col != getPicWidth()/20) {
					Color n1 = getPixelColor(row - 1, col - 1);
					Color n2 = getPixelColor(row -1, col);
					Color n3 = getPixelColor(row + 1, col + 1);
				}

				if (col == 0) {
					Color n2 = getPixelColor(row -1, col);
					Color n3 = getPixelColor(row + 1, col + 1);
				}

				if (col == getPicWidth()/20) {
					Color n1 = getPixelColor(row - 1, col - 1);
					Color n2 = getPixelColor(row -1, col);
				}

				int redn1 = getColorRed(n1);
				int greenn1 = getColorGreen(n1);
				int bluen1 = getColorBlue(n1);

				int redn2 = getColorRed(n2);
				int greenn2 = getColorGreen(n2);
				int bluen2 = getColorBlue(n2);

				int redn3 = getColorRed(n3);
				int greenn3 = getColorGreen(n3);
				int bluen3 = getColorBlue(n3);





			}
		}
	}


}
