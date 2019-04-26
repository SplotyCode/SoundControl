package team.gutterteam123.soundcontrol.sound;

import team.gutterteam123.soundcontrol.sound.device.VirtualInput;
import team.gutterteam123.soundcontrol.sound.device.VirtualOutput;

public class SoundUpdater extends Thread {

    @Override
    public void run() {
        while (true) {
            for (VirtualInput input : VirtualInput.FILE_SYSTEM.getEntries()) {
                input.updateBuffer();
                for (int i = 0; i < Controller.BUFFER_SIZE; i+=2) {
                    // convert byte pair to int
                    short buf1 = (short) ((input.getBuffer()[i+1]) << 8);
                    short buf2 = (short) (input.getBuffer()[i] & 0xff);

                    input.getShortBuffer()[i] = (short) (buf1 | buf2);
                }
            }
            for (VirtualOutput output : VirtualOutput.FILE_SYSTEM.getEntries()) {
                for (int i = 0; i < Controller.BUFFER_SIZE / 2; i++) {
                    int value = 0;
                    int sources = 0;
                    for (Channel channel : output.getChannels()) {
                        for (VirtualInput input : channel.getInputs()) {
                            value += input.getShortBuffer()[i] * channel.getVolume() * output.getVolume();
                            sources++;
                        }
                    }
                    value = value / sources;
                    output.getBuffer()[i * 2] = (byte) value;
                    output.getBuffer()[i * 2 + 1] = (byte) (value >> 8);
                }
            }
        }
    }
}
