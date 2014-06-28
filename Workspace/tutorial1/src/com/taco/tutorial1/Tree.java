package com.taco.tutorial1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tree {

	Vector2 position, size;
	Texture texture;
	Rectangle bounds;
	
	public Tree(Vector2 position){
		texture = new Texture(Gdx.files.internal("data/tree.png"));
		this.position = position;
		this.size = new Vector2(texture.getWidth(), texture.getHeight());
		bounds = new Rectangle(position.x, position.y, size.x, size.y);
		
	}
	
	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getSize() {
		return size;
	}

	public void setSize(Vector2 size) {
		this.size = size;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public void update(){
		bounds.set(position.x, position.y, size.x, size.y);
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(texture, position.x, position.y, size.x, size.y);
		
	}
}
