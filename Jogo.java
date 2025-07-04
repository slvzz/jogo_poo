import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Jogo extends JFrame {
    private Fase faseAtual;
    private EstadoJogo estadoJogo;
    private Tela telaJogo;

    private int numeroFaseAtual;
    private final int LARGURA_JANELA = 800;
    private final int ALTURA_JANELA = 600;

    public Jogo() {
        setTitle("Fuga dos Inimigos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        this.numeroFaseAtual = 0;
        this.estadoJogo = EstadoJogo.TELA_INICIAL;

        this.telaJogo = new Tela(this);
        add(telaJogo);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public EstadoJogo getEstadoJogo() { return estadoJogo; }
    public void setEstadoJogo(EstadoJogo estadoJogo) { this.estadoJogo = estadoJogo; }
    public Fase getFaseAtual() { return faseAtual; }

    public void iniciarProximaFase() {
        numeroFaseAtual++;
        faseAtual = new Fase(numeroFaseAtual, LARGURA_JANELA, ALTURA_JANELA);
        estadoJogo = EstadoJogo.RODANDO;
        telaJogo.requestFocusInWindow();
    }

    public void reiniciarJogo() {
        numeroFaseAtual = 0;
        faseAtual = null;
        estadoJogo = EstadoJogo.TELA_INICIAL;
        telaJogo.requestFocusInWindow();
    }

    // atualiza a etapa do jogo
    public void atualizarJogo() {
        if (faseAtual != null) {
            EstadoJogo novoEstado = faseAtual.atualizar();
            // verifica se foi game over ou prox fase
            if (novoEstado != EstadoJogo.RODANDO) {
                if (novoEstado == EstadoJogo.FIM_DE_JOGO && this.estadoJogo != EstadoJogo.FIM_DE_JOGO) {
                    GerenciadorSom.tocarSom("game_over.wav"); 
                } else if (novoEstado == EstadoJogo.FASE_COMPLETA && this.estadoJogo != EstadoJogo.FASE_COMPLETA) {
                    GerenciadorSom.tocarSom("fase_completa.wav"); 
                }
                this.estadoJogo = novoEstado; // muda o estado do jogo
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Jogo();
        });
    }
}