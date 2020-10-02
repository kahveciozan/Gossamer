package com.kahveci.gossamer.Sprides;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.kahveci.gossamer.Gossamer;

public class KuruKafa extends InteractiveTiledObject {

    public KuruKafa(World world, TiledMap map, Rectangle rect) {
        super(world, map, rect);
        fixture.setUserData(this);
        setCatogoryFilter(Gossamer.COIN_BIT);
}

    @Override
    public void onHeadHit() {
        Gdx.app.log("Kuru Kafa","Carpisma Oldu");
    }
}
