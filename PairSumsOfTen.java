/**
 * VIP Technical Exercise
 * 
 * Authors: Lydia Pitts Created: 04/03/2020
 * 
 * This program allows for an integer array to be passed in and will then: 
 * 1) Output all pairs (includes duplicates and the reversed ordered pairs) 
 * 2)Output unique pairs only once (removes the duplicates but includes the 
 * reversed ordered pairs) 
 * 3) Output the same combo pair only once (removes the reversed ordered pairs)
 * 
 * Overall this program (including the main function) runs in O(10n) time.
 */
public class PairSumsOfTen {

	/**
	 * findNumberDistrobution finds the number of times each number between 0-10
	 * (including 0 and 10) shows up in the array. This is stored in
	 * numDistrobution. The index of numDistrobution corresponds to the number from
	 * the argument array passed in, and the number within the array at each index
	 * corresponds to the number of times that number appears in the argument array.
	 * 
	 * for example: 
	 * array = {5, 5, 4, 5} 
	 * numDistrobution[5] = 3 
	 * numDistrobution[4] = 1
	 * numDistrobution[2] = 0 
	 * and so on.
	 * 
	 * This function runs in O(n) because it walks through the entire array once.
	 * 
	 * @param array = the initial integer array.
	 * @return numDistrobution = total time each number between 0-10 appears in
	 *         array.
	 */
	public static int[] findNumberDistrobution(int[] array) {
		int[] numDistrobution = new int[11];
		for (int i = 0; i < array.length; i++) {
			int number = array[i];
			if ((number >= 0) && (number <= 10)) {
				numDistrobution[number]++;
			}
		}
		return numDistrobution;
	}

	/**
	 * findAllPairs uses the number distribution array found and described in
	 * findNumberDistrobution. It uses these frequencies to create a 2D array with
	 * all all pairs, including duplicates and the reversed ordered pairs. It does
	 * this walking though each number 0 - 10 and finding the total number of pairs
	 * that specific number has, and adds the appropriate amount to to the allPairs
	 * array.
	 * 
	 * This runs in O(2n) time because the maximum number of pairs is 2n, and the
	 * combination of for/while loop only executes for the number of total pairs.
	 * 
	 * @param numDist = array returned from findNumberDistrobution. Stores
	 *                frequencies of numbers in array
	 * @param array   = the initial integer array, used to determine length of the
	 *                pairs array
	 * @return allPairs = array with all pairs, including duplicates and the
	 *         reversed ordered pairs
	 */
	public static int[][] findAllPairs(int[] numDist, int[] array) {
		int maxNumOfPairs = array.length * 2;
		int[][] allPairs = new int[maxNumOfPairs][2]; // 2d array with all the pairs; to return.
		int pairNumber = 0;
		for (int i = 0; i < numDist.length; i++) {
			int numOfI = numDist[i]; // The total times i shows up in the origional array.
			int numOf10SubI = numDist[10 - i]; // The total time 10-i shows up in the origional array.
			double totalPairsWithIFirst = numOfI * numOf10SubI;// the total number of pairs that start with i.
			if (i == 5) { // Five is a unique case, because it's compliment (10-i) is itself,
				totalPairsWithIFirst = numOfI * (numOf10SubI - 1);// so it needs a modified equation.
			}
			while (totalPairsWithIFirst > 0) { // adds the appropriate number of pairs that start with i to allPairs.
				allPairs[pairNumber][0] = i;
				allPairs[pairNumber][1] = 10 - i;
				totalPairsWithIFirst--;
				pairNumber++;
			}
		}
		return allPairs;
	}

