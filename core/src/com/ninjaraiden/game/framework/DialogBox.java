package com.ninjaraiden.game.framework;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class DialogBox extends BaseActor {

    private final Label dialogLabel;
    private final float padding = 16;

    public DialogBox(final float x, final float y, final Stage s) {
        super(x, y, s);
        loadTexture("assets/dialog-translucent.png");
        dialogLabel = new Label("", BaseGame.labelStyle);
        dialogLabel.setWrap(true);
        dialogLabel.setAlignment(Align.topLeft);
        dialogLabel.setPosition(padding, padding);
        this.setDialogSize(getWidth(), getHeight());
        this.addActor(dialogLabel);
    }

    public void setDialogSize(final float width, final float height) {
        this.setSize(width, height);
        dialogLabel.setWidth(width - 2 * padding);
        dialogLabel.setHeight(height - 2 * padding);
    }

    public void setText(final String text) {
        dialogLabel.setText(text);
    }

    public void setFontScale(final float scale) {
        dialogLabel.setFontScale(scale);
    }

    public void setFontColor(final Color color) {
        dialogLabel.setColor(color);
    }

    public void setBackgroundColor(final Color color) {
        this.setColor(color);
    }

    public void alignTopLeft() {
        dialogLabel.setAlignment(Align.topLeft);
    }

    public void alignCenter() {
        dialogLabel.setAlignment(Align.center);
    }

}
