package org.example;


import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Q1 {

    public static SuperSpecialNumber number = new SuperSpecialNumber();

    public static void main(String[] args) {


        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            executor.execute(new IncrementByOneTask());
        }
        executor.shutdown();

        while (!executor.isTerminated()) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        System.out.println(number.getNumber());





    }


    private static class IncrementByOneTask implements Runnable {


        @Override
        public void run() {
            number.increment(1);
        }
    }

        private static class SuperSpecialNumber {
            private static Integer number = 0;

            public synchronized void increment(int incrementBy) {
                int stateFreezer = number + incrementBy;


                number = stateFreezer;


            }

            public Integer getNumber() {
                return number;
        }
    }


}