package seamcarve;

import javafx.scene.layout.BorderPane;
import support.seamcarve.*;
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
 * Here are my comments on MyPicturePane: This class implements the seamcarve method that I made. It caculates
 * the values, costs, and directions array and uses these arrays to carve the seams through my pictures.
 *
 *
 * @version 01/17/2019
 */

public class MyPicturePane extends PicturePane {
	private double[][] _valuesArray;
	private int[][] _dirsArray;
	private double[][] _costArray;
	private Color pixColor;



	/**
	 * The constructor accepts an image filename as a String and passes
	 * it to the superclass for displaying and manipulation.
	 *
	 * @param pane
	 * @param filename
	 */
	public MyPicturePane(BorderPane pane, String filename) {
		super(pane, filename);

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
		_costArray = new double [getPicHeight()][getPicWidth()];
		_dirsArray = new int [getPicHeight()][getPicWidth()];
		_valuesArray = new double [getPicHeight()][getPicWidth()];
		this.caculateValues();

		_costArray[getPicHeight() - 1][getPicWidth() - 1] = _valuesArray[getPicHeight() - 1][getPicWidth() - 1];

		for (int row = getPicHeight() - 2; row >= 0; row--) {
			for (int col = 0; col <= getPicWidth() - 1; col++) {


				if (col == 0) {
					double min = Math.min(_costArray[row + 1][col], _costArray[row + 1][col + 1]);
					_costArray[row][col] = _valuesArray[row][col] + min;

					if(Math.min(_costArray[row + 1][col], _costArray[row + 1][col + 1]) == _costArray[row + 1][col]) {
						_dirsArray[row][col] = 0;
					}
					else {
						_dirsArray[row][col] = 1;
					}
				}

				else if (col == getPicWidth() - 1) {
					double min = Math.min(_costArray[row + 1][col], _costArray[row + 1][col - 1]);
					_costArray[row][col] = _valuesArray[row][col] + min;

					if (Math.min(_costArray[row + 1][col], _costArray[row + 1][col - 1]) == _costArray[row + 1][col]) {
						_dirsArray[row][col] = 0;
					}
					else {
						_dirsArray[row][col] = -1;
					}
				}

				else {
					double min = Math.min(_costArray[row + 1][col], Math.min(_costArray[row + 1][col - 1], _costArray[row + 1][col + 1]));
					_costArray[row][col] = _valuesArray[row][col] + min;

					if (Math.min(_costArray[row + 1][col], Math.min(_costArray[row + 1][col - 1], _costArray[row + 1][col + 1])) == _costArray[row + 1][col]) {
						_dirsArray[row][col] = 0;
					}
					else if (Math.min(_costArray[row + 1][col], Math.min(_costArray[row + 1][col - 1], _costArray[row + 1][col + 1])) == _costArray[row + 1][col - 1]) {
						_dirsArray[row][col] = -1;

					}
					else {
						_dirsArray[row][col] = 1;
					}
				}


			}
		}

		int min_col = argMin(_costArray[0]);

		int[] seam = new int[getPicHeight()];

		seam[0] = min_col;

		for (int row = 0; row <= getPicHeight() - 2; row++) {
			seam[row + 1] = seam[row] + _dirsArray[row][seam[row]];
		}

		return seam;
	}

	/**
	 *
	 * caculateValues() = void, no output
	 * Purpose: This method fills in my values array with the neighbors that are caculated in my three helper methods, which are
	 * expanded upon below.
	 */

	private void caculateValues() {
		for (int row=0; row<= getPicHeight() - 1; row++) { // loops through values array
			for (int col = 0; col <= getPicWidth() - 1; col++) {

				if (col != 0 && col != getPicWidth() - 1) {
					_valuesArray[row][col] = this.middleColumns(row, col); // fill in the middle columns with neighbors
				}

				if (col == 0) {
					_valuesArray[row][col] = this.leftColumn(row, col); // fills in the leftmost columns with neighbors
				}

				if (col == getPicWidth() - 1) {
					_valuesArray[row][col] = this.rightColumns(row, col); // fills in the rightmost columns with neighbors
				}
			}
		}
	}

