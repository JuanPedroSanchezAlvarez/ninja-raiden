package com.ninjaraiden.game.framework;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;

public final class SceneActions extends Actions {
    private SceneActions() {}

    public static Action setText(final String s) {
        return new SetTextAction(s);
    }

    public static Action pause() {
        return Actions.forever(Actions.delay(1));
    }

    public static Action moveToScreenLeft(final float duration) {
        return Actions.moveToAligned(0, 0, Align.bottomLeft, duration);
    }

    public static Action moveToScreenRight(final float duration) {
        return Actions.moveToAligned(BaseActor.getWorldBounds().width, 0, Align.bottomRight, duration);
    }

    public static Action moveToScreenCenter(final float duration) {
        return Actions.moveToAligned(BaseActor.getWorldBounds().width / 2, 0, Align.bottom, duration);
    }

    public static Action moveToOutsideLeft(final float duration) {
        return Actions.moveToAligned(0, 0, Align.bottomRight, duration);
    }

    public static Action moveToOutsideRight(final float duration) {
        return Actions.moveToAligned(BaseActor.getWorldBounds().width, 0, Align.bottomLeft, duration);
    }

    public static Action setAnimation(final Animation<TextureRegion> a) {
        return new SetAnimationAction(a);
    }

    public static Action typewriter(final String s) {
        return new TypewriterAction(s);
    }

}
