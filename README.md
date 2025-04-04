## JOKENPO

    Jokenpô é uma brincadeira de recreação para crianças com origem no Japão. O jogo consiste em

escolher entre pedra, papel ou tesoura e dependendo da combinação realizada é determinado um
vencedor. As combinações são as seguintes:

• Pedra ganha da tesoura (amassando-a ou quebrando-a).
• Tesoura ganha do papel (cortando-o).
• Papel ganha da pedra(embrulhando-a).

    Programa distribuído em Java, onde temos duas modalidades de jogos:

• Jogador Vs CPU: o jogador será o usuário do programa e o outro um computador (servidor)
que irá escolher de maneira aleatória uma das três opções de jogo.

• Jogador Vs Jogador: dois jogadores conectados cada um em uma máquina jogando um
contra o outro.

## Para execultar o projeto

1 - javac -d . src/server/JokenpoServer.java src/client/JokenpoClient.java src/app/JogadorVSJogador.java

2- java server.JokenpoServer

Abindo outro terminal para outro jogador

3- java client.JokenpoClient

127.0.0.1