	/**
	 *
	 * middleColumns(int row, int col): returns int total
	 *
	 * Purpose: This method caculates the values of the neighbors relevant to the current pixel, for all of the middle
	 * rows of an image.
	 *
	 *
	 * @param row
	 * @param col
	 * @return
	 */

	public int middleColumns(int row, int col) {
		pixColor = getPixelColor(row, col); // color of current pixel
		int currRed = getColorRed(pixColor); // RGB values of current pixel
		int currBlue = getColorBlue(pixColor);
		int currGreen = getColorGreen(pixColor);
		int total = 0; // initialize total values to zero for now

		// below, all of the possible neighbors of the current pixel are caculated

		if (row == getPicHeight() - 1) { // middle bottom
			Color n1 = getPixelColor(row, col - 1); // get color of the middle bottom pixel neighbor
			int redn1 = getColorRed(n1); // get the RGB values of the neighbor
			int greenn1 = getColorGreen(n1);
			int bluen1 = getColorBlue(n1);
			int redDiff1 = currRed - redn1;
			int greenDiff1 = currGreen - greenn1; // caculate the differences of the current RBG values and the neighbor RGB values
			int blueDiff1 = currBlue - bluen1;
			int sumof1 = Math.abs(redDiff1 + blueDiff1 + greenDiff1); // get the sum of all of the differences in the neighbor values

			// below, I repeat the process for every single neighbor of every single possible pixel

			Color n2 = getPixelColor(row, col + 1);
			int redn2 = getColorRed(n2);
			int greenn2 = getColorGreen(n2);
			int bluen2 = getColorBlue(n2);
			int redDiff2 = currRed - redn2;
			int greenDiff2 = currGreen - greenn2;
			int blueDiff2 = currBlue - bluen2;
			int sumof2 = Math.abs(redDiff2 + blueDiff2 + greenDiff2);

			Color n3 = getPixelColor(row - 1, col);
			int redn3 = getColorRed(n3);
			int greenn3 = getColorGreen(n3);
			int bluen3 = getColorBlue(n3);
			int redDiff3 = currRed - redn3;
			int greenDiff3 = currGreen - greenn3;
			int blueDiff3 = currBlue - bluen3;
			int sumof3 = Math.abs(redDiff3 + blueDiff3 + greenDiff3);

			Color n4 = getPixelColor(row - 1, col + 1);
			int redn4 = getColorRed(n4);
			int greenn4 = getColorGreen(n4);
			int bluen4 = getColorBlue(n4);
			int redDiff4 = currRed - redn4;
			int greenDiff4 = currGreen - greenn4;
			int blueDiff4 = currBlue - bluen4;
			int sumof4 = Math.abs(redDiff4 + blueDiff4 + greenDiff4);

			Color n5 = getPixelColor(row - 1, col - 1);
			int redn5 = getColorRed(n5);
			int greenn5 = getColorGreen(n5);
			int bluen5 = getColorBlue(n5);
			int redDiff5 = currRed - redn5;
			int greenDiff5 = currGreen - greenn5;
			int blueDiff5 = currBlue - bluen5;
			int sumof5 = Math.abs(redDiff5 + blueDiff5 + greenDiff5);

			total = sumof1 + sumof2 + sumof3 + sumof4 + sumof5; // set total to the sum of all of the sums of the differences in the RGB values of the neighbors

		}

		else if (row == 0) { // top middle
			Color n1 = getPixelColor(row, col - 1);
			int redn1 = getColorRed(n1);
			int greenn1 = getColorGreen(n1);
			int bluen1 = getColorBlue(n1);
			int redDiff1 = currRed - redn1;
			int greenDiff1 = currGreen - greenn1;
			int blueDiff1 = currBlue - bluen1;
			int sumof1 = Math.abs(redDiff1 + blueDiff1 + greenDiff1);

			Color n2 = getPixelColor(row, col + 1);
			int redn2 = getColorRed(n2);
			int greenn2 = getColorGreen(n2);
			int bluen2 = getColorBlue(n2);
			int redDiff2 = currRed - redn2;
			int greenDiff2 = currGreen - greenn2;
			int blueDiff2 = currBlue - bluen2;
			int sumof2 = Math.abs(redDiff2 + blueDiff2 + greenDiff2);

			Color n3 = getPixelColor(row + 1, col);
			int redn3 = getColorRed(n3);
			int greenn3 = getColorGreen(n3);
			int bluen3 = getColorBlue(n3);
			int redDiff3 = currRed - redn3;
			int greenDiff3 = currGreen - greenn3;
			int blueDiff3 = currBlue - bluen3;
			int sumof3 = Math.abs(redDiff3 + blueDiff3 + greenDiff3);

			Color n4 = getPixelColor(row + 1, col - 1);
			int redn4 = getColorRed(n4);
			int greenn4 = getColorGreen(n4);
			int bluen4 = getColorBlue(n4);
			int redDiff4 = currRed - redn4;
			int greenDiff4 = currGreen - greenn4;
			int blueDiff4 = currBlue - bluen4;
			int sumof4 = Math.abs(redDiff4 + blueDiff4 + greenDiff4);

			Color n5 = getPixelColor(row + 1, col + 1);
			int redn5 = getColorRed(n5);
			int greenn5 = getColorGreen(n5);
			int bluen5 = getColorBlue(n5);
			int redDiff5 = currRed - redn5;
			int greenDiff5 = currGreen - greenn5;
			int blueDiff5 = currBlue - bluen5;
			int sumof5 = Math.abs(redDiff5 + blueDiff5 + greenDiff5);

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
			int sumof1 = Math.abs(redDiff1 + blueDiff1 + greenDiff1);

			Color n2 = getPixelColor(row - 1, col);
			int redn2 = getColorRed(n2);
			int greenn2 = getColorGreen(n2);
			int bluen2 = getColorBlue(n2);
			int redDiff2 = currRed - redn2;
			int greenDiff2 = currGreen - greenn2;
			int blueDiff2 = currBlue - bluen2;
			int sumof2 = Math.abs(redDiff2 + blueDiff2 + greenDiff2);

			Color n3 = getPixelColor(row + 1, col + 1);
			int redn3 = getColorRed(n3);
			int greenn3 = getColorGreen(n3);
			int bluen3 = getColorBlue(n3);
			int redDiff3 = currRed - redn3;
			int greenDiff3 = currGreen - greenn3;
			int blueDiff3 = currBlue - bluen3;
			int sumof3 = Math.abs(redDiff3 + blueDiff3 + greenDiff3);

			Color n4 = getPixelColor(row, col - 1);
			int redn4 = getColorRed(n4);
			int greenn4 = getColorGreen(n4);
			int bluen4 = getColorBlue(n4);
			int redDiff4 = currRed - redn4;
			int greenDiff4 = currGreen - greenn4;
			int blueDiff4 = currBlue - bluen4;
			int sumof4 = Math.abs(redDiff4 + blueDiff4 + greenDiff4);

			Color n5 = getPixelColor(row, col + 1);
			int redn5 = getColorRed(n5);
			int greenn5 = getColorGreen(n5);
			int bluen5 = getColorBlue(n5);
			int redDiff5 = currRed - redn5;
			int greenDiff5 = currGreen - greenn5;
			int blueDiff5 = currBlue - bluen5;
			int sumof5 = Math.abs(redDiff5 + blueDiff5 + greenDiff5);

			Color n6 = getPixelColor(row - 1, col + 1);
			int redn6 = getColorRed(n6);
			int greenn6 = getColorGreen(n6);
			int bluen6 = getColorBlue(n6);
			int redDiff6 = currRed - redn6;
			int greenDiff6 = currGreen - greenn6;
			int blueDiff6 = currBlue - bluen6;
			int sumof6 = Math.abs(redDiff6 + blueDiff6 + greenDiff6);

			Color n7 = getPixelColor(row + 1, col);
			int redn7 = getColorRed(n7);
			int greenn7 = getColorGreen(n7);
			int bluen7 = getColorBlue(n7);
			int redDiff7 = currRed - redn7;
			int greenDiff7 = currGreen - greenn7;
			int blueDiff7 = currBlue - bluen7;
			int sumof7 = Math.abs(redDiff7 + blueDiff7 + greenDiff7);

			Color n8 = getPixelColor(row + 1, col + 1);
			int redn8 = getColorRed(n8);
			int greenn8 = getColorGreen(n8);
			int bluen8 = getColorBlue(n8);
			int redDiff8 = currRed - redn8;
			int greenDiff8 = currGreen - greenn8;
			int blueDiff8 = currBlue - bluen8;
			int sumof8 = Math.abs(redDiff8 + blueDiff8 + greenDiff8);

			total = sumof1 + sumof2 + sumof3 + sumof4 + sumof5 + sumof6 + sumof7 + sumof8;

		}


		return total; // returns the total value based on which if statement is accesssed
	}

