package daa.metrics;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {

    public static void write(String fileName, List<Result> results) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            writer.write(
                    "workload,variant,structure,n,time_ms,steps,moves,comparisons"
            );
            writer.newLine();

            for (Result result : results) {

                writer.write(
                        result.getWorkload() + "," +
                                result.getVariant() + "," +
                                result.getStructure() + "," +
                                result.getN() + "," +
                                result.getTimeMs() + "," +
                                result.getSteps() + "," +
                                result.getMoves() + "," +
                                result.getComparisons()
                );

                writer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}