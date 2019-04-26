package team.gutterteam123.soundcontrol;

import io.github.splotycode.mosaik.runtime.LinkBase;
import io.github.splotycode.mosaik.runtime.application.Application;
import io.github.splotycode.mosaik.runtime.startup.BootContext;
import lombok.Getter;
import team.gutterteam123.soundcontrol.sound.Controller;

import javax.sound.sampled.*;

public class SoundControl extends Application {

    public static SoundControl getInstance() {
        return LinkBase.getApplicationManager().getApplicationByClass(SoundControl.class);
    }

    @Getter private Controller controller = new Controller();


    public static void test(TargetDataLine targetLine) {
        try {

            //Speaker
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, Controller.FORMAT);
            SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open();

            targetLine.open();

            Thread monitorThread = new Thread(() -> {
                targetLine.start();
                sourceLine.start();

                byte[] data = new byte[targetLine.getBufferSize() / 5];
                int readBytes;

                int i = 0;
                while (true) {
                    readBytes = targetLine.read(data, 0, data.length);
                    if (i % 100 == 0) {
                        byte max = Byte.MIN_VALUE;
                        byte min = Byte.MAX_VALUE;
                        for (byte by : data) {
                            max = by > max ? by : max;
                            min = by < min ? by : min;
                        }
                        System.out.println(max + " " + min);
                    }
                    //adjustVolume(data, Short.MAX_VALUE, i % 100 == 0);
                    sourceLine.write(data, 0, readBytes);
                    i++;
                }
            });

            System.out.println( "Start LIVE Monitor for 15 seconds" );
            monitorThread.start();

            Thread.sleep(15000);
            targetLine.stop();
            targetLine.close();
            System.out.println( "End LIVE Monitor" );

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private static int max, min;

    private static void adjustVolume(byte[] audioSamples, float volume, boolean debug) {
        for (int i = 0; i < audioSamples.length; i+=2) {
            // convert byte pair to int
            short buf1 = audioSamples[i+1];
            short buf2 = audioSamples[i];

            buf1 = (short) ((buf1 & 0xff) << 8);
            buf2 = (short) (buf2 & 0xff);

            short res = (short) (buf1 | buf2);
            max = Math.max(max, res);
            min = Math.min(min, res);
            if (debug) {
                System.out.println(max + " " + min);
            }
            res = (short) (res * volume);

            // convert back
            audioSamples[i] = (byte) res;
            audioSamples[i+1] = (byte) (res >> 8);
        }
    }

    public static void main(String[] args) throws LineUnavailableException {
        Gui gui = new Gui();
        gui.buildGui(1000,1000);
        for (Mixer.Info info : AudioSystem.getMixerInfo()) {
            Mixer m = AudioSystem.getMixer(info);
            DataLine.Info lineInfo = new DataLine.Info(TargetDataLine.class, Controller.FORMAT);
            if (m.isLineSupported(lineInfo)) {
                test((TargetDataLine) m.getLine(lineInfo));
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
