package com.kahveci.gossamer.Sprides;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kahveci.gossamer.Gossamer;

public abstract class InteractiveTiledObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle rect;
    protected Body body;
    protected Fixture fixture;

    public InteractiveTiledObject(World world, TiledMap map, Rectangle rect) {
        this.world = world;
        this.tile = tile;
        this.rect = rect;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set( (rect.getX()+rect.getWidth()/2)/ Gossamer.PPM , (rect.getY()+rect.getHeight()/2)/Gossamer.PPM );

        body = world.createBody(bdef);

        shape.setAsBox(rect.getWidth()/2/Gossamer.PPM,rect.getHeight()/2/Gossamer.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }

    public abstract void onHeadHit();
    public void setCatogoryFilter(short filterbit){
        Filter filter = new Filter();
        filter.categoryBits = filterbit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(2);
        return layer.getCell((int)(body.getPosition().x * Gossamer.PPM / 16),
                                (int)(body.getPosition().y * Gossamer.PPM / 16));
    }
}
