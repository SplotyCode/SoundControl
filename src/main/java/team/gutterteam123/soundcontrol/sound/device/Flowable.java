package team.gutterteam123.soundcontrol.sound.device;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.Serializable;

public abstract class Flowable implements Serializable {

    public abstract String name();

    @Getter
    @Setter
    private transient long drawId;

    @Getter
    @Setter
    private transient int conections;

    public void checkDrawState(long drawId) {
        if (this.drawId != drawId) {
            this.drawId = drawId;
            conections = 0;
        }
        conections++;
    }


    @Getter
    @Setter
    private Rectangle position = new Rectangle(-1, -1, 100, 100);


}
