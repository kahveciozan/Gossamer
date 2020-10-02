package com.kahveci.gossamer.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kahveci.gossamer.Gossamer;
import com.kahveci.gossamer.Sprides.KuruKafa;
import com.kahveci.gossamer.Sprides.Seker;

public class B2WorldCreator {

    public B2WorldCreator(World world, TiledMap map) {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;



        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            new KuruKafa(world,map,rect);
        }
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();

            new Seker(world,map,rect);
        }
    }
}
