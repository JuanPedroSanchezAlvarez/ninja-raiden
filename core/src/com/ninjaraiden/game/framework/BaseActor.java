package com.ninjaraiden.game.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public abstract class BaseActor extends Group {

    private Animation<TextureRegion> animation;
    private float elapsedTime, acceleration, deceleration, maxSpeed;
    private boolean animationPaused;
    private Vector2 velocityVec, accelerationVec;
    private Polygon boundaryPolygon;
    private static Rectangle worldBounds;

    public BaseActor(final float x, final float y, final Stage s) {
        super();
        setPosition(x,y);
        s.addActor(this);
        animation = null;
        elapsedTime = 0;
        animationPaused = false;
        velocityVec = new Vector2(0,0);
        accelerationVec = new Vector2(0,0);
        acceleration = 0;
        maxSpeed = 1000;
        deceleration = 0;
    }

    public void setAnimation(final Animation<TextureRegion> anim) {
        animation = anim;
        TextureRegion tr = animation.getKeyFrame(0);
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        setSize(w, h);
        setOrigin(w/2, h/2);
        if (boundaryPolygon == null) {
            setBoundaryRectangle();
        }
    }

    public void setAnimationPaused(final boolean pause) {
        animationPaused = pause;
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
        if (!animationPaused) {
            elapsedTime += delta;
        }
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a);
        if (animation != null && isVisible()) {
            batch.draw(animation.getKeyFrame(elapsedTime), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
        super.draw(batch, parentAlpha);
    }

    public Animation<TextureRegion> loadAnimationFromFiles(final String[] fileNames, final float frameDuration, final boolean loop) {
        Array<TextureRegion> textureArray = new Array<>();
        for (String fileName : fileNames) {
            Texture texture = new Texture(Gdx.files.internal(fileName));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureArray.add(new TextureRegion(texture));
        }
        return loadAnimation(frameDuration, textureArray, loop);
    }

    public Animation<TextureRegion> loadAnimationFromSheet(final String fileName, final int rows, final int cols, final float frameDuration, final boolean loop) {
        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;
        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);
        Array<TextureRegion> textureArray = new Array<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                textureArray.add(temp[r][c]);
            }
        }
        return loadAnimation(frameDuration, textureArray, loop);
    }

    private Animation<TextureRegion> loadAnimation(final float frameDuration, final Array<TextureRegion> textureArray, final boolean loop) {
        Animation<TextureRegion> anim = new Animation<>(frameDuration, textureArray);
        anim.setPlayMode(loop ? Animation.PlayMode.LOOP : Animation.PlayMode.NORMAL);
        if (animation == null) {
            setAnimation(anim);
        }
        return anim;
    }

    public Animation<TextureRegion> loadTexture(final String fileName) {
        String[] fileNames = new String[1];
        fileNames[0] = fileName;
        return loadAnimationFromFiles(fileNames, 1, true);
    }

    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(elapsedTime);
    }

    public void setSpeed(final float speed) {
        // If length is zero, then assume motion angle is zero degrees
        if (velocityVec.len() == 0) {
            velocityVec.set(speed, 0);
        } else {
            velocityVec.setLength(speed);
        }
    }

    public float getSpeed() {
        return velocityVec.len();
    }

    public void setMotionAngle(final float angle) {
        velocityVec.setAngleDeg(angle);
    }

    public float getMotionAngle() {
        return velocityVec.angleDeg();
    }

    public boolean isMoving() {
        return (getSpeed() > 0);
    }

    public void setAcceleration(final float acc) {
        acceleration = acc;
    }

    public void accelerateAtAngle(final float angle) {
        accelerationVec.add(new Vector2(acceleration, 0).setAngleDeg(angle));
    }

    public void accelerateForward() {
        accelerateAtAngle(getRotation());
    }

    public void setMaxSpeed(final float ms) {
        maxSpeed = ms;
    }

    public void setDeceleration(final float dec) {
        deceleration = dec;
    }

    public void applyPhysics(final float delta) {
        // Apply acceleration
        velocityVec.add(accelerationVec.x * delta, accelerationVec.y * delta);
        float speed = getSpeed();
        // Decrease speed (decelerate) when not accelerating
        if (accelerationVec.len() == 0) {
            speed -= deceleration * delta;
        }
        // Keep speed within set bounds
        speed = MathUtils.clamp(speed, 0, maxSpeed);
        // Update velocity
        setSpeed(speed);
        // Apply velocity
        moveBy(velocityVec.x * delta, velocityVec.y * delta);
        // Reset acceleration
        accelerationVec.set(0, 0);
    }

    public void setBoundaryRectangle() {
        float[] vertices = {0, 0, getWidth(), 0, getWidth(), getHeight(), 0, getHeight()};
        boundaryPolygon = new Polygon(vertices);
    }

    public void setBoundaryPolygon(final int numSides) {
        float[] vertices = new float[2 * numSides];
        for (int i = 0; i < numSides; i++) {
            float angle = i * 6.28f / numSides;
            // x-coordinate
            vertices[2 * i] = getWidth() / 2 * MathUtils.cos(angle) + getWidth() / 2;
            // y-coordinate
            vertices[2 * i + 1] = getHeight() / 2 * MathUtils.sin(angle) + getHeight() / 2;
        }
        boundaryPolygon = new Polygon(vertices);
    }

    public Polygon getBoundaryPolygon() {
        boundaryPolygon.setPosition(getX(), getY());
        boundaryPolygon.setOrigin(getOriginX(), getOriginY());
        boundaryPolygon.setRotation(getRotation());
        boundaryPolygon.setScale(getScaleX(), getScaleY());
        return boundaryPolygon;
    }

    public boolean overlaps(final BaseActor other) {
        // Initial test to improve performance
        if (!this.getBoundaryPolygon().getBoundingRectangle().overlaps(other.getBoundaryPolygon().getBoundingRectangle())) {
            return false;
        }
        return Intersector.overlapConvexPolygons(this.getBoundaryPolygon(), other.getBoundaryPolygon());
    }

    public void centerAtPosition(final float x, final float y) {
        setPosition(x - getWidth() / 2 , y - getHeight() / 2);
    }

    public void centerAtActor(final BaseActor other) {
        centerAtPosition(other.getX() + other.getWidth() / 2 , other.getY() + other.getHeight() / 2);
    }

    public void setOpacity(final float opacity) {
        this.getColor().a = opacity;
    }

    public Vector2 preventOverlap(final BaseActor other) {
        // Initial test to improve performance
        if (!this.getBoundaryPolygon().getBoundingRectangle().overlaps(other.getBoundaryPolygon().getBoundingRectangle())) {
            return null;
        }
        Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();
        if (!Intersector.overlapConvexPolygons(this.getBoundaryPolygon(), other.getBoundaryPolygon(), mtv)) {
            return null;
        }
        this.moveBy(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);
        return mtv.normal;
    }

    public static ArrayList<BaseActor> getList(final Stage stage, final String className) {
        ArrayList<BaseActor> list = new ArrayList<>();
        Class theClass = null;
        try {
            theClass = Class.forName(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (theClass != null) {
            for (Actor a : stage.getActors()) {
                if (theClass.isInstance(a)) {
                    list.add((BaseActor)a);
                }
            }
        }
        return list;
    }

    public static int count(final Stage stage, final String className) {
        return getList(stage, className).size();
    }

    public static void setWorldBounds(final float width, final float height) {
        worldBounds = new Rectangle(0, 0, width, height);
    }

    public static void setWorldBounds(final BaseActor ba) {
        setWorldBounds(ba.getWidth(), ba.getHeight());
    }

    public void boundToWorld() {
        // Check left and right edge
        if (getX() < 0) {
            setX(0);
        } else if (getX() + getWidth() > worldBounds.width) {
            setX(worldBounds.width - getWidth());
        }
        // Check bottom abd top edge
        if (getY() < 0) {
            setY(0);
        } else if (getY() + getHeight() > worldBounds.height) {
            setY(worldBounds.height - getHeight());
        }
    }

    public void alignCamera() {
        Camera cam = this.getStage().getCamera();
        // Center camera on actor
        cam.position.set(this.getX() + this.getOriginX(), this.getY() + this.getOriginY(), 0);
        // Bound camera to layout
        cam.position.x = MathUtils.clamp(cam.position.x, cam.viewportWidth / 2, worldBounds.width - cam.viewportWidth / 2);
        cam.position.y = MathUtils.clamp(cam.position.y, cam.viewportHeight / 2, worldBounds.height - cam.viewportHeight / 2);
        cam.update();
    }

    public boolean isWithinDistance(final float distance, final BaseActor other) {
        float scaleX = (this.getWidth() + 2 * distance) / this.getWidth();
        float scaleY = (this.getHeight() + 2 * distance) / this.getHeight();
        this.getBoundaryPolygon().setScale(scaleX, scaleY);
        // Initial test to improve performance
        if (!this.getBoundaryPolygon().getBoundingRectangle().overlaps(other.getBoundaryPolygon().getBoundingRectangle())) {
            return false;
        }
        return Intersector.overlapConvexPolygons(this.getBoundaryPolygon(), other.getBoundaryPolygon());
    }

    public static Rectangle getWorldBounds() {
        return worldBounds;
    }

}
