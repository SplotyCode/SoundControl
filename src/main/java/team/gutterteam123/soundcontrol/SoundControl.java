package team.gutterteam123.soundcontrol;

import io.github.splotycode.mosaik.runtime.LinkBase;
import io.github.splotycode.mosaik.runtime.application.Application;
import io.github.splotycode.mosaik.runtime.startup.BootContext;
import lombok.Getter;
import team.gutterteam123.soundcontrol.sound.Channel;
import team.gutterteam123.soundcontrol.sound.Controller;
import team.gutterteam123.soundcontrol.sound.device.VirtualInput;

import javax.sound.sampled.*;
import java.util.Arrays;

public class SoundControl extends Application {

    public static SoundControl getInstance() {
        return LinkBase.getApplicationManager().getApplicationByClass(SoundControl.class);
    }

    @Getter private Controller controller = new Controller();

    public static void main(String[] args) throws Exception {
        //new SoundControl().start(null);
        for (Mixer.Info info : AudioSystem.getMixerInfo()) {
            Mixer m = AudioSystem.getMixer(info);
            if (!info.getName().toLowerCase().contains("default")) continue;
            for (Line.Info a : m.getTargetLineInfo()) {
                if (a instanceof DataLine.Info) {
                    System.out.println(info.getName());
                    for (AudioFormat format : ((DataLine.Info) a).getFormats()) {
                        if (!format.isBigEndian() && format.getChannels() == 2 && format.getEncoding() == AudioFormat.Encoding.PCM_SIGNED) {
                            System.out.println(format);
                        }
                    }
                }
            }
        }
    }

    public void start(BootContext bootContext) throws Exception {
        Controller.setInstance(controller);
        getLocalShutdownManager().addFirstShutdownTask(controller::stop);
        controller.init();
        controller.startSync();
    }

    public String getName() {
        return "Sound Control";
    }
}
