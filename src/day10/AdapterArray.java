package day10;

// Find a chain that uses all of your adapters to connect the charging outlet to your device's built-in adapter and
// count the joltage differences between the charging outlet, the adapters, and your device.
// What is the number of 1-jolt differences multiplied by the number of 3-jolt differences?
// Answer: 66 * 28 = 1848

// What is the total number of distinct ways you can arrange the adapters to connect the charging outlet to your device?
// 8099130339328


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterArray {

    public static void main(String[] args) throws IOException {
        new AdapterArray().run();
    }

    private void run() throws IOException {
        List<String> file = Files.readAllLines(Path.of("src/day10/input.txt"));
        List<Integer> adapters = file.stream().map(Integer::parseInt).sorted().collect(Collectors.toList());

        int deviceJolts = adapters.get(adapters.size() - 1) + 3;
        adapters.add(0, 0);
        adapters.add(adapters.size(), deviceJolts);

        int answer = countJolts(adapters);
        System.out.println(answer);

        long answerB = countAdapterArrangements(adapters);
        System.out.println(answerB);
    }


    private int countJolts(List<Integer> adapters) {
        List<Integer> joltDiffs = getJoltDiffs(adapters);
        Collections.sort(joltDiffs);
        int nrOf1JoltDiffs = joltDiffs.indexOf(3);
        int nrOf3JoltDiffs = joltDiffs.size() - nrOf1JoltDiffs;
        System.out.println(nrOf1JoltDiffs + " " + nrOf3JoltDiffs);
        return nrOf1JoltDiffs * nrOf3JoltDiffs;
    }


    private long countAdapterArrangements(List<Integer> adapters) {
        List<Integer> joltDiffs = getJoltDiffs(adapters);
        int rowOf1s = 1;
        long amountOfCombinations = 1;
        for (int i = 1; i < joltDiffs.size(); i++) {
            if ((joltDiffs.get(i -1) == 1) && (joltDiffs.get(i) == 1)) {
                rowOf1s += 1;
            } else if (rowOf1s > 1) {
                if (rowOf1s <= 3) {
                    amountOfCombinations *= Math.pow(2, rowOf1s - 1);
                } else {
                    int excess1s = rowOf1s -3;
                    int totalCombinations = (int) (Math.pow(2, rowOf1s - 1));
                    int impossibleCombinations = (int) (Math.pow(2, excess1s))-1;
                    amountOfCombinations *= totalCombinations - impossibleCombinations;
                }
                rowOf1s = 1;
            }
        }
        return amountOfCombinations;
    }


    private List<Integer> getJoltDiffs(List<Integer> adapters) {
        List<Integer> joltDiffs = new ArrayList<>();
        for (int i = 0; i < adapters.size() - 1; i++) {
            joltDiffs.add(adapters.get(i + 1) - adapters.get(i));
        }
        return joltDiffs;
    }
}
