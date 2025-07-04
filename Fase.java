import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Rectangle; 

public class Fase {
    private int numeroFase;
    private Jogador jogador;
    private List<Inimigo> inimigos;

    // constantes objetivo
    private int objetivoX;
    private int objetivoY;
    private final int TAMANHO_OBJETIVO = 25;
    private int larguraPainel;
    private int alturaPainel;
    private Random aleatorio;

    // constantes
    private static final int TAMANHO_JOGADOR = 20;
    private static final int TAMANHO_INIMIGO = 20;
    private static final int VELOCIDADE_INICIAL_JOGADOR = 3;
    private static final int VELOCIDADE_INICIAL_INIMIGO = 2;
    private static final int CONTAGEM_INICIAL_INIMIGOS = 3;

    public Fase(int numeroFase, int larguraPainel, int alturaPainel) {
        this.numeroFase = numeroFase;
        this.larguraPainel = larguraPainel;
        this.alturaPainel = alturaPainel;
        this.aleatorio = new Random();
        this.inimigos = new ArrayList<>();
        inicializarFase();
    }

    // getters membros fase
    public int getNumeroFase() { return numeroFase; }
    public Jogador getJogador() { return jogador; }
    public List<Inimigo> getInimigos() { return inimigos; }
    public int getObjetivoX() { return objetivoX; }
    public int getObjetivoY() { return objetivoY; }
    public int getTamanhoObjetivo() { return TAMANHO_OBJETIVO; }

    private void inicializarFase() {
        // inicializa com jogador em posicao aleatoria
        int xInicioJogador = aleatorio.nextInt(larguraPainel - TAMANHO_JOGADOR);
        int yInicioJogador = aleatorio.nextInt(alturaPainel - TAMANHO_JOGADOR);
        jogador = new Jogador(xInicioJogador, yInicioJogador, VELOCIDADE_INICIAL_JOGADOR + (numeroFase - 1), TAMANHO_JOGADOR);

        // valida posicoes iniciais do objetivo com base na posicao do jogador e so para quando nao colidirem
        do {
            objetivoX = aleatorio.nextInt(larguraPainel - TAMANHO_OBJETIVO);
            objetivoY = aleatorio.nextInt(alturaPainel - TAMANHO_OBJETIVO);
        } while (jogador.verificarColisao(objetivoX, objetivoY, TAMANHO_OBJETIVO));

        // add inimigo com base no nivel da fase
        int contagemInimigos = CONTAGEM_INICIAL_INIMIGOS + (numeroFase - 1);
        for (int i = 0; i < contagemInimigos; i++) {
            adicionarInimigo();
        }
    }

    private void adicionarInimigo() {
        int inimigoX, inimigoY;
        boolean colisao;
        do {
            colisao = false;
            inimigoX = aleatorio.nextInt(larguraPainel - TAMANHO_INIMIGO);
            inimigoY = aleatorio.nextInt(alturaPainel - TAMANHO_INIMIGO);

            if (jogador.verificarColisao(inimigoX, inimigoY, TAMANHO_INIMIGO)) { // verifica colisão com novo inimigo -> jogador
                colisao = true;
                continue;
            }
            if (new Rectangle(inimigoX, inimigoY, TAMANHO_INIMIGO, TAMANHO_INIMIGO).intersects
            (new Rectangle(objetivoX, objetivoY, TAMANHO_OBJETIVO, TAMANHO_OBJETIVO))) { // verifica colisão com novo inimigo -> objetivo
                colisao = true;
                continue;
            }
            for (Inimigo existente : inimigos) {
                if (existente.verificarColisao(inimigoX, inimigoY, TAMANHO_INIMIGO)) { // verifica colisão com novo inimigo -> inimigos existentes
                    colisao = true;
                    break;
                }
            }
        } while (colisao);

        Inimigo novoInimigo = new Inimigo(inimigoX, inimigoY, VELOCIDADE_INICIAL_INIMIGO + (numeroFase - 1), TAMANHO_INIMIGO);
        inimigos.add(novoInimigo);
    }

    // verifica se a colisao foi com o objetivo ou com inimigo
    public EstadoJogo atualizar() {
        jogador.mover(larguraPainel, alturaPainel);

        for (Inimigo inimigo : inimigos) {
            inimigo.mover(larguraPainel, alturaPainel);
            if (jogador.verificarColisao(inimigo.getX(), inimigo.getY(), inimigo.getTamanho())) {
                return EstadoJogo.FIM_DE_JOGO; // colidiu = game over
            }
        }

        if (jogador.verificarColisao(objetivoX, objetivoY, TAMANHO_OBJETIVO)) {
            return EstadoJogo.FASE_COMPLETA; // win
        }

        return EstadoJogo.RODANDO;
    }
}