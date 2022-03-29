package com.ninjaraiden.game.framework;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Sign extends BaseActor {

    private String text;
    private boolean viewing;

    public Sign(final float x, final float y, final Stage s) {
        super(x, y, s);
        loadTexture("assets/sign.png");
        text = "";
        viewing = false;
    }

    public void setText(String t) {
        text = t;
    }

    public String getText() {
        return text;
    }

    public void setViewing(boolean v) {
        viewing = v;
    }

    public boolean isViewing() {
        return viewing;
    }

}
