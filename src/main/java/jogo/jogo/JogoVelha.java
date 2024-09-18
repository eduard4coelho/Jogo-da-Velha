package src.main.java.jogo.jogo;

import src.main.java.jogo.pontuacao.Pontuacao;
import src.main.java.jogo.pontuacao.PontuacaoImpl;
import src.main.java.jogo.observer.Observer ;
import src.main.java.jogo.observer.Subject;
import src.main.java.jogo.tabuleiro.Tabuleiro;
import src.main.java.jogo.tabuleiro.TabuleiroImpl;

import java.util.ArrayList;
import java.util.List;

public class JogoVelha implements Subject {
    private Tabuleiro tabuleiro;
    private Pontuacao pontuacao;
    private List<Observer> observers;

    public JogoVelha() {
        tabuleiro = new TabuleiroImpl();
        pontuacao = new PontuacaoImpl();
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public boolean fazerJogada(int posicao, String simbolo) {
        String jogadorName = "Computador";
        if(simbolo == "X") {
            jogadorName = "Jogador";
        }   

        if (tabuleiro.fazerJogada(posicao, simbolo)) {
            notifyObservers(jogadorName + " fez uma jogada em " + posicao);
            return true;
        }
        return false;
    }

    public String[] getTabuleiro() {
        return tabuleiro.getTabuleiro();
    }

    public int[] verificaVitoria(String simbolo) {
        // Verificação de linhas
        for (int i = 0; i < 9; i += 3) {
            if (tabuleiro.getTabuleiro()[i].equals(simbolo) && 
                tabuleiro.getTabuleiro()[i + 1].equals(simbolo) && 
                tabuleiro.getTabuleiro()[i + 2].equals(simbolo)) {
                pontuacao.incrementarPontuacao(simbolo);
                notifyObservers(simbolo.equals("X") ? "Jogador venceu!" : "Computador venceu!");
                return new int[] {i, i + 1, i + 2};  // Retorna as posições da linha vencedora
            }
        }
    
        // Verificação de colunas
        for (int i = 0; i < 3; i++) {
            if (tabuleiro.getTabuleiro()[i].equals(simbolo) && 
                tabuleiro.getTabuleiro()[i + 3].equals(simbolo) && 
                tabuleiro.getTabuleiro()[i + 6].equals(simbolo)) {
                pontuacao.incrementarPontuacao(simbolo);
                notifyObservers(simbolo.equals("X") ? "Jogador venceu!" : "Computador venceu!");
                return new int[] {i, i + 3, i + 6};  // Retorna as posições da coluna vencedora
            }
        }
    
        // Verificação de diagonais
        if (tabuleiro.getTabuleiro()[0].equals(simbolo) && 
            tabuleiro.getTabuleiro()[4].equals(simbolo) && 
            tabuleiro.getTabuleiro()[8].equals(simbolo)) {
            pontuacao.incrementarPontuacao(simbolo);
            notifyObservers(simbolo.equals("X") ? "Jogador venceu!" : "Computador venceu!");
            return new int[] {0, 4, 8};  // Retorna as posições da diagonal principal
        }
    
        if (tabuleiro.getTabuleiro()[2].equals(simbolo) && 
            tabuleiro.getTabuleiro()[4].equals(simbolo) && 
            tabuleiro.getTabuleiro()[6].equals(simbolo)) {
            pontuacao.incrementarPontuacao(simbolo);
            notifyObservers(simbolo.equals("X") ? "Jogador venceu!" : "Computador venceu!");
            return new int[] {2, 4, 6};  // Retorna as posições da diagonal secundária
        }
    
        return null;  // Não houve vitória
    }

    public boolean isTabuleiroCheio() {
        if (tabuleiro.isTabuleiroCheio()) {
            notifyObservers("Empate!");
            return true;
        }
        return false;
    }

    public void resetTabuleiro() {
        tabuleiro.resetTabuleiro();
        notifyObservers("Novo jogo iniciado");
    }

    public int getPontuacaoJogador() {
        return pontuacao.getPontuacaoJogador();
    }

    public int getPontuacaoComputador() {
        return pontuacao.getPontuacaoComputador();
    }
}
