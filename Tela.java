import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Tela extends JPanel implements ActionListener {

    private Jogo jogo; 
    private Timer temporizadorJogo;
    private final int ATRASO = 15; // atraso em milissegundos para o timer
    public Tela(Jogo jogo) {
        this.jogo = jogo;
        setPreferredSize(new java.awt.Dimension(800, 600));
        setBackground(Color.WHITE);
        setFocusable(true); // add eventos de teclado

        addKeyListener(new Controle(this.jogo)); //add ouvinte de teclado

        temporizadorJogo = new Timer(ATRASO, this);
        temporizadorJogo.start(); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (jogo.getEstadoJogo() == EstadoJogo.RODANDO) {
            jogo.atualizarJogo(); // muda o estado do jogo
        }
        repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (jogo.getFaseAtual() != null) {
            Fase faseAtual = jogo.getFaseAtual();
            Jogador jogador = faseAtual.getJogador();
            java.util.List<Inimigo> inimigos = faseAtual.getInimigos();

            // jogador
            g2d.setColor(Color.BLUE);
            g2d.fillRect(jogador.getX(), jogador.getY(), jogador.getTamanho(), jogador.getTamanho());

            // inimigo
            g2d.setColor(Color.RED);
            for (Inimigo inimigo : inimigos) {
                g2d.fillRect(inimigo.getX(), inimigo.getY(), inimigo.getTamanho(), inimigo.getTamanho());
            }

            // objetivo
            g2d.setColor(Color.GREEN);
            g2d.fillRect(faseAtual.getObjetivoX(), faseAtual.getObjetivoY(), faseAtual.getTamanhoObjetivo(), faseAtual.getTamanhoObjetivo());

            // numero da fase
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("Fase: " + faseAtual.getNumeroFase(), 10, 25);
        }

        // mensagens
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 40));

        switch (jogo.getEstadoJogo()) {
            case TELA_INICIAL:
                desenharStringCentralizada(g2d, "FUGA DOS INIMIGOS", getWidth(), getHeight() / 2 - 50);
                g2d.setFont(new Font("Arial", Font.PLAIN, 20));
                desenharStringCentralizada(g2d, "Pressione ESPAÇO para Iniciar", getWidth(), getHeight() / 2);
                break;
            case FIM_DE_JOGO:
                desenharStringCentralizada(g2d, "FIM DE JOGO!", getWidth(), getHeight() / 2 - 30);
                g2d.setFont(new Font("Arial", Font.PLAIN, 20));
                desenharStringCentralizada(g2d, "Pressione R para Reiniciar", getWidth(), getHeight() / 2 + 10);
                break;
            case FASE_COMPLETA:
                desenharStringCentralizada(g2d, "FASE " + jogo.getFaseAtual().getNumeroFase() + " COMPLETA!", getWidth(), getHeight() / 2 - 30);
                g2d.setFont(new Font("Arial", Font.PLAIN, 20));
                desenharStringCentralizada(g2d, "Pressione ESPAÇO para Próxima Fase", getWidth(), getHeight() / 2 + 10);
                break;
            case PAUSADO:
                desenharStringCentralizada(g2d, "PAUSADO", getWidth(), getHeight() / 2);
                g2d.setFont(new Font("Arial", Font.PLAIN, 20));
                desenharStringCentralizada(g2d, "Pressione P para Continuar", getWidth(), getHeight() / 2 + 30);
                break;
            default:
                break;
        }
    }

    // centraliza mensagens
    private void desenharStringCentralizada(Graphics2D g2d, String texto, int larguraPainel, int y) {
        int x = (larguraPainel - g2d.getFontMetrics().stringWidth(texto)) / 2;
        g2d.drawString(texto, x, y);
    }
}