package com.dengqinghua.concurrency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadPool {
    public static void runSingleThreadServer() throws IOException {
        ServerSocket socket = new ServerSocket(10080);

        while (true) {
            final Socket connetion = socket.accept();
            handleConnection(connetion);
            connetion.close();
        }
    }

    public static void handleConnection(Socket connection) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            System.out.println(in.readLine());
            connection.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static final int THREAD_COUNT = 100;
    private static final Executor executor = Executors.newFixedThreadPool(THREAD_COUNT);

    /**
     * 线程池的组成
     *
     *      Executor
     *
     * @throws IOException
     */
    public static void runMuiltThreadServerWithThreadPool() throws IOException {
        ServerSocket socket = new ServerSocket(10080);

        while (true) {
            final Socket connection = socket.accept();
            // 这里采用了线程池的方式
            executor.execute(() -> handleConnection(connection));
        }
    }
}
