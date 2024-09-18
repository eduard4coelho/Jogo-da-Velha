package src.main.java.jogo.tabuleiro;

public interface Tabuleiro {
    boolean fazerJogada(int posicao, String simbolo);
    String[] getTabuleiro();
    void resetTabuleiro();
    boolean isTabuleiroCheio();
}
