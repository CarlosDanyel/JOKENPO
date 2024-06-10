package app;

public class JogadorVSJogador {

    public static String determineWinner(String move1, String move2) {
        if (move1.equals(move2)) return "Empate";

        switch (move1) {
            case "Pedra":
                return (move2.equals("Tesoura")) ? "Jogador 1 vence" : "Jogador 2 vence";
            case "Papel":
                return (move2.equals("Pedra")) ? "Jogador 1 vence" : "Jogador 2 vence";
            case "Tesoura":
                return (move2.equals("Papel")) ? "Jogador 1 vence" : "Jogador 2 vence";
            default:
                return "Movimento inválido";
        }
    }
}
