package com.dengqinghua.example;

public class App {
    public static void main( String[] args ) {
        System.out.println("dsg" + System.getProperty("dsg"));
        System.out.println("I am a server running FOREVER");
        runForever();
    }

    private static void runForever() {
        while (true) {
            try {
                Thread.sleep(10000000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
