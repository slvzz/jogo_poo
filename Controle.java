import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controle extends KeyAdapter {
    private Jogo jogo; 

    public Controle(Jogo jogo) {
        this.jogo = jogo;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        Jogador jogador = jogo.getFaseAtual() != null ? jogo.getFaseAtual().getJogador() : null;

        if (jogador != null && jogo.getEstadoJogo() == EstadoJogo.RODANDO) {
            if (tecla == KeyEvent.VK_UP || tecla == KeyEvent.VK_W) {
                jogador.setMovendoCima(true);
            }
            if (tecla == KeyEvent.VK_DOWN || tecla == KeyEvent.VK_S) {
                jogador.setMovendoBaixo(true);
            }
            if (tecla == KeyEvent.VK_LEFT || tecla == KeyEvent.VK_A) {
                jogador.setMovendoEsquerda(true);
            }
            if (tecla == KeyEvent.VK_RIGHT || tecla == KeyEvent.VK_D) {
                jogador.setMovendoDireita(true);
            }
        }

        // logicas de estado do jogo
        if (tecla == KeyEvent.VK_SPACE) {
            if (jogo.getEstadoJogo() == EstadoJogo.TELA_INICIAL || jogo.getEstadoJogo() == EstadoJogo.FASE_COMPLETA) {
                jogo.iniciarProximaFase();
            }
        }

        if (tecla == KeyEvent.VK_R) {
            if (jogo.getEstadoJogo() == EstadoJogo.FIM_DE_JOGO) {
                jogo.reiniciarJogo();
            }
        }

        if (tecla == KeyEvent.VK_P) {
            if (jogo.getEstadoJogo() == EstadoJogo.RODANDO) {
                jogo.setEstadoJogo(EstadoJogo.PAUSADO);
            } else if (jogo.getEstadoJogo() == EstadoJogo.PAUSADO) {
                jogo.setEstadoJogo(EstadoJogo.RODANDO);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int tecla = e.getKeyCode();
        Jogador jogador = jogo.getFaseAtual() != null ? jogo.getFaseAtual().getJogador() : null;

        if (jogador != null && jogo.getEstadoJogo() == EstadoJogo.RODANDO) {
            if (tecla == KeyEvent.VK_UP || tecla == KeyEvent.VK_W) {
                jogador.setMovendoCima(false);
            }
            if (tecla == KeyEvent.VK_DOWN || tecla == KeyEvent.VK_S) {
                jogador.setMovendoBaixo(false);
            }
            if (tecla == KeyEvent.VK_LEFT || tecla == KeyEvent.VK_A) {
                jogador.setMovendoEsquerda(false);
            }
            if (tecla == KeyEvent.VK_RIGHT || tecla == KeyEvent.VK_D) {
                jogador.setMovendoDireita(false);
            }
        }
    }
}