package com.ninjaraiden.game.framework;

import com.badlogic.gdx.scenes.scene2d.Action;

public class SetTextAction extends Action {

    protected String textToDisplay;
    protected DialogBox dialogBox;

    public SetTextAction(final String t) {
        textToDisplay = t;
        dialogBox = null;
    }

    @Override
    public boolean act(final float delta) {
        dialogBox = (DialogBox)target;
        dialogBox.setText(textToDisplay);
        return true;
    }

}
