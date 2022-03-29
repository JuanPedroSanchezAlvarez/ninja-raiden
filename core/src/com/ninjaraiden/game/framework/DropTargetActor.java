package com.ninjaraiden.game.framework;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class DropTargetActor extends BaseActor {

    private boolean targetable;

    public DropTargetActor(final float x, final float y, final Stage s) {
        super(x, y, s);
        targetable = true;
    }

    public void setTargetable(final boolean t) {
        targetable = t;
    }

    public boolean isTargetable() {
        return targetable;
    }

}
