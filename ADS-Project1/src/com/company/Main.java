package com.company;

public class Main {

    public static void main(String[] args) {

        int x = 0; int n = 4;

        for (int i = 0; i < n; i++){
            System.out.println("I: " + i);
            for (int j = 0; j < i; j++){
                System.out.println("J: " + j);
                for (int k = 0; k < j; k++){
                    System.out.println("K: " + k);
                    x = x + 1;
                    System.out.println("X: " + x);
                }
            }
        }
    }
}


