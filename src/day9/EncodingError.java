package day9;

// The first step of attacking the weakness in the XMAS data is to find the first number in the list
// (after the preamble) which is not the sum of two of the 25 numbers before it.
// What is the first number that does not have this property?
// 258585477
// What is the encryption weakness in your XMAS-encrypted list of numbers?
// 36981213

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EncodingError {

    public static void main(String[] args) throws IOException {
        new EncodingError().run();
    }

    private void run() throws IOException {
        List<String> file = Files.readAllLines(Path.of("src/day9/input_day9.txt"));
        List<Long> numberSeries = file.stream().map(Long::parseLong).collect(Collectors.toList());
        int preambleLength = 25;

        for (int i = preambleLength; i < numberSeries.size(); i++) {
            long nrToCheck = numberSeries.get(i);
            List<Long> preambleOfNr = numberSeries.subList(i - preambleLength, i);
            boolean isSumOfFormer = preambleCheck(nrToCheck, preambleOfNr);
            if (isSumOfFormer != true) {
                System.out.println(nrToCheck);
                long answer = findContiguousRange(nrToCheck, numberSeries);
                System.out.println(answer);
                break;
            }
        }
    }

    private boolean preambleCheck(long number, List<Long> preambleNumbers) {
        List<Long> sums = new ArrayList<>();
        for (int j = 0; j < preambleNumbers.size(); j++) {
            for (int k = 1; k < preambleNumbers.size(); k++) {
                long sum = preambleNumbers.get(j) + preambleNumbers.get(k);
                sums.add(sum);
            }
        }
        return sums.contains(number);
    }

    private long findContiguousRange(long nrToCheck, List<Long> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            long sum = 0;
            long answer = 0;
            int counter = i;
            List<Long> continuousRange = new ArrayList<>();
            while (sum < nrToCheck) {
                sum += numbers.get(counter);
                continuousRange.add(numbers.get(counter));
                Collections.sort(continuousRange);
                answer = continuousRange.get(0) + continuousRange.get(continuousRange.size() - 1);
                counter++;
            }
            if (sum == nrToCheck) {
                return answer;
            }
        }
        return 0;
    }
}
