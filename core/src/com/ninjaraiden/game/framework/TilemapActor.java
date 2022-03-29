package com.ninjaraiden.game.framework;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Iterator;

public class TilemapActor extends Actor {

    private final TiledMap tiledMap;
    private final OrthographicCamera tiledCamera;
    private final OrthoCachedTiledMapRenderer tiledMapRenderer;

    public TilemapActor(final String filename, final Stage theStage) {
        tiledMap = new TmxMapLoader().load(filename);
        int tileWidth = (int)tiledMap.getProperties().get("tilewidth");
        int tileHeight = (int)tiledMap.getProperties().get("tileheight");
        int numTilesHorizontal = (int)tiledMap.getProperties().get("width");
        int numTilesVertical = (int)tiledMap.getProperties().get("height");
        int mapWidth = tileWidth * numTilesHorizontal;
        int mapHeight = tileHeight * numTilesVertical;
        BaseActor.setWorldBounds(mapWidth, mapHeight);
        tiledMapRenderer = new OrthoCachedTiledMapRenderer(tiledMap);
        tiledMapRenderer.setBlending(true);
        tiledCamera = new OrthographicCamera();
        tiledCamera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        tiledCamera.update();
        theStage.addActor(this);
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        Camera mainCamera = getStage().getCamera();
        tiledCamera.position.x = mainCamera.position.x;
        tiledCamera.position.y = mainCamera.position.y;
        tiledCamera.update();
        tiledMapRenderer.setView(tiledCamera);
        // Need the following code to force batch order, otherwise it is batched and rendered last
        batch.end();
        tiledMapRenderer.render();
        batch.begin();
    }

    public ArrayList<MapObject> getRectangleList(final String propertyName) {
        ArrayList<MapObject> list = new ArrayList<>();
        for (MapLayer layer : tiledMap.getLayers()) {
            for (MapObject obj : layer.getObjects()) {
                if (obj instanceof RectangleMapObject) {
                    if (obj.getProperties().containsKey("name") && obj.getProperties().get("name").equals(propertyName)) {
                        list.add(obj);
                    }
                }
            }
        }
        return list;
    }

    public ArrayList<MapObject> getTileList(final String propertyName) {
        ArrayList<MapObject> list = new ArrayList<>();
        for (MapLayer layer : tiledMap.getLayers()) {
            for (MapObject obj : layer.getObjects()) {
                if (obj instanceof TiledMapTileMapObject) {
                    // Default MapProperties are stored within associated Tile object
                    // Instance-specific overrides are stored in MapObject
                    TiledMapTileMapObject tmtmo = (TiledMapTileMapObject)obj;
                    TiledMapTile t = tmtmo.getTile();
                    // Get list of default property keys
                    Iterator<String> propertyKeys = t.getProperties().getKeys();
                    // Iterate over keys; copy default values if needed
                    while (propertyKeys.hasNext()) {
                        String key = propertyKeys.next();
                        // Check if value already exists; if not, create property with default value
                        if (!obj.getProperties().containsKey(key)) {
                            Object value = t.getProperties().get(key);
                            obj.getProperties().put(key, value);
                        }
                    }
                    if (t.getProperties().containsKey("name") && t.getProperties().get("name").equals(propertyName)) {
                        list.add(obj);
                    }
                }
            }
        }
        return list;
    }

}
