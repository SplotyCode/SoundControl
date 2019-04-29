package team.gutterteam123.soundcontrol.sound.device;

import java.awt.*;
import java.io.Serializable;

public interface Flowable extends Serializable {

    String name();

    Rectangle getPosition();

    void setPosition(Rectangle dimension);

}
