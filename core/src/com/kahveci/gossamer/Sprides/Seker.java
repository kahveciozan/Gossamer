package com.kahveci.gossamer.Sprides;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kahveci.gossamer.Gossamer;

public class Seker extends InteractiveTiledObject {

    public Seker(World world, TiledMap map, Rectangle rect) {
        super(world, map, rect);
        fixture.setUserData(this);
        setCatogoryFilter(Gossamer.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Seker","Collision");
        setCatogoryFilter(Gossamer.DESTROYED_BIT);
        //getCell().setTile(null);
    }
}
