package app;

import java.util.Scanner;

public class JogadorVSComputador {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean jogarNovamente = true;

        System.out.println("Bem vindo ao JOKENPO");

        while (jogarNovamente) {
            System.out.println("");
            System.out.println("1. Pedra");
            System.out.println("2. Papel");
            System.out.println("3. Tesoura");
            System.out.println("");
            System.out.println("..........");
            System.out.println("...............");
            System.out.println("____COMPUTADOR VS JOGADOR____");
            System.out.println("...............");
            System.out.println("..........");

            System.out.println("Digite a opção desejada | 1. Pedra | 2. Papel | 3. Tesoura ");
            int player1 = scanner.nextInt();

            switch (player1) {
                case 1:
                    System.out.println("Jogador escolheu Pedra");
                    break;
                case 2:
                    System.out.println("Jogador escolheu Papel");
                    break;
                case 3:
                    System.out.println("Jogador escolheu Tesoura");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    continue; // Volta para o início do loop se a opção for inválida
            }

            int computador = (int) (Math.random() * 3 + 1);

            System.out.println("......");
            System.out.println("..........");
            System.out.println("...............");
            System.out.println("______VEZ DO CPU_______");
            System.out.println("...............");
            System.out.println("..........");
            System.out.println("......");

            switch (computador) {
                case 1:
                    System.out.println("Computador escolheu Pedra");
                    System.out.println("......");
                    break;
                case 2:
                    System.out.println("Computador escolheu Papel");
                    System.out.println("......");
                    break;
                case 3:
                    System.out.println("Computador escolheu Tesoura");
                    System.out.println("......");
                    break;
            }

            if (player1 == computador) {
                System.out.println("EMPATE!!");
            } else {
                if ((player1 == 1 && computador == 3) || (player1 == 2 && computador == 1) || (player1 == 3 && computador == 2)) {
                    System.out.println("JOGADOR VENCEU!!");
                    System.out.println("......");
                } else {
                    System.out.println("COMPUTADOR VENCEU!!");
                    System.out.println("......");
                }
            }

            System.out.println("Você quer jogar novamente? (s/n)");
            char resposta = scanner.next().charAt(0);
            if (resposta != 's' && resposta != 'S') {
                jogarNovamente = false;
            }
        }

        System.out.println("Obrigado por jogar!");
        scanner.close();
    }
}
