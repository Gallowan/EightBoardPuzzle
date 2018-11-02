/**
 * CS 420.01: Artificial Intelligence
 * Professor: Dr. Fang Tang
 *
 * Programming Assignment #1
 * <8-Puzzle>
 *
 * Justin Galloway
 *
 * ~EightPuzzleBoard Class~
 * This class contains the PuzzleBoard and its operations.
 */

import java.util.Random;

public class EightPuzzleBoard {
    // the positions of all the squares on the board
    private StringBuffer boardValues;
    // Parent node
    private EightPuzzleBoard parent;
    // Type of heuristic used; h1 or h2
    private int heuristicType;
    // node depth
    private int depth;
    // g(n) + h(n)
    private int searchValue;
    // 0 space
    private int emptySpace;

    //Constructor that calls itself as initiation
    public EightPuzzleBoard(String boardValue, int h) {
        this(boardValue, 0, null, h);
    }

    // Sets board.
    public void setBoard(String newVals){
        this.boardValues = new StringBuffer(newVals);
        this.setASearch();
    }

    public int getASearchVal(){
        return this.searchValue;
    }

    public EightPuzzleBoard getParent(){
        return this.parent;
    }

    public String getBoardValue(){
        return this.boardValues.toString();
    }

    // Initialize the board with specified values
    private EightPuzzleBoard(String boardVal, int d, EightPuzzleBoard p, int h){
        this.boardValues = new StringBuffer(boardVal);

        // Check for empty spot
        for (int i = 0; i < 9; i++) {
            if (this.boardValues.charAt(i) == '0') {
                this.emptySpace = i;
                break;
            }
        }
        this.heuristicType = h;
        this.depth = d;
        this.parent = p;
        this.setASearch();      // initialize A* value
    }

    //Choose which heuristic type to use
    private void setASearch(){
        // h1Misplaced() if the type is equal to 1
        if(this.heuristicType == 1){
            this.searchValue = this.depth + this.h1Misplaced();
        }
        // h2Manhattan() in any other case
        else {
            this.searchValue = this.depth + this.h2Manhattan();
        }
    }

    // (1) h1 = the number of misplaced tiles
    private int h1Misplaced() {
        int misplacedTiles = 0;
        // Checks through tiles to locate misplacements, increments accordingly
        for (int i = 0; i < 9; i++) {
            if (this.boardValues.charAt(i) != (char) (48 + i)) {
                misplacedTiles++;
            }
        }
        return misplacedTiles;
    }

    // (2) h2 = the sum of the distances of the tiles from their goal positions
    private int h2Manhattan() {
        int distance = 0;
        // iterate through each square
        for (int space = 0; space < 9; space++) {
            // Calculate row and column
            int row = space / 3;
            int column = space % 3;

            int currentValue = (int) this.boardValues.charAt(space) - 48;

            // Calculate correct row and column
            int correctRow = currentValue / 3;
            int correctColumn = currentValue % 3;

            distance += Math.abs(row - correctRow) + Math.abs(column - correctColumn);
        }
        return distance;
    }

    // Checks if the puzzle is at goal state by setting it to a string
    public boolean goalState() {
        return this.boardValues.toString().equals("012345678");
    }

