/**
 * CS 420.01: Artificial Intelligence
 * Professor: Dr. Fang Tang
 *
 * Programming Assignment #1
 * <8-Puzzle>
 *
 * Justin Galloway
 *
 * ~PuzzleCompare Class~
 * Sorts puzzle boards through the algorithm values, determined
 * by g(n) and h(n).
 */

import java.util.Comparator;

class PuzzleCompare implements Comparator<EightPuzzleBoard> {
    // Checks if the board values and returns depending on the result
    public int compare(EightPuzzleBoard p1, EightPuzzleBoard p2){
        if (p1.getASearchVal() < p2.getASearchVal())
        {
            return -1;
        }
        if (p1.getASearchVal() > p2.getASearchVal())
        {
            return 1;
        }
        return 0;
    }
}
