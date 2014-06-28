package com.taco.tutorial1;

import java.io.IOException;

import sun.rmi.runtime.Log;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class tutorial1 implements ApplicationListener {
	
	SpriteBatch batch;
	Texture texture; 
	Vector2 position;
	Player player;
	Tree tree; 
	World world;
	
	@Override
	public void create() {		
		batch = new SpriteBatch();
		world = new World(new Vector2(0,0),true);
		
		checkForSaveState();
		//TREE
		tree = new Tree (new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2));
	}

	@Override
	public void dispose() {
		try {
			Player.savePlayer(player.getPosition());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		
		
		
		player.update();
		tree.update();

		if(!player.getBounds().overlaps(tree.getBounds())){
			player.previousPosition = new Vector2(player.position.x,player.position.y);
			System.out.println("X: " + player.previousPosition.x + ", Y: " + player.previousPosition.y);
		}
		if(player.getBounds().overlaps(tree.getBounds())){
			System.out.println("Collision detected");
			System.out.println("X: " + player.previousPosition.x + ", Y: " + player.previousPosition.y);
			player.position = new Vector2(player.previousPosition.x,player.previousPosition.y);
		}
			
		
		batch.begin();
		
		batch.draw(player.getCurrentFrame(), player.getPosition().x, player.getPosition().y);
		batch.draw(tree.getTexture(), tree.getPosition().x, tree.getPosition().y);
		
		
	
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	public void checkForSaveState(){
		if(!Gdx.files.local("player.dat").exists()) {
			player = new Player(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2), "emily.jpg");
			try {
				Player.savePlayer(player.getPosition());
			} catch (IOException e){
				e.printStackTrace();
			}
			System.out.println("Player does not exist. Creating and Saving Player.");
		}
		
		else{
			try {
				player = new Player(Player.readPlayer(),"data/emily.jpg");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Player exists. Loading Player.");
		}
	}
}
