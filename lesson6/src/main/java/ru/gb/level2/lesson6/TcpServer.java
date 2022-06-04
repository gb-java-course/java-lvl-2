package ru.gb.level2.lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TcpServer {

    private static final int PORT = 8080;

    private static final int READING_THREADS_COUNT = 2;

    private Thread waitConnectionThread;
    private Thread manageReadingThread;

    private final List<Socket> sockets = new ArrayList<>();
    private final Map<Socket, Thread> readingThreads = new HashMap<>(READING_THREADS_COUNT);

    private final Object mon = new Object();

    public static void main(String[] args) {
        new TcpServer().start();
    }

    private void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started");
            startWaitConnectionThread(serverSocket);
            startManageReadingThread();

            waitConnectionThread.join();
            manageReadingThread.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void shutdown() throws IOException {
        if (waitConnectionThread != null && waitConnectionThread.isAlive()) {
            waitConnectionThread.interrupt();
        }
        if (manageReadingThread != null && manageReadingThread.isAlive()) {
            manageReadingThread.interrupt();
        }

        sockets.forEach(socket -> {
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println("Server stopped");
    }

    private void startWaitConnectionThread(ServerSocket serverSocket) throws InterruptedException {
        waitConnectionThread = new Thread(() -> {
            try {
                waitConnection(serverSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        waitConnectionThread.start();
    }

    private void startManageReadingThread() {
        manageReadingThread = new Thread(this::manageReadThreads);
        manageReadingThread.start();
    }

    private void waitConnection(ServerSocket serverSocket) throws IOException {
        while (true) {
            System.out.println("Waiting for connection...");
            var socket = serverSocket.accept();
            synchronized (mon) {
                sockets.add(socket);
            }
            System.out.println("Client connected");
        }
    }

    private void manageReadThreads() {
        while (true) {
            sockets.removeIf(Socket::isClosed);
            readingThreads.entrySet().removeIf(t -> !t.getValue().isAlive());

            var index = 0;
            while (index < sockets.size()
                    && readingThreads.size() < READING_THREADS_COUNT) {
                Socket socket;
                synchronized (mon) {
                    socket = sockets.get(index);
                }

                if (readingThreads.containsKey(socket)) {
                    index++;
                    continue;
                }
                var thread = new Thread(() -> {
                    try {
                        var name = Thread.currentThread().getName();
                        System.out.printf("thread %s inited%n", name);
                        var in = new DataInputStream(socket.getInputStream());
                        while (true) {
                            var income = in.readUTF();
                            System.out.printf("Received(thread:%s): %s%n", name, income);
                            broadcastMessage(socket, income);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        try {
                            socket.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                readingThreads.put(socket, thread);
                thread.setName("Reader" + index);
                thread.start();

                System.out.println("readingThreads.size " + readingThreads.size());

                index++;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcastMessage(Socket writingSocket, String message) throws IOException {
        synchronized (mon) {
            for (var s : sockets) {
                if (s.equals(writingSocket)) {
                    continue;
                }

                var out = new DataOutputStream(s.getOutputStream());
                out.writeUTF(String.format("%d:%s%n", sockets.indexOf(writingSocket), message));
            }
        }
    }
}
