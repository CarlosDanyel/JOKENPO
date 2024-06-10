package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class JokenpoServer {

    // PORTA SO SERVIDOR
    private static final int PORT = 12345;
    
    private static final ConcurrentHashMap<Integer, PlayerHandler> players = new ConcurrentHashMap<>();
    private static int playerCounter = 0;
    private static BlockingQueue<PlayerHandler> waitingPlayers = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT);
        
        System.out.println("Servidor iniciado na porta " + PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            playerCounter++;
            PlayerHandler handler = new PlayerHandler(socket, playerCounter);
            players.put(playerCounter, handler);
            new Thread(handler).start();
        }
    }

    private static class PlayerHandler implements Runnable {
        private Socket socket;
        private int playerId;
        private PrintWriter out;
        private BufferedReader in;
        private PlayerHandler opponent;
        private String move;

        public PlayerHandler(Socket socket, int playerId) {
            this.socket = socket;
            this.playerId = playerId;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println("Bem-vindo ao Jokenpo! Você é o jogador " + playerId);
                
                // Adiciona o jogador na fila de espera para emparelhamento

                waitingPlayers.put(this);
                if (waitingPlayers.size() >= 2) {
                    PlayerHandler player1 = waitingPlayers.take();
                    PlayerHandler player2 = waitingPlayers.take();
                    player1.setOpponent(player2);
                    player2.setOpponent(player1);
                    player1.out.println("Você está jogando contra o jogador " + player2.playerId);
                    player2.out.println("Você está jogando contra o jogador " + player1.playerId);
                }

                String move;
                while ((move = in.readLine()) != null) {
                    this.move = move;
                    out.println("Você jogou: " + move);
                    
                    if (opponent != null && opponent.move != null) {
                        String result = determineWinner(this.move, opponent.move);
                        out.println(result);
                        opponent.out.println(result);
                        
                        // Limpa as jogadas após determinar o vencedor
                        
                        this.move = null;
                        opponent.move = null;
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void setOpponent(PlayerHandler opponent) {
            this.opponent = opponent;
        }

        private String determineWinner(String move1, String move2) {
            
            if (move1.equals(move2)) return "Empate! Ambos jogaram " + move1;

            switch (move1) {
                case "Pedra":
                    return (move2.equals("Tesoura")) ? "Jogador " + playerId + " vence! Pedra quebra Tesoura." : "Jogador " + opponent.playerId + " vence! Papel cobre Pedra.";
                case "Papel":
                    return (move2.equals("Pedra")) ? "Jogador " + playerId + " vence! Papel cobre Pedra." : "Jogador " + opponent.playerId + " vence! Tesoura corta Papel.";
                case "Tesoura":
                    return (move2.equals("Papel")) ? "Jogador " + playerId + " vence! Tesoura corta Papel." : "Jogador " + opponent.playerId + " vence! Pedra quebra Tesoura.";
                default:
                    return "Movimento inválido";
            }
        }
    }
}