	/**
	 * oneOfEachPair uses the array of all pairs found in findAllPair and returns
	 * the unique pairs. Showing the pairs only once (removes the duplicates but
	 * includes the reversed ordered pairs). pairStart keeps track of the starting
	 * number of each pair, and because allPairs is in order, it only has to keep
	 * track of start number at a time. When walking through allPairs, if it has
	 * already added the pair that starts with that number then it does not add it
	 * to pairs. If it has not encountered that pairing, it adds it to pairs.
	 * 
	 * This runs in O(2n) because it walks through allPairs once, which is length
	 * 2n.
	 * 
	 * @param allPairs = array with all pairs, including duplicates and the reversed
	 *                 ordered pairs
	 * @return pairs = pairs that occur only once (removes the duplicates but
	 *         includes the reversed ordered pairs)
	 */
	public static int[][] oneOfEachPair(int[][] allPairs) {
		int[][] pairs = new int[allPairs.length][2]; // 2d array with all the unique pairs; to return.
		int pairNumber = 0;
		int i = 0;
		int pairStart = 11;
		while ((i < allPairs.length) && ((allPairs[i][0] != 0) && (allPairs[i][1] != 0))) { // There will not always be
																							// 2n pairs, and allPairs is
																							// length 2n, so this while
																							// loop stops when it
																							// reaches empty pairs:
																							// ([0,0])
			int start = allPairs[i][0];
			if (start != pairStart) {
				pairs[pairNumber][0] = start;
				pairs[pairNumber][1] = allPairs[i][1];
				pairNumber++;
				pairStart = start;
			}
			i++;
		}
		return pairs;
	}

	/**
	 * uniquePairs uses pairs, found in oneOfEachPair, to create an array of the
	 * same combo pair only once (removes the reversed ordered pairs). Because pairs
	 * is ordered, everything after half way through is a duplicate. The first
	 * number in the pair being anything over 5 would be a duplicate. Using the same
	 * idea as in oneOfEachPair for security, I cut off the process when it reached
	 * pairs past [5,5].
	 * 
	 * @param pairs = pairs that occur only once (removes the duplicates but
	 *              includes the reversed ordered pairs)
	 * @return uniquePairs = an array of the same combo pair only once (removes the
	 *         reversed ordered pairs)
	 */
	public static int[][] uniquePairs(int[][] pairs) {
		int[][] uniquePairs = new int[(int) Math.floor(pairs.length / 2)][2];
		int pairNumber = 0;
		int i = 0;
		int pairStart = 11;
		while ((i < uniquePairs.length) && (pairs[i][0] < 6)) {
			int start = pairs[i][0];
			if (start != pairStart) {
				uniquePairs[pairNumber][0] = start;
				uniquePairs[pairNumber][1] = pairs[i][1];
				pairNumber++;
				pairStart = start;
			}
			i++;

		}
		return uniquePairs;
	}

	public static void main(String[] args) {
		int[] numArray = { 1, 1, 2, 4, 4, 5, 5, 5, 6, 7, 9 };
		//Prints out the original input array
		System.out.println("The original array input: ");
		for (int i = 0; i < numArray.length; i++) {
			System.out.print(numArray[i] + " ");
		}
		System.out.println("\n");
		
		//Finds the number distrobution
		int[] numDist = findNumberDistrobution(numArray);
		
		//Finds and prints all pairs (includes duplicates and the reversed ordered pairs) 
		int[][] allPairs = findAllPairs(numDist, numArray);
		System.out.println("All pairs (includes duplicates and the reversed ordered pairs): ");
		int i = 0;
		while ((i < allPairs.length) && ((allPairs[i][0] != 0) && (allPairs[i][1] != 0))) {
			System.out.print("[" + allPairs[i][0] + ",");
			System.out.print(allPairs[i][1] + "]");
			i++;
		}
		System.out.println("\n");
		
		//Finds and prints unique pairs only once (removes the duplicates but includes the reversed ordered pairs)
		int[][] pairs = oneOfEachPair(allPairs);
		System.out.println("Unique pairs that occur only once (removes the duplicates but includes the reversed ordered pairs): ");
		int j = 0;
		while ((j < pairs.length) && ((pairs[j][0] != 0) && (pairs[j][1] != 0))) {
			System.out.print("[" + pairs[j][0] + ",");
			System.out.print(pairs[j][1] + "]");
			j++;
		}
		System.out.println("\n");
		
		//Finds and prints
		int[][] uniquePair = uniquePairs(allPairs);
		System.out.println("Same combo pairs that occur only once (removes the reversed ordered pairs): ");
		int h = 0;
		while ((h < uniquePair.length) && ((uniquePair[h][0] != 0) && (pairs[h][1] != 0))) {
			System.out.print("[" + uniquePair[h][0] + ",");
			System.out.print(uniquePair[h][1] + "]");
			h++;
		}
	}
}
