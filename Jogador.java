import java.awt.Rectangle;

public class Jogador implements Movimento {
    private int x;
    private int y;
    private int velocidade;
    private int tamanho;
    private boolean movendoCima, movendoBaixo, movendoEsquerda, movendoDireita;

    public Jogador(int x, int y, int velocidade, int tamanho) {
        this.x = x;
        this.y = y;
        this.velocidade = velocidade;
        this.tamanho = tamanho;
    }

    //getters jogador
    public int getX() { return x; }
    public int getY() { return y; }
    public int getVelocidade() { return velocidade; }
    public int getTamanho() { return tamanho; }

    //setters movimentos
    public void setMovendoCima(boolean movendoCima) { this.movendoCima = movendoCima; }
    public void setMovendoBaixo(boolean movendoBaixo) { this.movendoBaixo = movendoBaixo; }
    public void setMovendoEsquerda(boolean movendoEsquerda) { this.movendoEsquerda = movendoEsquerda; }
    public void setMovendoDireita(boolean movendoDireita) { this.movendoDireita = movendoDireita; }

    @Override
    public void mover(int larguraLimite, int alturaLimite) {
        if (movendoCima) {
            y -= velocidade;
        }
        if (movendoBaixo) {
            y += velocidade;
        }
        if (movendoEsquerda) {
            x -= velocidade;
        }
        if (movendoDireita) {
            x += velocidade;
        }

        // limita o jogador dentro dos limites da tela
        if (x < 0) x = 0;
        if (x > larguraLimite - tamanho) x = larguraLimite - tamanho;
        if (y < 0) y = 0;
        if (y > alturaLimite - tamanho) y = alturaLimite - tamanho;
    }

    // intersects valida um retangulo sobreposto ao outro (colisao)
    public boolean verificarColisao(int outroX, int outroY, int outroTamanho) {
        Rectangle retanguloJogador = new Rectangle(x, y, tamanho, tamanho);
        Rectangle retanguloOutro = new Rectangle(outroX, outroY, outroTamanho, outroTamanho);
        return retanguloJogador.intersects(retanguloOutro);
    }
}