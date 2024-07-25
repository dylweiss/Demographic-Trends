package org.example;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            String[][] stateInfo = DataScraper.getStateInfo();

            // Extract land area and electoral votes for each state
            double[] landAreas = new double[stateInfo.length];
            double[] electoralVotes = new double[stateInfo.length];

            for (int i = 0; i < stateInfo.length; i++) {
                landAreas[i] = Double.parseDouble(stateInfo[i][2]); // Assuming state size is stored in the third column
                electoralVotes[i] = Double.parseDouble(stateInfo[i][4]); // Assuming electoral votes are stored in the fifth column
            }

            // Calculate Pearson correlation coefficient
            double correlationCoefficient = calculatePearsonCorrelation(landAreas, electoralVotes);

            // Output the correlation coefficient
            System.out.println("Pearson correlation coefficient: " + correlationCoefficient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double calculatePearsonCorrelation(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Arrays must have the same length");
        }

        // Calculate means
        double meanX = calculateMean(x);
        double meanY = calculateMean(y);

        // Calculate sums of squares
        double sumXY = 0.0;
        double sumX2 = 0.0;
        double sumY2 = 0.0;

        for (int i = 0; i < x.length; i++) {
            sumXY += (x[i] - meanX) * (y[i] - meanY);
            sumX2 += Math.pow(x[i] - meanX, 2);
            sumY2 += Math.pow(y[i] - meanY, 2);
        }

        // Calculate Pearson correlation coefficient
        return sumXY / Math.sqrt(sumX2 * sumY2);
    }

    public static double calculateMean(double[] data) {
        double sum = 0.0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.length;
    }
}