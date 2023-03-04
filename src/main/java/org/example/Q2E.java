package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.*;

public class Q2E {
    public static void main(String[] args) {
        // Create a list
        final int N = 90000000;
        double[] list = new double[N];
        double sum = 0;;


        for (int i = 0; i < list.length; i++) {
           double num = (Math.random() * 100000);
            BigDecimal bigDecimal = new BigDecimal(num);
            bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_DOWN);
            list[i] = bigDecimal.doubleValue();
        }

        long startTime = System.currentTimeMillis();
        System.out.println("\nThe maximal number is " + max(list));
        long endTime = System.currentTimeMillis();
        System.out.println("Number of processors is " +
                Runtime.getRuntime().availableProcessors());
        System.out.println("Time with " + (endTime - startTime)
                + " milliseconds");

        startTime = System.currentTimeMillis();
        for (double a : list) {
            sum += a;

        }
        endTime = System.currentTimeMillis();
        System.out.println("Time with " + (endTime - startTime)
                + " milliseconds");


        if (max(list).equals(sum)) {
            System.out.println("Correct");
        } else {
            System.out.println("Incorrect");
            System.out.println("Sum: " + (sum - max(list)));
            System.out.println("Sum: " + (sum));
            System.out.println("Sum: " + (max(list)));
        }
    }

    public static Double max(double[] list) {
        RecursiveTask<Double> task = new MaxTask(list, 0, list.length);
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(task);
    }

    private static class MaxTask extends RecursiveTask<Double> {
        private final static int THRESHOLD = 1000;
        private double[] list;
        private int low;
        private int high;

        public MaxTask(double[] list, int low, int high) {//high is a index
            this.list = list;
            this.low = low;
            this.high = high;
        }

        @Override
        public Double compute() {
            if (high - low < THRESHOLD) {
                double max = 0;
                for (int i = low; i < high; i++)//get the max in this list with number< Threshold
                        max += list[i];
                return Double.parseDouble(String.valueOf(max));
            }
            else {
                int mid = (low + high) / 2;
                RecursiveTask<Double> left = new MaxTask(list, low, mid);//mid will be the new high,  this left is returned by MaxTask
                RecursiveTask<Double> right = new MaxTask(list, mid, high);

                right.fork();
                left.fork();
                return new Double(left.join() + right.join());
            }
        }
    }
}
