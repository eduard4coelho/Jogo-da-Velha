package src.main.java.jogo.pontuacao;

public interface Pontuacao {
    void incrementarPontuacao(String simbolo);
    int getPontuacaoJogador();
    int getPontuacaoComputador();
}