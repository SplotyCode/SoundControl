package team.gutterteam123.soundcontrol.sound;

import lombok.Setter;
import team.gutterteam123.soundcontrol.SoundControl;
import team.gutterteam123.soundcontrol.sound.device.VirtualInput;
import team.gutterteam123.soundcontrol.sound.device.VirtualOutput;

public class SoundUpdater extends Thread {

    @Setter private volatile boolean running = true;

    @Override
    public void run() {
        while (running) {
            /* Get new data from inputs and convert them */
            for (VirtualInput input : Controller.getInstance().getActiveInputs()) {
                input.updateBuffer();
                input.generateSamples();
            }
            /* Manipulate convert back and send to output */
            for (VirtualOutput output : Controller.getInstance().getActiveOutputs()) {
                for (int i = 0; i < Controller.SAMPLE_BUFFER_SIZE; i++) {
                    float rawValue = 0;
                    for (Channel channel : output.getChannels()) {
                        for (VirtualInput input : channel.getInputs()) {
                            rawValue += ((float) input.getSampleBuffer()[i]) * channel.getVolume();
                        }
                    }
                    short value = (short) (rawValue * output.getVolume());
                    output.getBuffer()[i * Controller.FRAME_SIZE] = (byte) value;
                    output.getBuffer()[i * Controller.FRAME_SIZE + 1] = (byte) (value >> 8);
                }
                output.flushBuffer();
            }
        }
    }
}
