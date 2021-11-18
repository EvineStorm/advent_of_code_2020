package day8;

// Run your copy of the boot code.
// Immediately before any instruction is executed a second time, what value is in the accumulator?
// 2051

// Fix the program so that it terminates normally by changing exactly one jmp (to nop) or nop (to jmp).
// What is the value of the accumulator after the program terminates?
// (answer)

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class HandheldHalting {

    public static void main(String[] args) throws IOException {
        new HandheldHalting().run();
    }

    private void run() throws IOException {
        int accumulator = 0;
        List<String> instructions = Files.readAllLines(Path.of("src/day8/input_day8.txt"));
        for (int i = 0; i < instructions.size(); i++) {
            String line = instructions.get(i);
            String[] operationArgument = line.split(" ", 2);

            if (operationArgument[0].equals("")) {
                System.out.println(accumulator);
                break;
            }

            if (operationArgument[0].equals("acc")) {
                instructions.set(i, "");
                accumulator = doOperation(accumulator, operationArgument);
            } else if (operationArgument[0].equals("jmp")) {
                instructions.set(i, "");
                i = (doOperation(i, operationArgument)) - 1;
            } else if (operationArgument[0].equals("nop")) {
                continue;
            }
        }
    }

    private int doOperation(int j, String[] operationArgument) {
        int argument = Integer.parseInt(operationArgument[1].substring(1));
        if (operationArgument[1].contains("+")) {
            j += argument;
        } else if (operationArgument[1].contains("-")) {
            j -= argument;
        }
        return j;
    }
}
