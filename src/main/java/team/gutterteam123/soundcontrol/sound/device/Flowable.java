package team.gutterteam123.soundcontrol.sound.device;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.Serializable;

public abstract class Flowable implements Serializable {

    @Getter @Setter private Rectangle position = new Rectangle(-1, -1, 100, 100);

    @Getter @Setter private transient long drawId;

    @Getter @Setter private transient int connections;

    public abstract String name();

    public void checkDrawState(long drawId) {
        if (this.drawId != drawId) {
            this.drawId = drawId;
            connections = 0;
        }
        connections++;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + name();
    }
}
