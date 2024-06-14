package app;

// Classe que determina o vencedor entre dois jogadores
public class JogadorVSJogador {

    // Método que determina o vencedor com base nas jogadas dos jogadores
    public static String determineWinner(String move1, String move2) {
        // Se as jogadas forem iguais, é um empate
        if (move1.equals(move2)) return "Empate";

        // Estrutura switch para determinar o vencedor com base nas jogadas
        switch (move1) {
            case "Pedra":
                return (move2.equals("Tesoura")) ? "Jogador 1 vence" : "Jogador 2 vence";
            case "Papel":
                return (move2.equals("Pedra")) ? "Jogador 1 vence" : "Jogador 2 vence";
            case "Tesoura":
                return (move2.equals("Papel")) ? "Jogador 1 vence" : "Jogador 2 vence";
            default:
                // Caso a jogada seja inválida
                return "Movimento inválido";
        }
    }
}
