package org.example;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Q2 {

    public static void main(String[] args) {
        final int N = 1000;
       double sum = 0;
        double[] list = new double[N];

        for (int i = 0; i < list.length; i++) {
            list[i] = Math.random();
        }
        long startTime = System.currentTimeMillis();
        System.out.println("Sum: " + parallelSum(list));
        long endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + " milliseconds");
        startTime = System.currentTimeMillis();
        for (double a: list) {
            sum += a;
        }
        endTime = System.currentTimeMillis();
        System.out.println("Sum: " + sum);
        System.out.println("Time: " + (endTime - startTime) + " milliseconds");


        if (sum == parallelSum(list)) {
            System.out.println("Correct");
        } else {
            System.out.println("Incorrect");
            System.out.println("Sum: " + (sum - parallelSum(list)) );
        }


    }

        public static double parallelSum(double[] list) {
         RecursiveTask<Double> mainTask = new SortTask(list);
            ForkJoinPool pool = new ForkJoinPool();
            return pool.invoke(mainTask);


        }


    private static class SortTask extends RecursiveTask<Double>{
        private final int THRESHOLD = 10;
        double[] list;


        SortTask(double[] list) {
           this.list = Arrays.copyOfRange(list, 0, list.length);

        }
        @Override
        protected Double compute() {
            if (list.length < THRESHOLD){
                double sum =0;
                for (double a: list) {
                  sum += a;
                }
                return sum;

            }
            else {
                double[] firstHalf = Arrays.copyOfRange(list, 0, list.length / 2);
                double[] secondHalf = Arrays.copyOfRange(list, list.length / 2, list.length);
                RecursiveTask<Double> left = new SortTask(firstHalf);//mid will be the new high,  this left is returned by MaxTask
                RecursiveTask<Double> right = new  SortTask(secondHalf);
                left.fork();
                right.fork();
                return left.join() + right.join();

            }
        }
    }

}