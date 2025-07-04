import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class GerenciadorSom {

    public static void tocarSom(String caminhoArquivo) {
        try {
            File arquivoSom = new File("sons/" + caminhoArquivo);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(arquivoSom);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            clip.start();

        } catch (Exception e) {} // ignorando qualquer erro de som que exista
    }
}