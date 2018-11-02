/**
 * CS 420.01: Artificial Intelligence
 * Professor: Dr. Fang Tang
 *
 * Programming Assignment #1
 * <8-Puzzle>
 *
 * Justin Galloway
 *
 * ~PuzzleSolver Class~
 * Solves the puzzle through both of the provided heuristics.
 */

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Stack;

public class PuzzleSolver {

    private EightPuzzleBoard root;
    private String setup;
    private int endNodes;
    private int optimizedSteps;

    // Initializes the solver
    public PuzzleSolver(String setupIn){
        this.setup = new String();
        this.setup = setupIn;
        this.endNodes = 0;
        this.optimizedSteps = 0;
    }

    // Calls in to solve specified heuristic
    public int solveH1(){
        return this.solve(1);
    }
    public int solveH2(){
        return this.solve(2);
    }

    public int getOptimizedPath(){
        return this.optimizedSteps;
    }

    // Solve the puzzle utilizing PriorityQueue
    // Using priority / hashmap because they are familiar, even if it's not the best choice!
        private int solve(int heuristicType){
        Comparator<EightPuzzleBoard> puzzleComp = new PuzzleCompare();
        PriorityQueue<EightPuzzleBoard> queue = new PriorityQueue<>(10, puzzleComp);
        HashMap<String, EightPuzzleBoard> hashMap = new HashMap<>(10);

        this.root = new EightPuzzleBoard(this.setup, heuristicType);
        queue.add(this.root);
        this.endNodes = 0;

        while(queue.size() > 0){
            EightPuzzleBoard epb = queue.remove();

            if(epb.goalState()){
                int optimizedSteps = 0;
                // Stack allows the results to be printed in the correct order
                Stack<EightPuzzleBoard> stack = new Stack<>();
                while(epb != null){
                    optimizedSteps++;
                    stack.push(epb);
                    epb = epb.getParent();
                }

                while (stack.size() > 1) {
                    stack.pop().printBoard();
                    System.out.println();
                }
                stack.pop().printBoard();

                this.optimizedSteps = optimizedSteps;
                return this.endNodes;
            }

            // This block of code will determine the new puzzle movement if the empty
            // Space were to move in any direction
            EightPuzzleBoard tempBoard = epb.moveUp();
            if (tempBoard != null){
                this.endNodes++;
                if(!hashMap.containsKey(tempBoard.getBoardValue())) {
                    queue.add(tempBoard);
                }
            }
            tempBoard = epb.moveDown();
            if (tempBoard != null){
                this.endNodes++;
                if(!hashMap.containsKey(tempBoard.getBoardValue())) {
                    queue.add(tempBoard);
                }
            }
            tempBoard = epb.moveLeft();
            if (tempBoard != null){
                this.endNodes++;
                if(!hashMap.containsKey(tempBoard.getBoardValue())) {
                    queue.add(tempBoard);
                }
            }
            tempBoard = epb.moveRight();
            if (tempBoard != null){
                this.endNodes++;
                if(!hashMap.containsKey(tempBoard.getBoardValue())) {
                    queue.add(tempBoard);
                }
            }
            hashMap.put(epb.getBoardValue(), epb);
        }
        return 0;
    }
}
