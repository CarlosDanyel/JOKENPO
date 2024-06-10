package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class JokenpoClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            Scanner scanner = new Scanner(System.in);
            System.out.println(in.readLine());  // Recebe mensagem de boas-vindas

            while (true) {
                System.out.println(in.readLine());  // Mensagem do servidor sobre o adversário

                System.out.println("Escolha sua jogada (Pedra, Papel, Tesoura): ");
                String move = scanner.nextLine();
                out.println(move);
                System.out.println(in.readLine());  // Confirmação da jogada
                System.out.println(in.readLine());  // Resultado da partida
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
