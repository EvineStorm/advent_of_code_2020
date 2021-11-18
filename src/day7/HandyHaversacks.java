package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

// How many bag colors can eventually contain at least one shiny gold bag?
// 335
// How many individual bags are required inside your single shiny gold bag?
// 2431

public class HandyHaversacks {

    private Map<String, List<String>> bagsContainBags = new HashMap<>();

    public static void main(String[] args) throws IOException {
        new HandyHaversacks().parseTxt();
    }

    private void parseTxt() throws IOException {
        List<String> allBags = Files.readAllLines(Path.of("src/day7/input.txt"));

        for (String input : allBags) {
            input = input.replaceAll(" bags?\\.?", ""); // Remove bag/bags/bag./bags.
            String[] bagAndContents = input.split(" contain ");

            List<String> containsBags;
            if (bagAndContents[1].equals("no other")) {
                containsBags = Collections.emptyList();
            } else {
                containsBags = Arrays.stream(bagAndContents[1].split(", ")).collect(Collectors.toList());
            }
            bagsContainBags.put(bagAndContents[0], containsBags);
        }
        sumContainGoldBag();
        System.out.println("Answer (without counting shiny): " + (countBagsInside("shiny gold") - 1));
    }


    private void sumContainGoldBag() {
        int sumBags = 0;
        for (String bag : bagsContainBags.keySet()) {
            if (canContainGold(bag)) sumBags += 1;
        }
        System.out.println(sumBags);
    }

    private boolean canContainGold(String bag) {
        if (bag.split(" ").length == 3) {
            bag = bag.substring(2);
        }

        for (String bagType : bagsContainBags.get(bag)) {
            if (bagType.contains("shiny gold")) {
                return true;
            } else {
                if (canContainGold(bagType)) return true;
            }
        }
        return false;
    }

    // 2432
    private int countBagsInside(String outerBag) {
        int totalBagsInGold = 1;
        for (String innerBag : bagsContainBags.get(outerBag)) {
            int amountOfBags = Integer.parseInt(innerBag.split(" ")[0]);
            String innerBagName = innerBag.substring(innerBag.indexOf(" ")).trim();
            totalBagsInGold += (countBagsInside(innerBagName) * amountOfBags);
        }
        return totalBagsInGold;
    }
}


