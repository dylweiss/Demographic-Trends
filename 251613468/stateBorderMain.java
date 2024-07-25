package org.example;
import java.util.*;
import java.util.List;

public class stateBorderMain {
    public static void main(String[] args) {
        // Prompt user for input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter start state: ");
        String startState = scanner.nextLine().trim();
        System.out.print("Enter end state: ");
        String endState = scanner.nextLine().trim();

        if (!DataScraper.borderInfoGetter().containsKey(startState) ||
                !DataScraper.borderInfoGetter().containsKey(endState)) {
            System.out.println("Invalid state name(s) entered.");
            return;
        }

        List<String> shortestPath = DataScraper.findShortestPath(startState, endState);
        if (shortestPath.isEmpty()) {
            System.out.println("No path exists between " + startState + " and " + endState);
        } else {
            System.out.println("Shortest path from " + startState + " to " + endState + ": " + shortestPath);
        }
    }
}
