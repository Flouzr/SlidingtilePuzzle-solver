import csis4463.*;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Main {

    // Simple formatting for the tables in the end.
    private static final DecimalFormat df = new DecimalFormat("#.00");

    public static void main(String[] args) {

        ArrayList<ArrayList<SlidingTilePuzzle>> tilePuzzles = new ArrayList<>();
        ArrayList<String> expandedStates = new ArrayList<>();
        ArrayList<String> generatedStates = new ArrayList<>();
        ArrayList<String> memoryStates = new ArrayList<>();

        // Create 6 ArrayLists of optimal path i (2, 4, 6, 8 ,10, 12) each containing an ArrayList of 100 SlidingTilePuzzle
        for (int i = 2; i <= 12; i += 2){
            ArrayList<SlidingTilePuzzle> temp = new ArrayList<>();
            for (int j = 0; j < 100; j++){
                    temp.add(new SlidingTilePuzzle(3,3, i));
                }
            tilePuzzles.add(new ArrayList<>(temp));
            temp.clear();
        }

        //Loop through the ArrayList of ArrayLists and then solve each puzzle and in the end get the average
        for (ArrayList<SlidingTilePuzzle> puzzles: tilePuzzles) {
            ArrayList<Integer> tempExpanded = new ArrayList<>();
            ArrayList<Integer> tempGenerated = new ArrayList<>();
            ArrayList<Integer> tempMemory = new ArrayList<>();

            // Converting the long to int for convenience of the average method. Not proper but our numbers will never
            // be large enough to matter.
            for (SlidingTilePuzzle puzzle : puzzles) {
                PuzzleSolution tempSolution = SlidingTilePuzzleSolver.uniformCostSearch(puzzle);
                tempExpanded.add(tempSolution.getNumberOfStatesExpanded());
                tempGenerated.add(Math.toIntExact(tempSolution.getNumGenerated()));
                tempMemory.add(tempSolution.getNumberOfStatesInMemory());
            }
            clearAddArrays(expandedStates, generatedStates, memoryStates, tempExpanded, tempGenerated, tempMemory);
            for (SlidingTilePuzzle puzzle : puzzles) {
                PuzzleSolution tempSolution = SlidingTilePuzzleSolver.AStarSearchMisplacedTiles(puzzle);
                tempExpanded.add(tempSolution.getNumberOfStatesExpanded());
                tempGenerated.add(Math.toIntExact(tempSolution.getNumGenerated()));
                tempMemory.add(tempSolution.getNumberOfStatesInMemory());
            }
            clearAddArrays(expandedStates, generatedStates, memoryStates, tempExpanded, tempGenerated, tempMemory);
            for (SlidingTilePuzzle puzzle : puzzles) {
                PuzzleSolution tempSolution = SlidingTilePuzzleSolver.AStarSearchManhattanDistance(puzzle);
                tempExpanded.add(tempSolution.getNumberOfStatesExpanded());
                tempGenerated.add(Math.toIntExact(tempSolution.getNumGenerated()));
                tempMemory.add(tempSolution.getNumberOfStatesInMemory());
            }
            clearAddArrays(expandedStates, generatedStates, memoryStates, tempExpanded, tempGenerated, tempMemory);
            for (SlidingTilePuzzle puzzle : puzzles) {
                PuzzleSolution tempSolution = SlidingTilePuzzleSolver.iterativeDeepening(puzzle);
                tempExpanded.add(tempSolution.getNumberOfStatesExpanded());
                tempGenerated.add(Math.toIntExact(tempSolution.getNumGenerated()));
                tempMemory.add(tempSolution.getNumberOfStatesInMemory());
            }
            clearAddArrays(expandedStates, generatedStates, memoryStates, tempExpanded, tempGenerated, tempMemory);
            for (SlidingTilePuzzle puzzle : puzzles) {
                PuzzleSolution tempSolution = SlidingTilePuzzleSolver.idaStarMisplacedTiles(puzzle);
                tempExpanded.add(tempSolution.getNumberOfStatesExpanded());
                tempGenerated.add(Math.toIntExact(tempSolution.getNumGenerated()));
                tempMemory.add(tempSolution.getNumberOfStatesInMemory());
            }
            clearAddArrays(expandedStates, generatedStates, memoryStates, tempExpanded, tempGenerated, tempMemory);
            for (SlidingTilePuzzle puzzle : puzzles) {
                PuzzleSolution tempSolution = SlidingTilePuzzleSolver.idaStarManhattanDistance(puzzle);
                tempExpanded.add(tempSolution.getNumberOfStatesExpanded());
                tempGenerated.add(Math.toIntExact(tempSolution.getNumGenerated()));
                tempMemory.add(tempSolution.getNumberOfStatesInMemory());
            }
            clearAddArrays(expandedStates, generatedStates, memoryStates, tempExpanded, tempGenerated, tempMemory);
        }

        // Print out each block of code and call printTotals to follow-up with necessary numbers
        System.out.println("Num States Expanded");
        System.out.println("L\t\t\tUCS\t\t\tA*1\t\t\tA*2\t\t\tID\t\t\tIDA*1\t\t\tIDA*2");
        printTotals(expandedStates);

        System.out.println("\nNum States Generated");
        System.out.println("L\t\t\tUCS\t\t\tA*1\t\t\tA*2\t\t\tID\t\t\tIDA*1\t\t\tIDA*2");
        printTotals(generatedStates);

        System.out.println("\nNum States in Memory");
        System.out.println("L\t\t\tUCS\t\t\tA*1\t\t\tA*2\t\t\tID\t\t\tIDA*1\t\t\tIDA*2");
        printTotals(memoryStates);
    }

    // Clear our temp arrays and add the numbers we want to the actual arrays after averaging.
    private static void clearAddArrays(ArrayList<String> expandedStates, ArrayList<String> generatedStates, ArrayList<String> memoryStates, ArrayList<Integer> tempExpanded, ArrayList<Integer> tempGenerated, ArrayList<Integer> tempMemory) {
        expandedStates.add(average(tempExpanded));
        memoryStates.add(average(tempMemory));
        generatedStates.add(average(tempGenerated));
        tempExpanded.clear();
        tempMemory.clear();
        tempGenerated.clear();
    }

    private static String average(ArrayList<Integer> numbers) {
        // Average the numbers given and convert to string so we can use decimal format.
        int sum = 0;
        if (!numbers.isEmpty()) {
            for (Integer num : numbers){
                sum += num;
            }
            return df.format((double)sum / numbers.size());
        }
        return Integer.toString(sum);
    }

    private static void printTotals(ArrayList<String> totals){
        // Array contains all of the numbers for the entire block so loop through
        // and print every 6 in the formatted string.
        for (int k = 0; k < 6; k++){
            System.out.println(String.format("%-10s\t%-10s\t%-10s\t%-9s\t%-10s\t%-10s\t\t%-8s", (k + 1) * 2, totals.get(k * 6), totals.get(k * 6 + 1), totals.get(k * 6 + 2), totals.get(k * 6 + 3), totals.get(k * 6 + 4), totals.get(k * 6 + 5)));
        }
    }
}