package com.kahveci.gossamer.Sprides;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.kahveci.gossamer.Gossamer;
import com.kahveci.gossamer.Screens.PlayScreen;

public class Spider extends Sprite {
    public enum State{FALLING, JUMPING,STANDING, RUNNING}
    public State currentState;
    private State previouState;

    public World world;
    public Body b2body;                         //Cebmeri isaret ediyor
    private TextureRegion spiderStand;
    private Animation spiderRun;
    private Animation spiderJump;
    private float stateTimer;
    private boolean runningRight;

    public BodyDef bdef;


    public Spider(World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("spider"));  // PlayScreen sinifinda tanımlanın ve projeye eklenen dosyayı ust sınıfın constructurina gonder
        this.world = world;

        currentState = State.RUNNING;
        previouState = State.RUNNING;
        stateTimer = 0;
        runningRight = true;

        /* --- Texture lari toplayip animasyon yapar   --- */
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i=3; i<10 ;i++)
            frames.add(new TextureRegion(getTexture(),i*64,32+64*3,64,64));
        spiderRun = new Animation(0.1f,frames);
        frames.clear();

        for (int i=0; i<10 ;i++)
            frames.add(new TextureRegion(getTexture(),i*64,32,64,64));
        spiderJump = new Animation(0.1f,frames);
        frames.clear();

        defineSpider();         // Cember seklindeki playerinizi tanımlayan metod

        spiderStand = new TextureRegion(getTexture(),0,32+64*4,64,64);

        setBounds(0,0,64/Gossamer.PPM,64/Gossamer.PPM);

        setRegion(spiderStand);
    }

    public void update(float delta){
        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
        setRegion(getFrame(delta));
    }

    public TextureRegion getFrame(float delta){
        currentState = getState();
        TextureRegion region;
        switch (currentState){
            case JUMPING:
                region = (TextureRegion) spiderJump.getKeyFrame(stateTimer,true);
                break;
            case RUNNING:
                region = (TextureRegion) spiderRun.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = spiderStand;
                break;
        }

        //Texture larin yonunu degistirme
        if ((b2body.getLinearVelocity().x<0 || !runningRight) && !region.isFlipX()){
            region.flip(true,false);
            //region.flip(false,true);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x>0 || runningRight) && region.isFlipX() ){
            region.flip(true,false);
            //region.flip(false,true);
            runningRight = true;
        }

        stateTimer = currentState == previouState ? stateTimer + delta : 0;
        previouState = currentState;

        return region;
    }

    public State getState(){
        if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previouState == State.JUMPING))
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    /* -------- Cember seklinde player body -------------*/
    public void defineSpider() {
        bdef = new BodyDef();
        bdef.position.set(32/ Gossamer.PPM,(Gossamer.HEIGHT-64-64)/ Gossamer.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(18/Gossamer.PPM);
        fdef.filter.categoryBits = Gossamer.GOSSAMER_BIT;
        fdef.filter.maskBits = Gossamer.DEFAULT_BIT |  Gossamer.COIN_BIT | Gossamer.BRICK_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-6/ Gossamer.PPM,18/Gossamer.PPM), new Vector2(6/ Gossamer.PPM,18/Gossamer.PPM));
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("head");
    }
}
