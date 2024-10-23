package com.company;

import java.util.Arrays;
import java.util.Random;

public class Main {

    // array of weights of the groups of bricks
    private double[] weights;

    // number of groups of bricks
    private int n;

    // random number generator
    private Random random;

    // current state of the lorries
    private int[] state;

    // fitness function
    private FitnessFunction fitnessFunction;

    public Main(double[] weights, FitnessFunction fitnessFunction) {
        this.weights = weights;
        this.n = weights.length;
        this.random = new Random();
        this.fitnessFunction = fitnessFunction;
        this.state = new int[n];
    }

    // generates a random initial state
    private void generateRandomState() {
        for (int i = 0; i < n; i++) {
            // assign a random lorry (A or B) to each group of bricks
            state[i] = random.nextInt(2);
        }
    }

    // gets the total load of lorry A
    private int getLorryALoad() {
        int load = 0;
        for (int i = 0; i < n; i++) {
            if (state[i] == 0) {
                load += weights[i];
            }
        }
        return load;
    }

    // gets the total load of lorry B
    private double getLorryBLoad() {
        double load = 0;
        for (int i = 0; i < n; i++) {
            if (state[i] == 1) {
                load += weights[i];
            }
        }
        return load;
    }

    // returns the fitness of the current state
    private double getFitness() {
        return fitnessFunction.evaluate(getLorryALoad(), getLorryBLoad());
    }

    // generates a random mutation of the current state
    private int[] generateMutation() {
        int[] mutation = state.clone();
        int index = random.nextInt(n);
        mutation[index] = mutation[index] == 0 ? 1 : 0;
        return mutation;
    }

    // runs the Hill Climbing algorithm
    public int[] run() {
        generateRandomState();
        int[] nextState;
        double currentFitness = getFitness();
        double nextFitness;

        while (true) {
            nextState = generateMutation();
            nextFitness = fitnessFunction.evaluate(getLorryALoad(), getLorryBLoad());
            if (nextFitness > currentFitness) {
                state = nextState;
                currentFitness = nextFitness;
            } else {
                break;
            }
        }
        return state;
    }

    // interface for the fitness function
    public interface FitnessFunction {
        // returns the fitness of the given state
        double evaluate(double lorryALoad, double lorryBLoad);
    }

    public static void main(String[] args) {
        // array of weights of the groups of bricks
        //double[] weights = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        double[] weights = {85.26, 72.1, 90.22, 30.96, 47.71, 20.45, 30.29, 92.38, 52.01, 17.66, 16.38, 56.28, 66.8, 53.91, 12.46, 79.52, 97.09, 70.8, 24.29, 49.33};

        // create a Hill Climbing instance with the given weights and maximum load
        Main hillClimbing = new Main(weights, new Main.FitnessFunction() {
            @Override
            public double evaluate(double lorryALoad, double lorryBLoad) {
                // the fitness is the absolute difference between the loads of the two lorries
                return Math.abs(lorryALoad - lorryBLoad);
            }
        });

        // run the Hill Climbing algorithm 10 times and capture the results
        for (int i = 1; i <= 10; i++) {
            int[] state = hillClimbing.run();
            double lorryALoad = hillClimbing.getLorryALoad();
            double lorryBLoad = hillClimbing.getLorryBLoad();
            double fitness = hillClimbing.getFitness();
            System.out.println("Experiment Number: " + i + ", Final Fitness Value: " + String.format("%.2f",fitness) + ", Solution: " + Arrays.toString(state));
        }
    }
}

