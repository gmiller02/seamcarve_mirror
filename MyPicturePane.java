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
	private int[][] _dirsArray;
	private int[][] _costArray;
	private Color pixColor;
	private int[] _seam;
	private Color n1;
	private Color n2;
	private Color n3;
	private Color n4;
	private Color n5;
	private Color n6;
	private Color n7;
	private Color n8;



	/**
	 * The constructor accepts an image filename as a String and passes
	 * it to the superclass for displaying and manipulation.
	 *
	 * @param pane
	 * @param filename
	 */
	public MyPicturePane(BorderPane pane, String filename) {
		super(pane, filename);
		_valuesArray = new int [getPicHeight()][getPicWidth()];


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


		_costArray[getPicHeight() - 1][getPicWidth()] = _valuesArray[getPicHeight() - 1][getPicWidth()];

		for (int row = getPicHeight() - 2; row >= 0; row--) {
			for (int col = 0; col <= getPicWidth() - 1; col++) {
				_costArray[row][col] = _valuesArray[row][col] + Math.min(_costArray[row + 1][col -1], _costArray[row + 1][col], _costArray[row + 1][col + 1]);
				// directions: if min x, directions =
			}
		}

		int min_col = argMin(_costArray[0][0]);

		int[] _seam = new int[0];

		_seam[0] = min_col;

		for (int row = 0; row > getPicHeight() - 2; row++) {
			_seam[row + 1] = _seam[row] + _dirsArray[row][_seam[row]];
		}

		return _seam;
	}

	private void caculateValues() {
		for (int row=0; row< getPicHeight(); row++) {
			for (int col = 0; col < getPicWidth(); col++) {

				if (col != 0 || col != getPicWidth()) {
					_valuesArray[row][col] = this.middleColumns(row, col);
				}

				if (col == 0) {
					_valuesArray[row][col] = this.leftColumn(row, col);
				}

				if (col == getPicWidth()) {
					_valuesArray[row][col] = this.endColumn(row, col);
				}

			}
		}
	}

	public int middleColumns(int row, int col) {
				pixColor = getPixelColor(row, col);
				int currRed = getColorRed(pixColor);
				int currBlue = getColorBlue(pixColor);
				int currGreen = getColorGreen(pixColor);
				int total = 0;

				if (row == getPicHeight()) { // middle bottom
					Color n1 = getPixelColor(row, col - 1);
					int redn1 = getColorRed(n1);
					int greenn1 = getColorGreen(n1);
					int bluen1 = getColorBlue(n1);
					int redDiff1 = currRed - redn1;
					int greenDiff1 = currGreen - greenn1;
					int blueDiff1 = currBlue - bluen1;
					int sumof1 = redDiff1 + blueDiff1 + greenDiff1;

					Color n2 = getPixelColor(row, col + 1);
					int redn2 = getColorRed(n2);
					int greenn2 = getColorGreen(n2);
					int bluen2 = getColorBlue(n2);
					int redDiff2 = currRed - redn2;
					int greenDiff2 = currGreen - greenn2;
					int blueDiff2 = currBlue - bluen2;
					int sumof2 = redDiff2 + blueDiff2 + greenDiff2;

					Color n3 = getPixelColor(row + 1, col);
					int redn3 = getColorRed(n3);
					int greenn3 = getColorGreen(n3);
					int bluen3 = getColorBlue(n3);
					int redDiff3 = currRed - redn3;
					int greenDiff3 = currGreen - greenn3;
					int blueDiff3 = currBlue - bluen3;
					int sumof3 = redDiff3 + blueDiff3 + greenDiff3;

					Color n4 = getPixelColor(row + 1, col - 1);
					int redn4 = getColorRed(n4);
					int greenn4 = getColorGreen(n4);
					int bluen4 = getColorBlue(n4);
					int redDiff4 = currRed - redn4;
					int greenDiff4 = currGreen - greenn4;
					int blueDiff4 = currBlue - bluen4;
					int sumof4 = redDiff4 + blueDiff4 + greenDiff4;

					Color n5 = getPixelColor(row + 1, col + 1);
					int redn5 = getColorRed(n5);
					int greenn5 = getColorGreen(n5);
					int bluen5 = getColorBlue(n5);
					int redDiff5 = currRed - redn5;
					int greenDiff5 = currGreen - greenn5;
					int blueDiff5 = currBlue - bluen5;
					int sumof5 = redDiff5 + blueDiff5 + greenDiff5;

					total = sumof1 + sumof2 + sumof3 + sumof4 + sumof5;

				}

				else if (row == 0) { // top middle
					Color n1 = getPixelColor(row, col - 1);
					int redn1 = getColorRed(n1);
					int greenn1 = getColorGreen(n1);
					int bluen1 = getColorBlue(n1);
					int redDiff1 = currRed - redn1;
					int greenDiff1 = currGreen - greenn1;
					int blueDiff1 = currBlue - bluen1;
					int sumof1 = redDiff1 + blueDiff1 + greenDiff1;

					Color n2 = getPixelColor(row, col + 1);
					int redn2 = getColorRed(n2);
					int greenn2 = getColorGreen(n2);
					int bluen2 = getColorBlue(n2);
					int redDiff2 = currRed - redn2;
					int greenDiff2 = currGreen - greenn2;
					int blueDiff2 = currBlue - bluen2;
					int sumof2 = redDiff2 + blueDiff2 + greenDiff2;

					Color n3 = getPixelColor(row - 1, col);
					int redn3 = getColorRed(n3);
					int greenn3 = getColorGreen(n3);
					int bluen3 = getColorBlue(n3);
					int redDiff3 = currRed - redn3;
					int greenDiff3 = currGreen - greenn3;
					int blueDiff3 = currBlue - bluen3;
					int sumof3 = redDiff3 + blueDiff3 + greenDiff3;

					Color n4 = getPixelColor(row - 1, col - 1);
					int redn4 = getColorRed(n4);
					int greenn4 = getColorGreen(n4);
					int bluen4 = getColorBlue(n4);
					int redDiff4 = currRed - redn4;
					int greenDiff4 = currGreen - greenn4;
					int blueDiff4 = currBlue - bluen4;
					int sumof4 = redDiff4 + blueDiff4 + greenDiff4;

					Color n5 = getPixelColor(row + 1, col + 1);
					int redn5 = getColorRed(n5);
					int greenn5 = getColorGreen(n5);
					int bluen5 = getColorBlue(n5);
					int redDiff5 = currRed - redn5;
					int greenDiff5 = currGreen - greenn5;
					int blueDiff5 = currBlue - bluen5;
					int sumof5 = redDiff5 + blueDiff5 + greenDiff5;

					total = sumof1 + sumof2 + sumof3 + sumof4 + sumof5;
				}

				else  { // middle middle
					Color n1 = getPixelColor(row - 1, col - 1);
					int redn1 = getColorRed(n1);
					int greenn1 = getColorGreen(n1);
					int bluen1 = getColorBlue(n1);
					int redDiff1 = currRed - redn1;
					int greenDiff1 = currGreen - greenn1;
					int blueDiff1 = currBlue - bluen1;
					int sumof1 = redDiff1 + blueDiff1 + greenDiff1;

					Color n2 = getPixelColor(row - 1, col);
					int redn2 = getColorRed(n2);
					int greenn2 = getColorGreen(n2);
					int bluen2 = getColorBlue(n2);
					int redDiff2 = currRed - redn2;
					int greenDiff2 = currGreen - greenn2;
					int blueDiff2 = currBlue - bluen2;
					int sumof2 = redDiff2 + blueDiff2 + greenDiff2;

					Color n3 = getPixelColor(row + 1, col + 1);
					int redn3 = getColorRed(n3);
					int greenn3 = getColorGreen(n3);
					int bluen3 = getColorBlue(n3);
					int redDiff3 = currRed - redn3;
					int greenDiff3 = currGreen - greenn3;
					int blueDiff3 = currBlue - bluen3;
					int sumof3 = redDiff3 + blueDiff3 + greenDiff3;

					Color n4 = getPixelColor(row, col - 1);
					int redn4 = getColorRed(n4);
					int greenn4 = getColorGreen(n4);
					int bluen4 = getColorBlue(n4);
					int redDiff4 = currRed - redn4;
					int greenDiff4 = currGreen - greenn4;
					int blueDiff4 = currBlue - bluen4;
					int sumof4 = redDiff4 + blueDiff4 + greenDiff4;

					Color n5 = getPixelColor(row, col + 1);
					int redn5 = getColorRed(n5);
					int greenn5 = getColorGreen(n5);
					int bluen5 = getColorBlue(n5);
					int redDiff5 = currRed - redn5;
					int greenDiff5 = currGreen - greenn5;
					int blueDiff5 = currBlue - bluen5;
					int sumof5 = redDiff5 + blueDiff5 + greenDiff5;

					Color n6 = getPixelColor(row - 1, col + 1);
					int redn6 = getColorRed(n6);
					int greenn6 = getColorGreen(n6);
					int bluen6 = getColorBlue(n6);
					int redDiff6 = currRed - redn6;
					int greenDiff6 = currGreen - greenn6;
					int blueDiff6 = currBlue - bluen6;
					int sumof6 = redDiff6 + blueDiff6 + greenDiff6;

					Color n7 = getPixelColor(row + 1, col);
					int redn7 = getColorRed(n7);
					int greenn7 = getColorGreen(n7);
					int bluen7 = getColorBlue(n7);
					int redDiff7 = currRed - redn7;
					int greenDiff7 = currGreen - greenn7;
					int blueDiff7 = currBlue - bluen7;
					int sumof7 = redDiff7 + blueDiff7 + greenDiff7;

					Color n8 = getPixelColor(row + 1, col + 1);
					int redn8 = getColorRed(n8);
					int greenn8 = getColorGreen(n8);
					int bluen8 = getColorBlue(n8);
					int redDiff8 = currRed - redn8;
					int greenDiff8 = currGreen - greenn8;
					int blueDiff8 = currBlue - bluen8;
					int sumof8 = redDiff8 + blueDiff8 + greenDiff8;

					total = sumof1 + sumof2 + sumof3 + sumof4 + sumof5 + sumof6 + sumof7 + sumof8;

				}


		return total;
	}

	public int leftColumn(int row, int col) {
				pixColor = getPixelColor(row, col);
				int currRed = getColorRed(pixColor);
				int currBlue = getColorBlue(pixColor);
				int currGreen = getColorGreen(pixColor);
				int total = 0;

				if (row == 0) { // left top
					Color n1 = getPixelColor(row, col + 1);
					int redn1 = getColorRed(n1);
					int greenn1 = getColorGreen(n1);
					int bluen1 = getColorBlue(n1);
					int redDiff1 = currRed - redn1;
					int greenDiff1 = currGreen - greenn1;
					int blueDiff1 = currBlue - bluen1;
					int sumof1 = redDiff1 + blueDiff1 + greenDiff1;

					Color n2 = getPixelColor(row + 1, col);
					int redn2 = getColorRed(n2);
					int greenn2 = getColorGreen(n2);
					int bluen2 = getColorBlue(n2);
					int redDiff2 = currRed - redn2;
					int greenDiff2 = currGreen - greenn2;
					int blueDiff2 = currBlue - bluen2;
					int sumof2 = redDiff2 + blueDiff2 + greenDiff2;

					Color n3 = getPixelColor(row + 1, col + 1);
					int redn3 = getColorRed(n3);
					int greenn3 = getColorGreen(n3);
					int bluen3 = getColorBlue(n3);
					int redDiff3 = currRed - redn3;
					int greenDiff3 = currGreen - greenn3;
					int blueDiff3 = currBlue - bluen3;
					int sumof3 = redDiff3 + blueDiff3 + greenDiff3;

					total = sumof1 + sumof2 + sumof3;
				}

				else if (row == getPicHeight()) { //left bottom
					Color n1 = getPixelColor(row, col - 1);
					int redn1 = getColorRed(n1);
					int greenn1 = getColorGreen(n1);
					int bluen1 = getColorBlue(n1);
					int redDiff1 = currRed - redn1;
					int greenDiff1 = currGreen - greenn1;
					int blueDiff1 = currBlue - bluen1;
					int sumof1 = redDiff1 + blueDiff1 + greenDiff1;

					Color n2 = getPixelColor(row + 1, col);
					int redn2 = getColorRed(n2);
					int greenn2 = getColorGreen(n2);
					int bluen2 = getColorBlue(n2);
					int redDiff2 = currRed - redn2;
					int greenDiff2 = currGreen - greenn2;
					int blueDiff2 = currBlue - bluen2;
					int sumof2 = redDiff2 + blueDiff2 + greenDiff2;

					Color n3 = getPixelColor(row + 1, col - 1);
					int redn3 = getColorRed(n3);
					int greenn3 = getColorGreen(n3);
					int bluen3 = getColorBlue(n3);
					int redDiff3 = currRed - redn3;
					int greenDiff3 = currGreen - greenn3;
					int blueDiff3 = currBlue - bluen3;
					int sumof3 = redDiff3 + blueDiff3 + greenDiff3;

					total = sumof1 + sumof2 + sumof3;
				}

				else { // left middle
					Color n1 = getPixelColor(row, col - 1);
					int redn1 = getColorRed(n1);
					int greenn1 = getColorGreen(n1);
					int bluen1 = getColorBlue(n1);
					int redDiff1 = currRed - redn1;
					int greenDiff1 = currGreen - greenn1;
					int blueDiff1 = currBlue - bluen1;
					int sumof1 = redDiff1 + blueDiff1 + greenDiff1;

					Color n2 = getPixelColor(row + 1, col);
					int redn2 = getColorRed(n2);
					int greenn2 = getColorGreen(n2);
					int bluen2 = getColorBlue(n2);
					int redDiff2 = currRed - redn2;
					int greenDiff2 = currGreen - greenn2;
					int blueDiff2 = currBlue - bluen2;
					int sumof2 = redDiff2 + blueDiff2 + greenDiff2;

					Color n3 = getPixelColor(row + 1, col - 1);
					int redn3 = getColorRed(n3);
					int greenn3 = getColorGreen(n3);
					int bluen3 = getColorBlue(n3);
					int redDiff3 = currRed - redn3;
					int greenDiff3 = currGreen - greenn3;
					int blueDiff3 = currBlue - bluen3;
					int sumof3 = redDiff3 + blueDiff3 + greenDiff3;

					Color n4 = getPixelColor(row + 1, col + 1);
					int redn4 = getColorRed(n4);
					int greenn4 = getColorGreen(n4);
					int bluen4 = getColorBlue(n4);
					int redDiff4 = currRed - redn4;
					int greenDiff4 = currGreen - greenn4;
					int blueDiff4 = currBlue - bluen4;
					int sumof4 = redDiff4 + blueDiff4 + greenDiff4;

					Color n5 = getPixelColor(row, col + 1);
					int redn5 = getColorRed(n5);
					int greenn5 = getColorGreen(n5);
					int bluen5 = getColorBlue(n5);
					int redDiff5 = currRed - redn5;
					int greenDiff5 = currGreen - greenn5;
					int blueDiff5 = currBlue - bluen5;
					int sumof5 = redDiff5 + blueDiff5 + greenDiff5;

					total = sumof1 + sumof2 + sumof3 + sumof4 + sumof5;

				}


				return total;


	}

	public int endColumn(int row, int col) {
				pixColor = getPixelColor(row, col);
				int currRed = getColorRed(pixColor);
				int currBlue = getColorBlue(pixColor);
				int currGreen = getColorGreen(pixColor);


					n1 = getPixelColor(row - 1, col - 1);
					int redn1 = getColorRed(n1);
					int greenn1 = getColorGreen(n1);
					int bluen1 = getColorBlue(n1);
					int redDiff1 = currRed - redn1;
					int greenDiff1 = currGreen - greenn1;
					int blueDiff1 = currBlue - bluen1;
		            int sumof1 = redDiff1 + blueDiff1 + greenDiff1;

					n2 = getPixelColor(row -1, col);
					int redn2 = getColorRed(n2);
					int greenn2 = getColorGreen(n2);
					int bluen2 = getColorBlue(n2);
		            int redDiff2 = currRed - redn2;
		            int greenDiff2 = currGreen - greenn2;
		            int blueDiff2 = currBlue - bluen2;
		            int sumof2 = redDiff1 + blueDiff1 + greenDiff1;

		            int sumDiff = sumof1 + sumof2;

		            return sumDiff;
	}

	public int argMin(int row) {
		int value = Integer.MAX_VALUE;
		int index = 0;
		for (int i = 0; i < getPicWidth(); i++) {
				if (_valuesArray[row][i] < value) {
					value = _valuesArray[row][i];
					index = i;
				}
		}
		return index;
	}


}
