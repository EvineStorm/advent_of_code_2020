package day10;

// Find a chain that uses all of your adapters to connect the charging outlet to your device's built-in adapter and
// count the joltage differences between the charging outlet, the adapters, and your device.
// What is the number of 1-jolt differences multiplied by the number of 3-jolt differences?
// Answer: 66 * 28 = 1848

// What is the total number of distinct ways you can arrange the adapters to connect the charging outlet to your device?


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterArray {

    public static void main(String[] args) throws IOException {
        new AdapterArray().run();
    }

    private void run() throws IOException {
        List<String> file = Files.readAllLines(Path.of("src/day10/input.txt"));
        List<Integer> adapters = file.stream().map(Integer::parseInt).sorted().collect(Collectors.toList());
        adapters.add(0, 0);
        int deviceJolts = adapters.get(adapters.size()-1) + 3;
        adapters.add(adapters.size(), deviceJolts);
        int answer = countJolts(adapters);
        System.out.println(answer);
    }

    private int countJolts(List<Integer> adapters) {
        int nrOf1JoltDiffs = 0;
        int nrOf3JoltDiffs = 0;
        for (int i = 0; i < adapters.size() - 1; i++) {
            if ((adapters.get(i+1) - adapters.get(i)) == 1) {
                nrOf1JoltDiffs += 1;
            } else if ((adapters.get(i+1) - adapters.get(i)) == 3) {
                nrOf3JoltDiffs += 1;
            }
        }
        System.out.println(nrOf1JoltDiffs + " " + nrOf3JoltDiffs);
        return nrOf1JoltDiffs * nrOf3JoltDiffs;
    }
}
