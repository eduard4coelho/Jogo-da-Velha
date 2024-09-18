package src.main.java.jogo.pontuacao;

public class PontuacaoImpl implements Pontuacao {
    private int pntJogador;
    private int pntComputador;

    @Override
    public void incrementarPontuacao(String simbolo) {
        if (simbolo.equals("X")) {
            pntJogador++;
        } else if (simbolo.equals("O")) {
            pntComputador++;
        }
    }

    @Override
    public int getPontuacaoJogador() {
        return pntJogador;
    }

    @Override
    public int getPontuacaoComputador() {
        return pntComputador;
    }
}