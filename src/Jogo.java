import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Jogo {
    private Random random;
    private Catch catcher;

    public void pedir_cadastro(Jogador jogadores[]) throws InterruptedException, IOException {
        String nome;
        int vidas;
        random = new Random();
        catcher = new Catch();
        for (int i = 0; i < jogadores.length; i++) {
            System.out.printf("Digite o nome do %dº jogador: ", i + 1);
            nome = catcher.catch_String();
            vidas = random.nextInt(1, 4);
            jogadores[i] = new Jogador(nome, vidas);
            System.out.printf("\nA quantidade de vidas do %dº jogador: %d \n", i + 1, vidas);
            System.out.println("Tudo certo! Novo Jogador cadastrado! \n-----------------------------\n");
        }
        System.out.println("Tudo pronto para as partidas começarem! ");
        System.out.println("Pressione enter para continuar :)");
        catcher.catch_String();
        TimeUnit.SECONDS.sleep(2);
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    public int rodada(int palpite1, int palpite2, Jogador[] jogadores, int[] chosen) {
        /// Declarações
        //
        jogadores[chosen[0]].setPalpite(palpite1);
        jogadores[chosen[1]].setPalpite(palpite2);
        int jogador1[] = new int[20];
        int jogador2[] = new int[20];
        int num_jogador1 = jogadores[chosen[0]].getNumeros();
        int num_jogador2 = jogadores[chosen[1]].getNumeros();
        //
        /// Criação da margem de erro
        //
        for (int i = 0; i < 10; i++) {
            jogador1[i] = num_jogador1 - (i + 1);
            jogador1[i + 10] = num_jogador1 + (i + 1);
            jogador2[i] = num_jogador2 - i;
            jogador2[i + 10] = num_jogador1 + (i + 1);
        }
        //
        /// Testes
        //
        if (jogadores[chosen[0]].getPalpite() == jogadores[chosen[1]].getNumeros()) {
            return 1;
        } else if (jogadores[chosen[1]].getPalpite() == jogadores[chosen[0]].getNumeros()) {
            return 2;
        } else {
            for (int i = 0; i < 20; i++) {
                if (jogador1[i] == jogadores[chosen[1]].getPalpite()) {
                    int points = jogadores[chosen[1]].getPontuacao();
                    if (i >= 5) {
                        jogadores[chosen[1]].setPoint(points + 1);
                    } else if (i < 5 && i >= 3) {
                        jogadores[chosen[1]].setPoint(points + 3);
                    } else {
                        jogadores[chosen[1]].setPoint(points + 5);
                    }
                } else if (jogador2[i] == jogadores[chosen[0]].getPalpite()) {
                    int points = jogadores[chosen[0]].getPontuacao();
                    if (i >= 5) {
                        jogadores[chosen[0]].setPoint(points + 1);
                    } else if (i < 5 && i >= 3) {

                        jogadores[chosen[0]].setPoint(points + 3);
                    } else {
                        jogadores[chosen[0]].setPoint(points + 5);
                    }
                }
                if (jogadores[chosen[0]].getPontuacao() >= 7) {
                    return -1;
                } else if (jogadores[chosen[1]].getPontuacao() >= 7) {
                    return -2;
                }
            }
        }
        return 0;
    }

    public void resultado(int cont, int[][] placar, Jogador[] jogadores) {
        for (int i = 0; i < cont; i++) {
            System.out.printf("Rodadas de %s: ", jogadores[i].getNome());
            for (int j = 0; j < cont; j++) {
                if (i != j) {
                    System.out.print(placar[i][j] + " ");
                }
            }
            System.out.println("");
        }
    }

    public int[] catch_players(Jogador jogadores[]) {
        int[] the_chosen = new int[2];
        do {
            the_chosen[0] = random.nextInt(0, jogadores.length);
            the_chosen[1] = random.nextInt(0, jogadores.length);
        } while (the_chosen[0] == the_chosen[1] || jogadores[the_chosen[1]].getStatus() == false
                || jogadores[the_chosen[0]].getStatus() == false);

        return the_chosen;

    }

    public void inicio() throws InterruptedException{
        final String GREEN = "\u001B[32m";
        final String RESET = "\u001B[0m";

        System.out.println(GREEN + """
            ____ ____ ____ ____ ____ 
            ||G |||U |||E |||S |||S ||
            ||__|||__|||__|||__|||__||
            |/__\\|/__\\|/__\\|/__\\|/__\\|""");
            TimeUnit.SECONDS.sleep(1);
            System.out.println(
                    """
            ____ ____ ____ ____ ____ ____ 
            ||N |||U |||M |||B |||E |||R ||
            ||__|||__|||__|||__|||__|||__||
            |/__\\|/__\\|/__\\|/__\\|/__\\|/__\\|
            
            
        """ + RESET);
        TimeUnit.SECONDS.sleep(2);
        System.out.println(
                """
                        Regras:
                        1 - No mínimo, devem estar dispostos dois jogadores.
                        2 - O jogo consiste em conseguir acertar o número do oponente (1 a 100).
                        3 - Existem vidas (De 1 a 3).
                        4 - Sistema de pontos:
                            4.1 - O número está dez digitos de diferença: 1 ponto.
                            4.2 - O número está cinco digitos de diferença: 3 pontos.
                            4.3 - O número está 2 digitos de diferença: 5 pontos:
                        5 - Ao conquistar mais do que 7 pontos, o jogador terá a possibilidade de chance dupla.
                        6 - Derrota o adversário quem acertar o número em cheio.
                        """);
    }

    public int[][] checar_resultado(int resultado, Jogador[] jogadores, int[] players, int[][] placar)
            throws IOException, InterruptedException {
        int palpite1 = -10, palpite2 = -10;
        switch (resultado) {
            case 1:
                System.out.printf("%s acertou em cheio! %s foi derrotado!", jogadores[players[0]].getNome(),
                        jogadores[players[1]].getNome());
                jogadores[players[1]].setVidas(jogadores[players[1]].getVidas() - 1);
                placar[players[0]][players[1]] += 1;
                jogadores[players[0]].setPoint(0);
                jogadores[players[1]].setPoint(0);
                break;
            case 2:
                System.out.printf("%s acertou em cheio! %s foi derrotado!",
                        jogadores[players[1]].getNome(),
                        jogadores[players[0]].getNome());
                jogadores[players[0]].setVidas(jogadores[players[0]].getVidas() - 1);
                placar[players[1]][players[0]] += 1;
                jogadores[players[0]].setPoint(0);
                jogadores[players[1]].setPoint(0);
                break;
            case -1:
                System.out.printf("O jogador %s está tão próximo que terá mais uma chance. \n",
                        jogadores[players[0]].getNome());
                System.out.println("Digita e ganha: ");
                palpite1 = catcher.catch_int();
                resultado = rodada(palpite1, -10, jogadores, players);
                if (resultado == 1) {
                    System.out.printf("%s acertou em cheio! %s foi derrotado!",
                            jogadores[players[0]].getNome(),
                            jogadores[players[1]].getNome());
                    placar[players[0]][players[1]] += 1;
                    jogadores[players[1]].setVidas(jogadores[players[1]].getVidas() - 1);
                    jogadores[players[0]].setPoint(0);
                    jogadores[players[1]].setPoint(0);
                    TimeUnit.SECONDS.sleep(3);
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } else {
                    jogadores[players[0]].setPoint(0);
                }
                break;
            case -2:
                System.out.printf("O jogador %s está tão próximo que terá mais uma chance. \n",
                        jogadores[players[1]].getNome());
                System.out.println("Digita e ganha: ");
                palpite2 = catcher.catch_int();
                resultado = rodada(-10, palpite2, jogadores, players);
                if (resultado == 2) {
                    System.out.printf("%s acertou em cheio! %s foi derrotado!",
                            jogadores[players[1]].getNome(),
                            jogadores[players[0]].getNome());
                    placar[players[1]][players[0]] += 1;
                    jogadores[players[0]].setVidas(jogadores[players[0]].getVidas() - 1);
                    jogadores[players[0]].setPoint(0);
                    jogadores[players[1]].setPoint(0);
                    TimeUnit.SECONDS.sleep(3);
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } else {
                    jogadores[players[1]].setPoint(0);
                }
                break;
            default:
                System.out.printf("""
                    ----------------------------
                        Jogada encerrada!
                        %s: %d pontos.
                        %s: %d pontos.
                    ----------------------------\n
                        """, jogadores[players[0]].getNome(), jogadores[players[0]].getPontuacao(),
                        jogadores[players[1]].getNome(),
                        jogadores[players[1]].getPontuacao());
                break;
        }
        if (jogadores[players[0]].getVidas() == 0) {
            jogadores[players[0]].setStatus(false);
        } else if (jogadores[players[1]].getVidas() == 0) {
            jogadores[players[1]].setStatus(false);
        }
        return placar;
    }

    public int check_win(Jogador[] jogadores) {
        int vencedor = 0;
        for (int i = 0; i < jogadores.length; i++) {
            if (jogadores[i].getStatus() == true) {
                vencedor = i;
            }
        }
        return vencedor;
    }
}