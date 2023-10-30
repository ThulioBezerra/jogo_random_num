
public class Jogador {
    private int num = 0;
    private int vidas = 0;
    private String nome = "";
    private int palpite = 0;
    private int pontuacao = 0;
    private boolean status = true;
    public Jogador(String nome, int vidas) {
        setVidas(vidas);
        setNome(nome);
    }

    public void setNome(String name) {
        this.nome = name;
    }

    public void setVidas(int life) {
        this.vidas = life;
    }

    public void setNumeros(int numeros) {
        this.num = numeros;
    }

    public void setPalpite(int palpite) {
        this.palpite = palpite;
    }

    public void setPoint(int point) {
        this.pontuacao = point;
    }

    public void setStatus(boolean stts) {
        this.status = stts;
    }

    public int getVidas() {
        return this.vidas;
    }

    public int getNumeros() {
        return this.num;
    }

    public String getNome() {
        return this.nome;
    }

    public int getPalpite() {
        return this.palpite;
    }

    public int getPontuacao() {
        return this.pontuacao;
    }

    public boolean getStatus() {
        return this.status;
    }
}