package com.ninjaraiden.game.framework;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;

public class SetAnimationAction extends Action {

    protected Animation<TextureRegion> animationToDisplay;

    public SetAnimationAction(final Animation<TextureRegion> a) {
        animationToDisplay = a;
    }

    @Override
    public boolean act(final float delta) {
        BaseActor ba = (BaseActor)target;
        ba.setAnimation(animationToDisplay);
        return true;
    }

}
