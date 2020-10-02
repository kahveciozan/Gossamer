package com.kahveci.gossamer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kahveci.gossamer.Gossamer;
import com.kahveci.gossamer.Scenes.Hud;
import com.kahveci.gossamer.Sprides.Spider;
import com.kahveci.gossamer.Tools.B2WorldCreator;
import com.kahveci.gossamer.Tools.WorldContactListener;

public class PlayScreen implements Screen {
    private OrthographicCamera gamecam;
    private Gossamer game;
    private Viewport viewport;

    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;

    Spider player ;

    private TextureAtlas atlas;

    public PlayScreen(Gossamer game) {

        atlas =new TextureAtlas("spider_and_fly.pack");

        this.game = game;

        gamecam = new OrthographicCamera();
        viewport = new StretchViewport(Gossamer.WIDTH/Gossamer.PPM,Gossamer.HEIGHT/Gossamer.PPM,gamecam);

        hud = new Hud(game.sb);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1/Gossamer.PPM);

        gamecam.position.set((viewport.getWorldWidth()/2),(viewport.getWorldHeight()/2),0);

        world = new World(new Vector2(0,0),true);
        b2dr = new Box2DDebugRenderer();

        // map teki nesneleri tespit et ve kare icine al
        new B2WorldCreator(world,map);

        player = new Spider(world,this);

        world.setContactListener(new WorldContactListener());
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(){

        if (Gdx.input.isTouched()){
            if (Gdx.input.getDeltaX()>10  && player.b2body.getLinearVelocity().x <0.5)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if (Gdx.input.getDeltaX()<-10  && player.b2body.getLinearVelocity().x >-0.5)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
            if (Gdx.input.getDeltaY()<10  )
                player.b2body.applyLinearImpulse(new Vector2(0, 0.1f), player.b2body.getWorldCenter(), true);
            if (Gdx.input.getDeltaY()>-10  )
                player.b2body.applyLinearImpulse(new Vector2(0, -0.1f), player.b2body.getWorldCenter(), true);
        }
    }

    public void update(float delta){
        handleInput();

        world.step(1/60f,6,2);
        player.update(delta);
        //gamecam.position.x = player.b2body.getPosition().x;
        //gamecam.position.y = player.b2body.getPosition().y;
        gamecam.update();
        renderer.setView(gamecam);
    }
    @Override
    public void render(float delta) {
        update(delta);
        ekranTemizle(delta);

        renderer.render();
        b2dr.render(world,gamecam.combined);

        game.sb.setProjectionMatrix(gamecam.combined);

        /* -------------- cizim baslangic ------- */
        game.sb.begin();
        player.draw(game.sb);
        game.sb.end();
        /* -------------------------------------- */

        // camerayi senkronize et
        game.sb.setProjectionMatrix(hud.stage.getCamera().combined);
        // ust baslıklar
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    private void ekranTemizle(float delta){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