	/**
	 * leftColumns(int row, int col): returns int total
	 *
	 * Purpose: This method caculates the values of the neighbors relevant to the current pixel, for the leftmost
	 * columns of the image.
	 *
	 * @param row
	 * @param col
	 * @return
	 */

	public int leftColumn(int row, int col) {
		pixColor = getPixelColor(row, col);
		int currRed = getColorRed(pixColor);
		int currBlue = getColorBlue(pixColor);
		int currGreen = getColorGreen(pixColor);
		int total = 0;

		// below, I do not change my strategy for caculating the values of the neighbors. All of the operations
		// are the same as in my middleColumns method.

		if (row == 0) { // left top
			Color n1 = getPixelColor(row, col + 1);
			int redn1 = getColorRed(n1);
			int greenn1 = getColorGreen(n1);
			int bluen1 = getColorBlue(n1);
			int redDiff1 = currRed - redn1;
			int greenDiff1 = currGreen - greenn1;
			int blueDiff1 = currBlue - bluen1;
			int sumof1 = Math.abs(redDiff1 + blueDiff1 + greenDiff1);

			Color n2 = getPixelColor(row + 1, col);
			int redn2 = getColorRed(n2);
			int greenn2 = getColorGreen(n2);
			int bluen2 = getColorBlue(n2);
			int redDiff2 = currRed - redn2;
			int greenDiff2 = currGreen - greenn2;
			int blueDiff2 = currBlue - bluen2;
			int sumof2 = Math.abs(redDiff2 + blueDiff2 + greenDiff2);

			Color n3 = getPixelColor(row + 1, col + 1);
			int redn3 = getColorRed(n3);
			int greenn3 = getColorGreen(n3);
			int bluen3 = getColorBlue(n3);
			int redDiff3 = currRed - redn3;
			int greenDiff3 = currGreen - greenn3;
			int blueDiff3 = currBlue - bluen3;
			int sumof3 = Math.abs(redDiff3 + blueDiff3 + greenDiff3);

			total = sumof1 + sumof2 + sumof3;
		}

		else if (row == getPicHeight() - 1) { //left bottom
			Color n1 = getPixelColor(row - 1, col);
			int redn1 = getColorRed(n1);
			int greenn1 = getColorGreen(n1);
			int bluen1 = getColorBlue(n1);
			int redDiff1 = currRed - redn1;
			int greenDiff1 = currGreen - greenn1;
			int blueDiff1 = currBlue - bluen1;
			int sumof1 = Math.abs(redDiff1 + blueDiff1 + greenDiff1);

			Color n2 = getPixelColor(row, col + 1);
			int redn2 = getColorRed(n2);
			int greenn2 = getColorGreen(n2);
			int bluen2 = getColorBlue(n2);
			int redDiff2 = currRed - redn2;
			int greenDiff2 = currGreen - greenn2;
			int blueDiff2 = currBlue - bluen2;
			int sumof2 = Math.abs(redDiff2 + blueDiff2 + greenDiff2);

			Color n3 = getPixelColor(row - 1, col + 1);
			int redn3 = getColorRed(n3);
			int greenn3 = getColorGreen(n3);
			int bluen3 = getColorBlue(n3);
			int redDiff3 = currRed - redn3;
			int greenDiff3 = currGreen - greenn3;
			int blueDiff3 = currBlue - bluen3;
			int sumof3 = Math.abs(redDiff3 + blueDiff3 + greenDiff3);

			total = sumof1 + sumof2 + sumof3;
		}

		else { // left middle
			Color n1 = getPixelColor(row - 1, col);
			int redn1 = getColorRed(n1);
			int greenn1 = getColorGreen(n1);
			int bluen1 = getColorBlue(n1);
			int redDiff1 = currRed - redn1;
			int greenDiff1 = currGreen - greenn1;
			int blueDiff1 = currBlue - bluen1;
			int sumof1 = Math.abs(redDiff1 + blueDiff1 + greenDiff1);

			Color n2 = getPixelColor(row + 1, col);
			int redn2 = getColorRed(n2);
			int greenn2 = getColorGreen(n2);
			int bluen2 = getColorBlue(n2);
			int redDiff2 = currRed - redn2;
			int greenDiff2 = currGreen - greenn2;
			int blueDiff2 = currBlue - bluen2;
			int sumof2 = Math.abs(redDiff2 + blueDiff2 + greenDiff2);

			Color n3 = getPixelColor(row + 1, col + 1);
			int redn3 = getColorRed(n3);
			int greenn3 = getColorGreen(n3);
			int bluen3 = getColorBlue(n3);
			int redDiff3 = currRed - redn3;
			int greenDiff3 = currGreen - greenn3;
			int blueDiff3 = currBlue - bluen3;
			int sumof3 = Math.abs(redDiff3 + blueDiff3 + greenDiff3);

			Color n4 = getPixelColor(row - 1, col + 1);
			int redn4 = getColorRed(n4);
			int greenn4 = getColorGreen(n4);
			int bluen4 = getColorBlue(n4);
			int redDiff4 = currRed - redn4;
			int greenDiff4 = currGreen - greenn4;
			int blueDiff4 = currBlue - bluen4;
			int sumof4 = Math.abs(redDiff4 + blueDiff4 + greenDiff4);

			Color n5 = getPixelColor(row, col + 1);
			int redn5 = getColorRed(n5);
			int greenn5 = getColorGreen(n5);
			int bluen5 = getColorBlue(n5);
			int redDiff5 = currRed - redn5;
			int greenDiff5 = currGreen - greenn5;
			int blueDiff5 = currBlue - bluen5;
			int sumof5 = Math.abs(redDiff5 + blueDiff5 + greenDiff5);

			total = sumof1 + sumof2 + sumof3 + sumof4 + sumof5;

		}

		return total;
	}

