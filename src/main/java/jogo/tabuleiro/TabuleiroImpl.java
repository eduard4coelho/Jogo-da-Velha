package src.main.java.jogo.tabuleiro;

public class TabuleiroImpl implements Tabuleiro {
    private String[] tabuleiro;

    public TabuleiroImpl() {
        tabuleiro = new String[9];
        resetTabuleiro();
    }

    @Override
    public boolean fazerJogada(int posicao, String simbolo) {
        if (tabuleiro[posicao].equals(" ")) {
            tabuleiro[posicao] = simbolo;
            return true;
        }
        return false;
    }

    @Override
    public String[] getTabuleiro() {
        return tabuleiro;
    }

    @Override
    public void resetTabuleiro() {
        for (int i = 0; i < 9; i++) {
            tabuleiro[i] = " ";
        }
    }

    @Override
    public boolean isTabuleiroCheio() {
        for (String pos : tabuleiro) {
            if (pos.equals(" ")) {
                return false;
            }
        }
        return true;
    }
}