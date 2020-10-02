package com.kahveci.gossamer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kahveci.gossamer.Screens.PlayScreen;


public class Gossamer extends Game {
	public static final int WIDTH =  (480);
	public static final int HEIGHT = (800);
	public static final float PPM = 100;

	public static final short DEFAULT_BIT = 1;
	public static final short GOSSAMER_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;

	public static final String TITILE ="GOSSAMER";

	public static SpriteBatch sb;

	@Override
	public void create() {
		sb = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
}
