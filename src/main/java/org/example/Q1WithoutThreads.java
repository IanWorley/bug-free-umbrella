package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Q1WithoutThreads {

        public static SuperSpecialNumber number = new SuperSpecialNumber();

        public static void main(String[] args) {


            for (int i = 0; i <= 1000; i++) {
                number.increment(1);
                System.out.println(number.getNumber());


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
