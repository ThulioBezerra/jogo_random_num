import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        /// Declarações da Main
        Random random = new Random();
        Catch catcher = new Catch();
        Jogo jogo_main = new Jogo();
        boolean winner = false;
        int palpite1 = -10, palpite2 = -10, resultado, cont_winner = 0;
        int[] players = new int[2];
        int cont = 0;
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        // inicio
        jogo_main.inicio();
        do {
            System.out.println("Quantos jogadores queres adicionar? (min. 2)");
            cont = catcher.catch_int();
        } while (cont < 2);
        Jogador[] jogadores = new Jogador[cont];
        int[][] placar = new int[cont][cont];
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        /// Cadastro de Jogadores
        jogo_main.pedir_cadastro(jogadores);
        /// Início do Jogo
        while (winner == false) {
            System.out.println("Escolhendo jogadores...");
            TimeUnit.SECONDS.sleep(2);
            players = jogo_main.catch_players(jogadores);
            jogadores[players[0]].setNumeros(random.nextInt(1, 100));
            jogadores[players[1]].setNumeros(random.nextInt(1, 100));
            System.out.println("Okay! Os jogadores escolhidos foram: " + jogadores[players[0]].getNome() + " e "
                    + jogadores[players[1]].getNome() + "! Boa sorte!");
            TimeUnit.SECONDS.sleep(2);
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            System.out.printf("""
                    Começo de Jogo!\n
                    Número de vidas do jogador %s: %d.
                    Número de vidas do jogador  %s: %d. \n
                    """, jogadores[players[0]].getNome(), jogadores[players[0]].getVidas(),
                    jogadores[players[1]].getNome(),
                    jogadores[players[1]].getVidas());
            TimeUnit.SECONDS.sleep(3);
            do {
                do {
                    try {
                        System.out.println("Qual o seu palpite, " + jogadores[players[0]].getNome() + "?");
                        palpite1 = catcher.catch_int();
                        System.out.println("Qual o seu palpite, " + jogadores[players[1]].getNome() + "?");
                        palpite2 = catcher.catch_int();
                    } catch (Exception e) {
                        System.out.println("Hmm, algo deu errado. Vamos tentar de novo? \n");
                    }
                } while (palpite1 != (int) palpite1 || palpite2 != (int) palpite2);
                resultado = jogo_main.rodada(palpite1, palpite2, jogadores, players);
                placar = jogo_main.checar_resultado(resultado, jogadores, players, placar);
            } while (resultado != 1 && resultado != 2);
            System.out.printf("\n Rodada encerrada. \n O número do 1º jogador: %d \n O número do 2º jogador: %d",
                    jogadores[players[0]].getNumeros(), jogadores[players[1]].getNumeros());
            TimeUnit.SECONDS.sleep(5);
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            for (int i = 0; i < jogadores.length; i++) {
                if (jogadores[i].getStatus() != false) {
                    cont_winner++;
                }
            }
            if (cont_winner > 1) {
                cont_winner = 0;
            } else {
                winner = true;
            }
        }
        int id_vencedor = jogo_main.check_win(jogadores);
        System.out.printf("Fim do jogo. O vencedor foi %s.\nPlacar: \n", jogadores[id_vencedor].getNome());
        jogo_main.resultado(cont, placar, jogadores);
    }
}
