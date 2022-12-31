package com.company;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Stack<Integer> plateStack = new Stack<>();

        int amountOfPlates, plateTypeIndex;
        int zincPlates = 0, stackOrder = 0;

        Random rand = new Random();
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("How many plates should be added to the stack (Minimum 3 / Maximum 10): ");
            amountOfPlates = scanner.nextInt();
        }
        while (amountOfPlates < 3 || amountOfPlates > 10);

        Plates[] plate = new Plates[amountOfPlates];

        int[] randomDiameterArray = new int[amountOfPlates];
        String[] randomTypeArray = new String[amountOfPlates];
        String[] plateTypes = {"Zinc", "Porcelain", "Ceramic", "Glass", "Copper"};

        for (int i = 0; i < amountOfPlates; i++) {
            int randomDiameter = rand.nextInt(5);
            randomDiameter += 1;
            randomDiameterArray[i] = randomDiameter;

            plateTypeIndex = rand.nextInt(plateTypes.length);
            randomTypeArray[i] = plateTypes[plateTypeIndex];

            plate[i] = new Plates(randomDiameterArray[i], randomTypeArray[i]);
            if (!Objects.equals(plate[i].getPlateType(), "Zinc"))
                plateStack.add(plate[i].getPlateDiameter());
            else
                zincPlates += 1;
        }

        try {
            stackOrder = checkStackOrder(plateStack, stackOrder);
        }
        catch (NoSuchElementException e) {
            System.out.println("There is not enough items in the stack to check: " + plateStack.size() + " item(s)");
        }

        if (stackOrder < 1)
            System.out.println("The stack is already sorted accordingly");
        else {
            switch (zincPlates) {
                case 0 -> System.out.println("\nThere was 0 occurrences of zinc plates.");
                case 1 -> System.out.println("\nThere was 1 occurrence of a zinc plate that has been moved elsewhere.");
                default -> System.out.println("\nThere was " + zincPlates + " occurrences of zinc plates that have been moved elsewhere.");
            }
            System.out.println("The plates have been sorted, those with larger diameters have been placed under plates with lower diameters as shown:\n");
            Stack<Integer> sortedPlates = sortTheStack(plateStack);
            System.out.println("Plate Diameters " + sortedPlates + "\n");
            System.out.println("Plate Symbols: \n");
            for (Integer sortedPlate : sortedPlates)
                plateSymbols(sortedPlate);
        }
    }

    private static Stack<Integer> sortTheStack(Stack<Integer> stackInt) {
        Stack<Integer> stack = new Stack<>(); int element;
        do {
            element = stackInt.pop();
            while ((!stack.isEmpty()) && (stack.peek() > element))
                stackInt.push(stack.pop());
            stack.push(element);
        }
        while (!stackInt.isEmpty());
        return stack;
    }

    private static int checkStackOrder(Stack<Integer> plateStack, int stackOrder){
        Iterator<Integer> isSorted = plateStack.iterator();
        int oldPlate = isSorted.next();
        do {
            int newPlate = isSorted.next();
            if (newPlate < oldPlate)
                stackOrder += 1;
            oldPlate = newPlate;
        }
        while(isSorted.hasNext());
        return stackOrder;
    }

    private static void plateSymbols(int plateDiameter){
        switch (plateDiameter) {
            case 1 -> System.out.println("    ━━━━━");
            case 2 -> System.out.println("   ━━━━━━━");
            case 3 -> System.out.println("  ━━━━━━━━━");
            case 4 -> System.out.println(" ━━━━━━━━━━━");
            case 5 -> System.out.println("━━━━━━━━━━━━━");
        }
    }
}
