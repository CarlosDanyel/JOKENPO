package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class JokenpoServer {

    private static final int PORT = 12345;  // Porta do servidor

    // Mapa para armazenar os jogadores conectados
    private static final ConcurrentHashMap<Integer, PlayerHandler> players = new ConcurrentHashMap<>();
    private static int playerCounter = 0;  // Contador de jogadores
    // Fila de espera para emparelhamento de jogadores
    private static BlockingQueue<PlayerHandler> waitingPlayers = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);  // Cria o socket do servidor
        System.out.println("Servidor iniciado na porta " + PORT);  // Mensagem de início do servidor

        while (true) {
            Socket socket = serverSocket.accept();  // Aceita conexões de clientes
            playerCounter++;  // Incrementa o contador de jogadores
            PlayerHandler handler = new PlayerHandler(socket, playerCounter);  // Cria um manipulador para o jogador
            players.put(playerCounter, handler);  // Adiciona o jogador ao mapa
            new Thread(handler).start();  // Inicia a thread do manipulador do jogador
        }
    }

    // Classe interna para manipular as conexões dos jogadores
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
                out = new PrintWriter(socket.getOutputStream(), true);  // Inicializa o PrintWriter
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  // Inicializa o BufferedReader

                out.println("Bem-vindo ao Jokenpo! Você é o jogador " + playerId);  // Envia mensagem de boas-vindas
                
                // Adiciona o jogador na fila de espera para emparelhamento
                waitingPlayers.put(this);
                if (waitingPlayers.size() >= 2) {
                    PlayerHandler player1 = waitingPlayers.take();  // Remove o primeiro jogador da fila
                    PlayerHandler player2 = waitingPlayers.take();  // Remove o segundo jogador da fila
                    player1.setOpponent(player2);  // Define o adversário do primeiro jogador
                    player2.setOpponent(player1);  // Define o adversário do segundo jogador
                    player1.out.println("Você está jogando contra o jogador " + player2.playerId);  // Informa ao primeiro jogador o ID do adversário
                    player2.out.println("Você está jogando contra o jogador " + player1.playerId);  // Informa ao segundo jogador o ID do adversário
                }

                String move;
                while ((move = in.readLine()) != null) {  // Lê a jogada do jogador
                    this.move = move;
                    out.println("Você jogou: " + move);  // Confirma a jogada para o jogador
                    
                    if (opponent != null && opponent.move != null) {  // Se ambos os jogadores tiverem jogado
                        String result = determineWinner(this.move, opponent.move);  // Determina o vencedor
                        out.println(result);  // Envia o resultado ao jogador
                        opponent.out.println(result);  // Envia o resultado ao adversário
                        
                        // Limpa as jogadas após determinar o vencedor
                        this.move = null;
                        opponent.move = null;
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();  // Imprime a pilha de exceções em caso de erro
            } finally {
                try {
                    socket.close();  // Fecha o socket
                } catch (IOException e) {
                    e.printStackTrace();  // Imprime a pilha de exceções em caso de erro
                }
            }
        }

        public void setOpponent(PlayerHandler opponent) {
            this.opponent = opponent;  // Define o adversário
        }

        // Método que determina o vencedor com base nas jogadas dos jogadores
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
