package src.main.java.jogo.jogo;

import src.main.java.jogo.observer.Observer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;

public class JogoVelhaUI extends JFrame implements Observer {
    private JButton[] buttons;
    private JogoVelha jogo;
    private JLabel guia;
    private JLabel pontuacaoJogador;
    private JLabel pontuacaoComputador;
    private String nomeJogador;

    private static final Color COR_JOGADOR = new Color(200, 25, 60); // #C8193C
    private static final Color COR_COMPUTADOR = new Color(70, 50, 57); // #463239
    private static final Color COR_VENCEU = new Color(84, 209, 31);

    public JogoVelhaUI(JogoVelha jogo) {
        this.jogo = jogo;
        this.jogo.registerObserver(this);

        setTitle("Jogo da Velha");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        nomeJogador = JOptionPane.showInputDialog("Digite seu nome:");
        if (nomeJogador == null || nomeJogador.isEmpty()) {
            nomeJogador = "Jogador";
        }

        buttons = new JButton[9];
        
        // Define a fonte para os textos
        Font font = new Font("Arial", Font.BOLD, 18); // Fonte maior

        guia = new JLabel(nomeJogador + ", sua vez!");
        guia.setFont(font); // Define a fonte do guia

        pontuacaoJogador = new JLabel("Jogador (" + nomeJogador + "): 0");
        pontuacaoJogador.setFont(font); // Define a fonte da pontuação do jogador

        pontuacaoComputador = new JLabel("Computador: 0");
        pontuacaoComputador.setFont(font); // Define a fonte da pontuação do computador

        // Cria um painel para os textos
        JPanel painelTexto = new JPanel();
        painelTexto.setLayout(new BoxLayout(painelTexto, BoxLayout.Y_AXIS)); // Layout vertical
        painelTexto.setBorder(BorderFactory.createEmptyBorder(30, 25, 30, 0)); // Adiciona padding à esquerda
        painelTexto.add(pontuacaoJogador);
        painelTexto.add(Box.createRigidArea(new Dimension(0, 10))); // Espaçamento entre textos
        painelTexto.add(pontuacaoComputador);
        painelTexto.add(Box.createRigidArea(new Dimension(0, 10))); // Espaçamento entre textos
        painelTexto.add(guia);

        // Adiciona o painel de textos ao painel principal
        add(painelTexto, BorderLayout.SOUTH);

        initializeButtons();
    }

    // Aqui monta o tabuleiro
    private void initializeButtons() {
        Font buttonFont = new Font("Arial", Font.BOLD, 60); // Fonte maior para os botões

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(3, 3)); // Layout para os botões

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton(" ");
            buttons[i].setFont(buttonFont); // Define a fonte do botão
            buttons[i].setForeground(Color.BLACK); // Cor padrão para o texto
            final int index = i;

            // Quando o jogador clica em um dos botões do tabuleiro
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handlePlayerMove(index); // Aqui faz a jogada no backend, e atualiza a interface do tabuleiro com a jogada 
                }
            });
            painelBotoes.add(buttons[i]); // aqui adiciona de fato os botões do tabuleiro
        }

        add(painelBotoes, BorderLayout.CENTER); // Aqui centraliza o tabuleiro
    }

    private void handlePlayerMove(int index) {
        if (jogo.fazerJogada(index, "X")) {
            buttons[index].setText("X");
            buttons[index].setForeground(COR_JOGADOR); // Define a cor do texto para o jogador
            int[] posicoesVencedoras = jogo.verificaVitoria("X");
            if (posicoesVencedoras != null) {
                destacarVitoria(posicoesVencedoras);
                JOptionPane.showMessageDialog(null, nomeJogador + " venceu!");
                resetTabuleiro();
            } else if (jogo.isTabuleiroCheio()) {
                JOptionPane.showMessageDialog(null, "Empate!");
                resetTabuleiro();
            } else {
                jogadaComputador();
            }
        }
    }

    private void destacarVitoria(int[] posicoesVencedoras) {
        for (int pos : posicoesVencedoras) {
            buttons[pos].setBackground(COR_VENCEU);
        }
    }

    private void jogadaComputador() {
        int pos = new java.util.Random().nextInt(9);
        while (!jogo.fazerJogada(pos, "O")) {
            pos = new java.util.Random().nextInt(9);
        }
        buttons[pos].setText("O");
        buttons[pos].setForeground(COR_COMPUTADOR); // Define a cor do texto para o computador

        int[] posicoesVencedoras = jogo.verificaVitoria("O");
        if (posicoesVencedoras != null) {
            destacarVitoria(posicoesVencedoras);
            JOptionPane.showMessageDialog(null, "O Computador venceu!");
            resetTabuleiro();
        } else if (jogo.isTabuleiroCheio()) {
            JOptionPane.showMessageDialog(null, "Empate!");
            resetTabuleiro();
        } else {
            guia.setText(nomeJogador + ", sua vez!");
        }
    }

    private void resetTabuleiro() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setText(" ");
            buttons[i].setBackground(UIManager.getColor("Button.background"));
            buttons[i].setForeground(Color.BLACK); // Restaura a cor padrão
        }
        jogo.resetTabuleiro();
        atualizarPontuacao();
    }

    private void atualizarPontuacao() {
        pontuacaoJogador.setText("Jogador (" + nomeJogador + "): " + jogo.getPontuacaoJogador());
        pontuacaoComputador.setText("Computador: " + jogo.getPontuacaoComputador());
    }

    @Override
    public void update(String message) {
        guia.setText(message);
        System.out.println(message);
    }

    public static void main(String[] args) {
        JogoVelha jogo = new JogoVelha();
        JogoVelhaUI ui = new JogoVelhaUI(jogo);
        ui.setVisible(true);
    }
}