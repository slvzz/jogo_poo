import java.util.Random;
import java.awt.Rectangle;

public class Inimigo implements Movimento {
    private int x;
    private int y;
    private int velocidade;
    private int tamanho;
    private Random aleatorio;

    private int direcaoX; // -1: esquerda, 0: parado, 1: direita
    private int direcaoY; // -1: cima, 0: parado, 1: baixo
    private int contadorMudarDirecao;
    private final int INTERVALO_MUDAR_DIRECAO = 60; // 60 frames até mudar de direção

    public Inimigo(int x, int y, int velocidade, int tamanho) {
        this.x = x;
        this.y = y;
        this.velocidade = velocidade;
        this.tamanho = tamanho;
        this.aleatorio = new Random();
        mudarDirecao(); // nesse caso, define posicao inicial
    }

    // getters inimigo
    public int getX() { return x; }
    public int getY() { return y; }
    public int getVelocidade() { return velocidade; }
    public int getTamanho() { return tamanho; }

    @Override
    public void mover(int larguraLimite, int alturaLimite) {
        x += direcaoX * velocidade;
        y += direcaoY * velocidade;

        // inverte a direção se colidir com o limite do mapa
        if (x < 0) {
            x = 0;
            direcaoX = 1;
        }
        if (x > larguraLimite - tamanho) {
            x = larguraLimite - tamanho;
            direcaoX = -1;
        }
        if (y < 0) {
            y = 0;
            direcaoY = 1;
        }
        if (y > alturaLimite - tamanho) {
            y = alturaLimite - tamanho;
            direcaoY = -1;
        }

        contadorMudarDirecao--;
        if (contadorMudarDirecao <= 0) {
            mudarDirecao();
        }
    }

    private void mudarDirecao() {
        direcaoX = aleatorio.nextInt(3) - 1; // esq, 0, dir
        direcaoY = aleatorio.nextInt(3) - 1; // cima, 0, baixo
        
        if (direcaoX == 0 && direcaoY == 0) { // nao deixa completamente parado
            if (aleatorio.nextBoolean()) direcaoX = aleatorio.nextBoolean() ? 1 : -1;
            else direcaoY = aleatorio.nextBoolean() ? 1 : -1;
        }
        contadorMudarDirecao = INTERVALO_MUDAR_DIRECAO;
    }

    // verifica se o inimigo colidiu com o jogador
    public boolean verificarColisao(int outroX, int outroY, int outroTamanho) {
        Rectangle retanguloInimigo = new Rectangle(x, y, tamanho, tamanho);
        Rectangle retanguloOutro = new Rectangle(outroX, outroY, outroTamanho, outroTamanho);
        return retanguloInimigo.intersects(retanguloOutro);
    }
}