/**
 * CS 420.01: Artificial Intelligence
 * Professor: Dr. Fang Tang
 *
 * Programming Assignment #1
 * <8-Puzzle>
 *
 * Justin Galloway
 *
 * ~PuzzleMain Class~
 * This is the main class of the program.
 */

import java.util.Scanner;

public class PuzzleMain {

    public PuzzleMain() {}

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean solvable = false;

        System.out.println("Welcome to the 8-Puzzle Menu.\nChoose 1 randomize a" +
                "board and 2 to input your own puzzle!");
        int userChoice = input.nextInt();
        input.nextLine();

        EightPuzzleBoard defaultBoard = new EightPuzzleBoard("012345678", 2);

        if (userChoice == 1){
            defaultBoard.randomize(100);
        } else if (userChoice == 2){
            while (!solvable) {
                System.out.println("Enter the first line.");
                String userInput;
                userInput = input.nextLine();

                System.out.println("... and the second line.");
                userInput += input.nextLine();

                System.out.println("... and the third line.");
                userInput += input.nextLine();

                defaultBoard.setBoard(userInput);
                solvable = defaultBoard.solvableCheck();
                if(!solvable){
                    System.out.println("The puzzle entered is not solvable. Try again.");
                }
            }
        }
        System.out.println("New Board: ");
        defaultBoard.printBoard();

        PuzzleSolver puzzleSolver = new PuzzleSolver(defaultBoard.getBoardValue());
        System.out.println("\n- h1 : Misplaced Heuristic -\n");
        System.out.println("\nNodes - " + puzzleSolver.solveH1());
        System.out.println("Depth - " + puzzleSolver.getOptimizedPath());
        System.out.println("\n- h2 : Distance Heuristic -\n");
        System.out.println("\nNodes - " + puzzleSolver.solveH2());
        System.out.println("Depth - " + puzzleSolver.getOptimizedPath());
    }
}
