package team.gutterteam123.soundcontrol.sound;

import lombok.Getter;
import lombok.Setter;

import javax.sound.sampled.*;
import java.util.ArrayList;

public class Controller {

    @Getter @Setter private static Controller instance = new Controller();

    public static final int BUFFER_SIZE = 1_000;

    public static final DataLine.Info INPUT_INFO = new DataLine.Info(TargetDataLine.class, Controller.FORMAT);
    public static final DataLine.Info OUTPUT_INFO = new DataLine.Info(SourceDataLine.class, Controller.FORMAT);

    public static final AudioFormat FORMAT = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED,
            44100, /* sample rate */
            16, /* bit | sample size */
            2, /* stereo | channels */
            4, /* 4 bytes/frame | frame size */
            44100, /* frames per second | frame rate */
            false /* little-endian */
    );

    public ArrayList<String> getInputMixer() {
        ArrayList<String> mixers = new ArrayList<>();
        for (Mixer.Info info : AudioSystem.getMixerInfo()) {
            if (AudioSystem.getMixer(info).isLineSupported(INPUT_INFO)) {
                mixers.add(info.getName());
            }
        }
        return mixers;
    }

    public ArrayList<String> getOutputMixer() {
        ArrayList<String> mixers = new ArrayList<>();
        for (Mixer.Info info : AudioSystem.getMixerInfo()) {
            if (AudioSystem.getMixer(info).isLineSupported(OUTPUT_INFO)) {
                mixers.add(info.getName());
            }
        }
        return mixers;
    }

    @Getter private ArrayList<Channel> channels = new ArrayList<>();

}