	/**
	 * rightColumns(int row, int col): returns int total
	 *
	 * Purpose: This method caculates the values of the neighbors relevant to the current pixel, for the rightmost
	 * columns of the image.
	 *
	 * @param row
	 * @param col
	 * @return
	 */

	public int rightColumns(int row, int col) {
		pixColor = getPixelColor(row, col);
		int currRed = getColorRed(pixColor);
		int currBlue = getColorBlue(pixColor);
		int currGreen = getColorGreen(pixColor);
		int total = 0;

		// Again, all of the operations and logic for this class is the same as the earlier two classes, but for
		// the rightmost columns.

		if (row == 0) { // top right
			Color n1 = getPixelColor(row + 1, col);
			int redn1 = getColorRed(n1);
			int greenn1 = getColorGreen(n1);
			int bluen1 = getColorBlue(n1);
			int redDiff1 = currRed - redn1;
			int greenDiff1 = currGreen - greenn1;
			int blueDiff1 = currBlue - bluen1;
			int sumof1 = Math.abs(redDiff1 + blueDiff1 + greenDiff1);

			Color n2 = getPixelColor(row, col - 1);
			int redn2 = getColorRed(n2);
			int greenn2 = getColorGreen(n2);
			int bluen2 = getColorBlue(n2);
			int redDiff2 = currRed - redn2;
			int greenDiff2 = currGreen - greenn2;
			int blueDiff2 = currBlue - bluen2;
			int sumof2 = Math.abs(redDiff2 + blueDiff2 + greenDiff2);

			Color n3 = getPixelColor(row + 1, col - 1);
			int redn3 = getColorRed(n3);
			int greenn3 = getColorGreen(n3);
			int bluen3 = getColorBlue(n3);
			int redDiff3 = currRed - redn3;
			int greenDiff3 = currGreen - greenn3;
			int blueDiff3 = currBlue - bluen3;
			int sumof3 = Math.abs(redDiff3 + blueDiff3 + greenDiff3);

			total = sumof1 + sumof2 + sumof3;
		}

		else if (row == getPicHeight() - 1) { // bottom right
			Color n1 = getPixelColor(row, col - 1);
			int redn1 = getColorRed(n1);
			int greenn1 = getColorGreen(n1);
			int bluen1 = getColorBlue(n1);
			int redDiff1 = currRed - redn1;
			int greenDiff1 = currGreen - greenn1;
			int blueDiff1 = currBlue - bluen1;
			int sumof1 = Math.abs(redDiff1 + blueDiff1 + greenDiff1);

			Color n2 = getPixelColor(row - 1, col);
			int redn2 = getColorRed(n2);
			int greenn2 = getColorGreen(n2);
			int bluen2 = getColorBlue(n2);
			int redDiff2 = currRed - redn2;
			int greenDiff2 = currGreen - greenn2;
			int blueDiff2 = currBlue - bluen2;
			int sumof2 = Math.abs(redDiff2 + blueDiff2 + greenDiff2);

			Color n3 = getPixelColor(row - 1, col - 1);
			int redn3 = getColorRed(n3);
			int greenn3 = getColorGreen(n3);
			int bluen3 = getColorBlue(n3);
			int redDiff3 = currRed - redn3;
			int greenDiff3 = currGreen - greenn3;
			int blueDiff3 = currBlue - bluen3;
			int sumof3 = Math.abs(redDiff3 + blueDiff3 + greenDiff3);

			total = sumof1 + sumof2 + sumof3;
		}

		else { //middle right
			Color n1 = getPixelColor(row, col - 1);
			int redn1 = getColorRed(n1);
			int greenn1 = getColorGreen(n1);
			int bluen1 = getColorBlue(n1);
			int redDiff1 = currRed - redn1;
			int greenDiff1 = currGreen - greenn1;
			int blueDiff1 = currBlue - bluen1;
			int sumof1 = Math.abs(redDiff1 + blueDiff1 + greenDiff1);

			Color n2 = getPixelColor(row - 1, col);
			int redn2 = getColorRed(n2);
			int greenn2 = getColorGreen(n2);
			int bluen2 = getColorBlue(n2);
			int redDiff2 = currRed - redn2;
			int greenDiff2 = currGreen - greenn2;
			int blueDiff2 = currBlue - bluen2;
			int sumof2 = Math.abs(redDiff2 + blueDiff2 + greenDiff2);

			Color n3 = getPixelColor(row + 1, col);
			int redn3 = getColorRed(n3);
			int greenn3 = getColorGreen(n3);
			int bluen3 = getColorBlue(n3);
			int redDiff3 = currRed - redn3;
			int greenDiff3 = currGreen - greenn3;
			int blueDiff3 = currBlue - bluen3;
			int sumof3 = Math.abs(redDiff3 + blueDiff3 + greenDiff3);


			Color n4 = getPixelColor(row - 1, col - 1);
			int redn4 = getColorRed(n4);
			int greenn4 = getColorGreen(n4);
			int bluen4 = getColorBlue(n4);
			int redDiff4 = currRed - redn4;
			int greenDiff4 = currGreen - greenn4;
			int blueDiff4 = currBlue - bluen4;
			int sumof4 = Math.abs(redDiff4 + blueDiff4 + greenDiff4);

			Color n5 = getPixelColor(row + 1, col - 1);
			int redn5 = getColorRed(n5);
			int greenn5 = getColorGreen(n5);
			int bluen5 = getColorBlue(n5);
			int redDiff5 = currRed - redn5;
			int greenDiff5 = currGreen - greenn5;
			int blueDiff5 = currBlue - bluen5;
			int sumof5 = Math.abs(redDiff5 + blueDiff5 + greenDiff5);

			total = sumof1 + sumof2 + sumof3 + sumof4 + sumof5;
		}


		return total;
	}

	/**
	 *
	 * argMin(double array): returns int index
	 *
	 * Purpose: returns the index of a function where the function values are minimized.
	 *
	 * @param array
	 * @return
	 */

	private int argMin(double [] array) {
		int length = array.length;
		int index = 0;
		double value = array[index];

		for (int i = 0; i < length - 1; i++) { // loops through array
				if (array[i] < value) { // if the array at i is less than the index, set the index value equal to i
					value = array[i];
					index = i;
				}
		}
		return index; // return minimum of function values
	}

}