    // Checks if the puzzle is solvable. If there are an even number of
    // inverted spaces, then the puzzle is solvable. Checks by looking through the
    // puzzle to look for inconsistencies.
    public boolean solvableCheck() {
        int invertedSpaces = 0;
        // Checks to see if the coming tile is greater than the tile before and
        // increments accordingly
        for (int i = 0; i < 8; i++) {
            if (this.boardValues.charAt(i) != '0') {
                for (int j = i + 1; j < 9; j++) {
                    if (this.boardValues.charAt(j) != '0' && (int) this.boardValues.charAt(i) > (int) this.boardValues.charAt(j)) {
                        invertedSpaces++;
                    }
                }
            }
        }

        if ((invertedSpaces % 2) == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    // Randomizes the board through an array if the user so chooses
    public void randomize(int n) {
        Random rand = new Random();
        int nextMove;
        for (int i = 0; i < n; i++) {
            nextMove = rand.nextInt(4);
            if (nextMove == 0) {
                if (this.emptySpace > 2) {
                    this.boardValues.setCharAt(this.emptySpace, this.boardValues.charAt(this.emptySpace - 3));
                    this.emptySpace -= 3;
                    this.boardValues.setCharAt(this.emptySpace, '0');
                }
            }
            if (nextMove == 1) {
                if (this.emptySpace % 3 != 0) {
                    this.boardValues.setCharAt(this.emptySpace, this.boardValues.charAt(this.emptySpace - 1));
                    this.emptySpace--;
                    this.boardValues.setCharAt(this.emptySpace, '0');
                }
            }
            if (nextMove == 2) {
                if (this.emptySpace % 3 != 2) {
                    this.boardValues.setCharAt(this.emptySpace, this.boardValues.charAt(emptySpace + 1));
                    this.emptySpace++;
                    this.boardValues.setCharAt(this.emptySpace, '0');
                }
            }
            if (nextMove == 3) {
                if (this.emptySpace < 6) {
                    this.boardValues.setCharAt(this.emptySpace, this.boardValues.charAt(this.emptySpace + 3));
                    this.emptySpace += 3;
                    this.boardValues.setCharAt(this.emptySpace, '0');
                }
            }
        }
    }

    /*
    The next four functions will create a new 8-Puzzle Board in the event of a space
    movement to any of the four possible directions. If the move is illegal, however, it
    will return a null instead.
     */
    public EightPuzzleBoard moveLeft(){
        // StringBuffer to store a copy and manipulate the current board values
        StringBuffer newValue = new StringBuffer(this.boardValues.toString());
        // if empty isn't on left column, produce left movement
        if (this.emptySpace % 3 != 0) {
            // to move "left" we must swap the empty tile with the character 1 tiles before the empty
            newValue.setCharAt(this.emptySpace, this.boardValues.charAt(this.emptySpace - 1));
            newValue.setCharAt(this.emptySpace - 1, '0');
            // return a new PuzzleBoard as a child produced from a leftward movement
            return new EightPuzzleBoard(newValue.toString(), this.depth + 1, this, this.heuristicType);
        } else {
            return null;
        }
    }
    public EightPuzzleBoard moveRight(){
        // StringBuffer to store a copy and manipulate the current board values
        StringBuffer newValue = new StringBuffer(this.boardValues.toString());
        // if empty isn't on right column, produce right movement
        if (this.emptySpace % 3 != 2) {
            // to move "right" we must swap the empty tile with the character 1 tiles after the empty
            newValue.setCharAt(this.emptySpace, this.boardValues.charAt(this.emptySpace + 1));
            newValue.setCharAt(this.emptySpace + 1, '0');
            // return a new PuzzleBoard as a child produced from a rightward movement
            return new EightPuzzleBoard(newValue.toString(), this.depth + 1, this, this.heuristicType);
        } else {
            return null;
        }
    }
    public EightPuzzleBoard moveUp(){
        // StringBuffer to store a copy and manipulate the current board values
        StringBuffer newValue = new StringBuffer(this.boardValues.toString());
        // if empty isn't on top row, produce up movement
        if (this.emptySpace > 2) {
            // to move "up" we must swap the empty tile with the character 3 tiles before the empty
            newValue.setCharAt(this.emptySpace, this.boardValues.charAt(this.emptySpace - 3));
            newValue.setCharAt(this.emptySpace - 3, '0');
            // return a new PuzzleBoard as a child produced from a upward movement
            return new EightPuzzleBoard(newValue.toString(), this.depth + 1, this, this.heuristicType);
        } else {
            return null;
        }
    }
    public EightPuzzleBoard moveDown(){
        // StringBuffer to store a copy and manipulate the current board values
        StringBuffer newValue = new StringBuffer(this.boardValues.toString());
        // if empty isn't on the bottom row, produce down movement
        if (this.emptySpace < 6) {
            // to move "down" we must swap the empty tile with the character 3 tiles after the empty
            newValue.setCharAt(this.emptySpace, this.boardValues.charAt(this.emptySpace + 3));
            newValue.setCharAt(this.emptySpace + 3, '0');
            // return a new PuzzleBoard as a child produced from a downward movement
            return new EightPuzzleBoard(newValue.toString(), this.depth + 1, this, this.heuristicType);
        } else {
            return null;
        }
    }

    // Print the board
    public void printBoard() {
        System.out.println(this.boardValues.charAt(0) + " " + this.boardValues.charAt(1) + " " + this.boardValues.charAt(2) +
            "\n" + this.boardValues.charAt(3) + " " + this.boardValues.charAt(4) + " " + this.boardValues.charAt(5) +
            "\n" + this.boardValues.charAt(6) + " " + this.boardValues.charAt(7) + " " + this.boardValues.charAt(8));
    }
}
