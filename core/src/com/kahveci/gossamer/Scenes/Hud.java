package com.kahveci.gossamer.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kahveci.gossamer.Gossamer;


public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private Integer score;

    Label countDownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label spiderLabel;

    public Hud(SpriteBatch sb){
        worldTimer = 60;
        timeCount =0;
        score = 0;

        viewport = new StretchViewport(Gossamer.WIDTH,Gossamer.HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport,sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        // sonuclar
        countDownLabel = new Label(String.format("%03d",worldTimer),new Label.LabelStyle(new BitmapFont(), Color.PINK));
        scoreLabel  = new Label(String.format("%06d",score),new Label.LabelStyle(new BitmapFont(), Color.PINK));
        timeLabel  = new Label("KALAN SURE",new Label.LabelStyle(new BitmapFont(), Color.RED));

        //ust basliklar
        levelLabel = new Label("1-1",new Label.LabelStyle(new BitmapFont(), Color.PINK));
        worldLabel =new Label("LEVEL",new Label.LabelStyle(new BitmapFont(), Color.RED));
        spiderLabel =new Label("Gossamer",new Label.LabelStyle(new BitmapFont(), Color.RED));

        table.add(spiderLabel).expandX().padTop(0);
        table.add(worldLabel).expandX().padTop(0);
        table.add(timeLabel).expandX().padTop(0);
        table.row();
        table.add(scoreLabel).expandX().padTop(0);
        table.add(levelLabel).expandX().padTop(0);
        table.add(countDownLabel).expandX().padTop(0);

        stage.addActor(table);

    }
    // gorevi biten stage yok et
    @Override
    public void dispose() {
        stage.dispose();
    }
}
