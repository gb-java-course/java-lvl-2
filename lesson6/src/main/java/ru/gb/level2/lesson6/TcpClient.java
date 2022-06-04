package ru.gb.level2.lesson6;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TcpClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;
    private DataInputStream in;
    private DataOutputStream out;
    private Thread clientThread;
    private String name;

    public static void main(String[] args) {
        new TcpClient("1").start();
    }

    public TcpClient(String name) {
        this.name = name;
    }

    private void start() {
        try (Socket socket = new Socket(HOST, PORT)) {
            System.out.printf("Client %s connected to server%n", name);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            startConsoleInput();

            while (!socket.isClosed()) {
                String income = in.readUTF();
                System.out.printf("%s received: %s%n", name, income);
            }
        } catch (IOException e) {
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
        if (clientThread != null && clientThread.isAlive()) {
            clientThread.interrupt();
        }
        System.out.printf("Client %s stopped%n", name);
    }

    private void startConsoleInput() {
        clientThread = new Thread(() -> {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.print("Enter your message >>>>> ");
                while (!Thread.currentThread().isInterrupted()) {
                    if (br.ready()) {
                        String outcome = br.readLine();
                        out.writeUTF(outcome);

                        if (outcome.equals("/end")) {
                            shutdown();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        clientThread.start();
    }
}
