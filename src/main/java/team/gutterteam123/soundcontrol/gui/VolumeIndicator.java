package team.gutterteam123.soundcontrol.gui;

import lombok.Setter;
import team.gutterteam123.soundcontrol.sound.Controller;

import javax.swing.*;
import java.awt.*;

public class VolumeIndicator extends JComponent {

    public double rPeak = 0.0D;
    public double rRMS = 0.0D;
    @Setter public short[] rMeter = new short[Controller.SAMPLE_BUFFER_SIZE];

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        double erPeak = 0, rPeakTMP, rRMSTMP;
        g2d.setColor(Color.decode("#000000"));
        String[] label = { "0", "3", "5", "10", "15", "20", "25", "30", "35", "40" };
        double[] value = { 1.0D, 0.501187234D, 0.3162278D, 0.1D, 0.03162278D, 0.01D, 0.003162278D, 0.001D, 3.162278E-4D, 1.0E-4D };
        for (int j = 0; j < 10; j++) {
            int xp = 10 + 10 * (int)(40.0D + 10.0D * Math.log10(value[j]));
            g2d.drawLine(xp, 30, xp, 35);
            g2d.drawString(label[j], xp - 3, 50);
        }
        rPeak -= 0.005D;
        rPeak = Math.max(rPeak, 0.01);
        for (int i = 0; i < Controller.SAMPLE_BUFFER_SIZE; i++) {
            short rTmp = (short) Math.abs(rMeter[i]);
            if (erPeak < rTmp) erPeak = rTmp;
            if (rPeak < rTmp) rPeak = rTmp;
        }
        if (erPeak > rRMS) {
            rRMS += (erPeak - rRMS) / 10.0D;
        } else if (rRMS > erPeak) {
            rRMS -= (rRMS - erPeak) / 10.0D;
        }
        if (rRMS < 0.01D) rRMS = 0.01D;
        if (rRMS > 1.0D) rRMS = 1.0D;
        if (rPeak > 1.0D) rPeak = 1.0D;
        rRMSTMP = 40.0D + 10.0D * Math.log10(rRMS * rRMS);
        rPeakTMP = 40.0D + 10.0D * Math.log10(rPeak * rPeak);
        g2d.setColor(Color.decode("#929292"));
        g2d.fillRect(10, 15, (int)(rRMSTMP * 10.0D), 10);
        //if (rPeakTMP > 37.0D) g2d.setColor(Color.decode("#DD0000")); else g2d.setColor(Color.decode("#00DD00"));
        //g2d.drawLine((int)(10.0D + rPeakTMP * 10.0D), 15, (int)(10.0D + rPeakTMP * 10.0D), 25);
    }
}
